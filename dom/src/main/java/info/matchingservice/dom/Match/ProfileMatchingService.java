/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package info.matchingservice.dom.Match;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Profile.RequiredProfileElementRole;
import info.matchingservice.dom.Rules.ProfileElementTypeMatchingRules;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

/**
 * 
 * TODO: how to JUNITtest this object?
 * 
 * Algorithm:
 * 
 * Level 1.
 * collectProfileComparisons(demandProfile) loops over all 'matching' supplyProfiles of other users 
 * in order to find 'valid' profile comparisons for the demand profile
 *
 * collectDemandProfileComparisons(supplyProfile) loops over all 'matching' demandProfiles of other users
 * in order to find 'valid' profile comparisons for the supply profile
 *
 * 	- 'matching' will be caught in a 'rule'?; at the moment f.e. profileTypes have to be the same
 * 	- 'valid' will also be caught in a 'rule'?; at the moment: the calculated matching value of the profile comparison has to be greater than the threshold in the rulele
 *
 *(Nested) level 2.
 * getProfileComparison(demandProfile, supplyProfile) loops over all the profile elements on the demand profile
 * in order to find 'valid' profile element comparisons on the 'matching' supply profile. It then calculates the profile comparison by 
 * 	- first determine the average weight out of the given weights set on the demand profile element (if none are given: average weight will be set to 1)
 * 	- calculate the 'calculated matching value' of the profile comparison: 
 * 		- every profile element comparison contributes: [matching value of the element] * [weight] (OR if no weight is given for the element: * [average weight])
 * 		- this will be divided by the [total number of profile elements on the demand profile] * [average weight]
 * 		- NOTE: a demand profile element with no matches still counts and diminishes the overall outcome of the 'calculated matching value' on the profile comparison
 * 
 * 	- 'matching' will be caught in a 'rule'?; at the moment: profile element types have to be the same except for PASSION_TAGS matches PASSION
 * 	- 'valid' will also be caught in a 'rule'?; at the moment: the calculated matching value of the profile element comparison has to be greater than the threshold in the rule
 *
 * (Nested) level 3.
 * getProfileElementComparison(demandProfileElement, supplyProfileElement) returns a 'valid' profile element comparison
 * this comprises
 * 	- a check on whether a valid comparison is possible: if not: null will be returned
 * 	- a calculated matching value between 0 - 100
 * 	- the weight on the demand profile element (attributed to this 'match': how important the element match is to the demand owner)
 * 	- this method checks the type of the profile element on the demand profile and delegates to the appropriate method for the type
 * 	(e.g. getProfileElementPassionTagComparison(demandProfileElement, supplyProfileElement) )
 *
 * 	The actual comparison is delegated to the getProfileElementComparison() method on the ENUM ProfileElementType
 * 
 *
 * @version 1.0 29-08-2015
 */
@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ProfileMatchingService extends AbstractService {
	


	//********************************************************************* getProfileElementComparison ************************************************************************
	
	/**
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementComparison(
			final ProfileElement demandProfileElement, 
			final ProfileElement supplyProfileElement
			){

		/* Comparisons for equal element types*/
		if (
				(
						demandProfileElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.LOCATION
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.HOURLY_RATE
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.EDUCATION_LEVEL
				)
				&&
				demandProfileElement.getProfileElementType() == supplyProfileElement.getProfileElementType()
			)
		{

			if (
					demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement).getCalculatedMatchingValue()
					>= 1
					)
			{
				return demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
			}

		}

		/* Comparisons for unequal element types*/
		if (
				(demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.PASSION)
				||
				(demandProfileElement.getProfileElementType() == ProfileElementType.TIME_PERIOD
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.USE_TIME_PERIOD)
				||
				(demandProfileElement.getProfileElementType() == ProfileElementType.AGE
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.USE_AGE)

			)
		{

			if (
					demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement).getCalculatedMatchingValue()
							>= 1
					)
			{
				return demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
			}

		}

		// default
		return null;
		
	}
	
	@Inject
	ProfileElementTypeMatchingRules profileElementTypeMatchingRules;
	
	//********************************************************************* END getProfileElementComparison ************************************************************************
	
	//********************************************************************* ProfileComparison ************************************************************************
	
	/**
	 * Assumes and tests that supplyProfile is of another owner (usernames ownedBy are different)
	 * Assumes and tests that the match between profileTypes is according to the rules 
	 * 
	 * @param demandProfile
	 * @param supplyProfile
	 * @return
	 */
	@Programmatic
	public void getProfileComparison(
			final Profile demandProfile,
			final Profile supplyProfile
			){

		// check the validity - if not valid: return null
		if (
				// filter niet active Actors uit
				demandProfile.getOwner().isActivated()

				&&

				supplyProfile.getOwner().isActivated()

				&&

				// filter uit dezelfde eigenaar mocht dat per ongeluk er nog doorheen slippen
				!demandProfile.getOwnedBy().equals(supplyProfile.getOwnedBy())
				
				&&
				
				// implementation of mockRule1
				// types of profile must be the same
				demandProfile.getType() == supplyProfile.getType()
				
				&&
				
				//there are elements on the demand profile
				demandProfile.getElements().size() > 0
				
				&&
				
				//there are elements on the supply profile
				supplyProfile.getElements().size() > 0

				)
		{
			
			// init
			List<ProfileElementComparison> profileElementComparisons = new ArrayList<ProfileElementComparison>();
			Long calculatedMatchingValue = (long) 0;
			Integer cumWeight = 0;
            Integer averageWeight = 1; // of no weight is given, the average will be set to 1 as default
            Integer weightCounter = 0; // keeps track of profile elements on demand profile that have a weight filled in
			
			// loop through profile elements on the demand profile
			for (ProfileElement demandProfileElement: demandProfile.getElements()){

				//test if demandProfileElement is active
				if (demandProfileElement.getIsActive()) {

					// loop through profile elements on the 'matching' supply profile
					for (ProfileElement supplyProfileElement: supplyProfile.getElements()){

						//test if supplyProfileElement is active
						if (supplyProfileElement.getIsActive()) {

							// get the element comparison
							ProfileElementComparison profileElementComparison = this.getProfileElementComparison(demandProfileElement, supplyProfileElement);

							// catch the null returns and filter out element comparisons without a value - if any
							// TODO: maak efficienter (Het afvangen van nulls zouden we voorkomen door te zorgen dat ook hier de 'rules' geimplementeerd worden
							try {
								if (profileElementComparison.getCalculatedMatchingValue() > 0) {

									// voeg de comparison toe aan het lijstje relevante comparisons
									profileElementComparisons.add(profileElementComparison);

									// debug
//									System.out.println("match from getProfileComparison() in ProfileMatchingService.class:");
//									System.out.println("matchingValue: " + profileElementComparison.getCalculatedMatchingValue() + " - " + profileElementComparison.getMatchingProfileElementActorOwner().title());

								}
							} catch (NullPointerException e) {
//								System.out.println("no valid element comparison");
							}
							
						}
					}

				}
			}
			
			
			// loop through all found valid profile element comparisons in order to 
			// determine the cumulative weight given by the demand profile 
			for (ProfileElementComparison e: profileElementComparisons) {
                
                if (e.getWeight() != null && e.getWeight() > 0) {
                	
                    cumWeight += e.getWeight();
                    weightCounter++;
                    
                    //debug
//                    System.out.println("match from getProfileComparison() in ProfileMatchingService.class:");
//                    System.out.println("cumWeight: " + cumWeight + " weightCounter " + weightCounter);
                    
                }
				
			}
			
			// determine average weight out of cumulative weight given and the number of elements with a weight
			// again: if no weights are found the average will remain 1 as initialized
            if (cumWeight > 0 && weightCounter > 0) {
            	
                averageWeight = cumWeight / weightCounter;
                
                //debug
//                System.out.println("avarageWeight: " + averageWeight);
                
            }
			
			
            // again loop through all found valid profile element comparisons in order to 
            // determine the summed calculated matching value
			for (ProfileElementComparison e: profileElementComparisons) {
				
				if (e.getWeight() != null && e.getWeight()>0){
					
					calculatedMatchingValue += e.getWeight()*e.getCalculatedMatchingValue();
					
				} else {
					
					calculatedMatchingValue += averageWeight*e.getCalculatedMatchingValue();
					
				}
				
			}
			
			// divide the summed calculated matching value by the [number of profile elements on the demand profile] * [average weight] 
				
			calculatedMatchingValue = calculatedMatchingValue / (demandProfile.getElements().size() * averageWeight);
										
			
			// create or update the profileComparison if value greater than threshold (0) and checkRequiredProfileElements == true
			
			if (
					calculatedMatchingValue >=	1 && checkRequiredProfileElements(demandProfile, supplyProfile)
				) 
			{
				if (profileComparisons.findProfileComparisonByDemandAndSupplyProfile(demandProfile, supplyProfile) != null) {
					profileComparisons.findProfileComparisonByDemandAndSupplyProfile(demandProfile, supplyProfile).setCalculatedMatchingValue(calculatedMatchingValue.intValue());
				} else {
					profileComparisons.createProfileComparison(demandProfile, supplyProfile, calculatedMatchingValue.intValue());
				}
			}
			// or delete profileComparison if value smaller then threshold or checkRequiredProfileElements == false
			if (
					calculatedMatchingValue <	1 || !checkRequiredProfileElements(demandProfile, supplyProfile)
					)
			{
				if (profileComparisons.findProfileComparisonByDemandAndSupplyProfile(demandProfile, supplyProfile) != null) {
					profileComparisons.findProfileComparisonByDemandAndSupplyProfile(demandProfile, supplyProfile).delete();
				}
			}

		}
	}


	@Inject
	ProfileTypeMatchingRules profileTypeMatchingRules;
	
	//********************************************************************* END ProfileComparison ************************************************************************
	
	//********************************************************************* collectProfileComparisons ************************************************************************
	
	/**
	 * Collects all profile comparisons between the demandProfile and all matching supplyProfiles
	 * 
	 * Business logic:
	 * Implements ProfileTypeMatching Rule
	 * 
	 * @param demandProfile
	 * @return
	 */
	@Programmatic
	public List<ProfileComparison> collectProfileComparisons(final Profile demandProfile){


		for (Profile supplyProfile: profiles.allSupplyProfilesOtherOwners(demandProfile.getOwnedBy())) {
			
			
			if (
					demandProfile.getType() == supplyProfile.getType()
					) 
			{
				
				try
				{
					getProfileComparison(demandProfile,supplyProfile);

				}
				catch(NullPointerException e)
				{
					
				}
				
			}
			
		}
		
		return profileComparisons.collectProfileComparisons(demandProfile);
		
	}

	/**
	 * Collects all profile comparisons between the supplyProfile and all matching demandProfiles
	 *
	 * Business logic:
	 * Implements ProfileTypeMatching Rule
	 *
	 * @param supplyProfile
	 * @return
	 */
	@Programmatic
	public List<ProfileComparison> collectDemandProfileComparisons(final Profile supplyProfile){

		for (Profile demandProfile: profiles.allDemandProfilesOtherOwners(supplyProfile.getOwnedBy())) {


			if (
					supplyProfile.getType() == demandProfile.getType()
					)
			{

				try
				{
					getProfileComparison(demandProfile,supplyProfile);
				}
				catch(NullPointerException e)
				{

				}

			}

		}

		return profileComparisons.collectDemandProfileComparisons(supplyProfile);

	}


	
	//********************************************************************* END collectProfileComparisons ************************************************************************	

	//********************************************************************* checkRequiredProfileElements ************************************************************************

	private boolean checkRequiredProfileElements(final Profile demandProfile, final Profile supplyProfile) {

		RequiredProfileElementRole profileElement;

		// business rule: when there is a element of ProfileElementType.ROLE_REQUIRED filter out the roles excluded by returning false; defaults to true
		if (profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).size() > 0
				&& profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0).getIsActive()) {
			profileElement = (RequiredProfileElementRole) profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0);

			Person  supplyActorOwner;
			supplyActorOwner = (Person) supplyProfile.getOwner();

			//case student is asked for and isStudent is found
			if (profileElement.getStudent() && supplyActorOwner.getIsStudent()) {
				return true;
			}

			//case professional is asked for and isProfessional is found
			if (profileElement.getProfessional() && supplyActorOwner.getIsProfessional()) {
				return true;
			}

			//case principal is asked for and isPrincipal is found
			if (profileElement.getPrincipal()  && supplyActorOwner.getIsPrincipal()) {
				return true;
			}

			// default: filter out all
			return false;

		}

		return true;
	}

	//********************************************************************* END checkRequiredProfileElements ************************************************************************



	// Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @javax.inject.Inject
    private Profiles profiles;

	@Inject
	private ProfileComparisons profileComparisons;

	@Inject
	private ProfileElements profileElements;
        

}
