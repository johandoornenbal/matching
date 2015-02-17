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

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Rules.ProfileElementTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileElementTypeMatchingRules;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * 
 * TODO: how to test this object?
 * 
 * Algorithm:
 * 
 * Level 1.
 * collectProfileComparisons(demandProfile) loops over all 'matching' supplyProfiles of other users 
 * in order to find 'valid' profile comparisons for the demand profile
 * 	- 'matching' will be caught in a 'rule'; at the moment: profileTypes have to be the same
 * 	- 'valid' will also be caught in a 'rule'; at the moment: the calculated matching value of the profile comparison has to be greater than the threshold in the rulele
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
 * 	- 'matching' will be caught in a 'rule'; at the moment: profile element types have to be the same except for PASSION_TAGS matches PASSION
 * 	- 'valid' will also be caught in a 'rule'; at the moment: the calculated matching value of the profile element comparison has to be greater than the threshold in the rule
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
 * 	- 'valid' will be caught in 'rules'; at the moment: at the moment: profile element types have to be the same except for PASSION_TAGS matches PASSION
 * 
 * @version 0.2 17-02-2015
 */
@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ProfileMatchingService extends AbstractService {
	
	//********************************************************************* getProfileElementPassionTagComparison ************************************************************************
	
	/**
	 * Returns a comparison between to demand profile element of type PASSION_TAGS and supply profile element of type PASSION
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementPassionTagComparison(
			final ProfileElementTag demandProfileElement,
			final ProfileElementText supplyProfileElement
			)
	{
		
		// return null if types are not as expected
		if (
				demandProfileElement.getProfileElementType() != ProfileElementType.PASSION_TAGS
				||
				supplyProfileElement.getProfileElementType() != ProfileElementType.PASSION
			)
		{
			return null;
		}
		
		Integer matchValue = 0;
		
		for (final Iterator<TagHolder> it = demandProfileElement.getCollectTagHolders().iterator(); it.hasNext();) 
		{
			
            Tag tag = it.next().getTag();
            
            if (supplyProfileElement.getTextValue().toLowerCase().matches("(.*)" + tag.getTagDescription() + "(.*)"))
            {
            	
                matchValue += 100;
                System.out.println("match from getProfileElementPassionTagComparison() in ProfileMatchingService.class:");
    			System.out.println("supply of: " + supplyProfileElement.getOwnedBy() + "type: " + supplyProfileElement.getProfileElementType().toString());
    			System.out.println(tag.getTagDescription() + " matchValue: " + matchValue);
    			
            }
        }
		
        // take the average matchValue of all Tags
        if (demandProfileElement.getCollectTagHolders().size() > 0){
        	
        	matchValue = matchValue / demandProfileElement.getCollectTagHolders().size();
        	
        } else {
        	
        	matchValue = 0;
     
        }
		
        ProfileElementComparison profileElementComparison = new ProfileElementComparison(
				demandProfileElement.getProfileElementOwner(),
				demandProfileElement, 
				supplyProfileElement, 
				supplyProfileElement.getProfileElementOwner(), 
				supplyProfileElement.getProfileElementOwner().getActorOwner(), 
				matchValue, 
				demandProfileElement.getWeight()
				);		
		return profileElementComparison;

	}
	
	//********************************************************************* END getProfileElementPassionTagComparison ************************************************************************
	
	//********************************************************************* getProfileElementTagComparison ************************************************************************
	
	/**
	 * Returns a comparison between to TagElements of the same ProfileElementType
	 * 
	 * business logic:
	 * The ProfileElementTypes should be the same
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementTagComparison(
			final ProfileElementTag demandProfileElement,
			final ProfileElementTag supplyProfileElement
			)
	{
		
		// return null if types are not the same
		if (demandProfileElement.getProfileElementType() != supplyProfileElement.getProfileElementType())
		{
			return null;
		}
		
		Integer matchValue = 0;
        Integer numberOfTagsOnDemand = 0;
        
    	//Iterate over all the tags (this is: tagholders) on the demand profileElement element
		for (final Iterator<TagHolder> it_demand = demandProfileElement.getCollectTagHolders().iterator(); it_demand.hasNext();){
			
			Tag tag_demand = it_demand.next().getTag();
			numberOfTagsOnDemand += 1;
			
			if (supplyProfileElement.getCollectTagHolders().size() > 0)
			{
				
				//iterate over all the tags (tagholders) on supply element
				for (final Iterator<TagHolder> it_supply = supplyProfileElement.getCollectTagHolders().iterator(); it_supply.hasNext();){ 
					
					Tag tag_supply = it_supply.next().getTag();
					
					if (tag_demand.equals(tag_supply)){
						
						matchValue += 100;
						System.out.println("match from getProfileElementTagComparison() in ProfileMatchingService.class:");
						System.out.println("supply of: " + supplyProfileElement.getOwnedBy() + " type: " + supplyProfileElement.getProfileElementType().toString());
						System.out.println(tag_supply.getTagDescription() + " matchValue: " + matchValue);
						
					}
					
				}
				
			}
			
		}
		
		// take the average matchValue of all Tags
		if (numberOfTagsOnDemand > 0) {
			
			matchValue = matchValue / numberOfTagsOnDemand;
			
		} else {
			
			matchValue = 0;
			
		}
		
		ProfileElementComparison profileElementComparison = new ProfileElementComparison(
				demandProfileElement.getProfileElementOwner(),
				demandProfileElement, 
				supplyProfileElement, 
				supplyProfileElement.getProfileElementOwner(), 
				supplyProfileElement.getProfileElementOwner().getActorOwner(), 
				matchValue, 
				demandProfileElement.getWeight()
				);		
		return profileElementComparison;
	}
	
	//********************************************************************* END getProfileElementTagComparison ************************************************************************
	
	//********************************************************************* getProfileElementComparison ************************************************************************
	
	/**
	 * 
	 * @param demandProfile
	 * @param demandProfileElement
	 * @param supplyProfile
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementComparison(
			final Profile demandProfile, 
			final ProfileElement demandProfileElement, 
			final Profile supplyProfile,
			final ProfileElement supplyProfileElement
			){
		
		final ProfileElementTypeMatchingRule mockRule1 = profileElementTypeMatchingRules.createProfileElementTypeMatchingRule("mockrule1", "SAME_PROFILE_ELEMENT_TYPE", 1);
		final ProfileElementTypeMatchingRule mockRule2 = profileElementTypeMatchingRules.createProfileElementTypeMatchingRule("mockrule2", "PASSION_TAGS_TO_PASSION", 1);
		
		if (
				//implementation of mockRule1
				(
						demandProfileElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS
				)
				&&
				demandProfileElement.getProfileElementType() == supplyProfileElement.getProfileElementType()
			)
		{
			
			if (
					// implement mockRule1
					getProfileElementTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementTag) supplyProfileElement).getCalculatedMatchingValue()
					>= 
					mockRule1.getMatchingProfileElementValueThreshold()
				) 
			{
				return getProfileElementTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementTag) supplyProfileElement);
			}
            
		}
		
		if (
				//implementation of mockRule2
				demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.PASSION
				
			)
		{
			if (
					// implement mockRule2
					getProfileElementPassionTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementText) supplyProfileElement).getCalculatedMatchingValue()
					>=
					mockRule2.getMatchingProfileElementValueThreshold()
				)
			{
				return getProfileElementPassionTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementText) supplyProfileElement);
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
	public ProfileComparison getProfileComparison(
			final Profile demandProfile,
			final Profile supplyProfile
			){
		
		ProfileComparison profileComparison = new ProfileComparison(demandProfile, supplyProfile, 0);
		
		final ProfileTypeMatchingRule mockRule1 = profileTypeMatchingRules.createProfileTypeMatchingRule("mockrule1", "SAME_PROFILE_TYPE", 1);
		
		// check the validity - if not valid: return null
		if (
				// filter uit dezelfde eigenaar mocht dat per ongeluk er nog doorheen slippen
				!demandProfile.getOwnedBy().equals(supplyProfile.getOwnedBy())
				
				&&
				
				//implementation of mockRule1
				demandProfile.getProfileType() == supplyProfile.getProfileType()
				
				&&
				
				//there are elements on the demand profile
				demandProfile.getCollectProfileElements().size() > 0
				
				&&
				
				//there are elements on the supply profile
				supplyProfile.getCollectProfileElements().size() > 0
				
				)
		{
			
			// init
			List<ProfileElementComparison> profileElementComparisons = new ArrayList<ProfileElementComparison>();
			Long calculatedMatchingValue = (long) 0;
			Integer cumWeight = 0;
            Integer averageWeight = 1; // of no weight is given, the average will be set to 1 as default
            Integer weightCounter = 0; // keeps track of profile elements on demand profile that have a weight filled in
			
			// loop through profile elements on the demand profile
			for (ProfileElement demandProfileElement: demandProfile.getCollectProfileElements()){
				
				// loop through profile elements on the 'matching' supply profile
				for (ProfileElement supplyProfileElement: supplyProfile.getCollectProfileElements()){
					
					// get the element comparison
					ProfileElementComparison profileElementComparison = this.getProfileElementComparison(demandProfile, demandProfileElement, supplyProfile, supplyProfileElement);
					
					// catch the null returns and filter out element comparisons without a value - if any 
					// TODO: maak efficienter (Het afvangen van nulls zouden we voorkomen door te zorgen dat ook hier de 'rules' geimplementeerd worden
					try
					{
						if (profileElementComparison.getCalculatedMatchingValue() > 0){
							
							// voeg de comparison toe aan het lijstje relevante comparisons
							profileElementComparisons.add(profileElementComparison);
							
							// debug
							System.out.println("match from getProfileComparison() in ProfileMatchingService.class:");
							System.out.println("matchingValue: " + profileElementComparison.getCalculatedMatchingValue() + " - " + profileElementComparison.getMatchingProfileElementActorOwner().title());
							
						}
					}
					catch(NullPointerException e) 
					{
						System.out.println("no valid element comparison");
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
                    System.out.println("match from getProfileComparison() in ProfileMatchingService.class:");
                    System.out.println("cumWeight: " + cumWeight + " weightCounter " + weightCounter);
                    
                }
				
			}
			
			// determine average weight out of cumulative weight given and the number of elements with a weight
			// again: if no weights are found the average will remain 1 as initialized
            if (cumWeight > 0 && weightCounter > 0) {
            	
                averageWeight = cumWeight / weightCounter;
                
                //debug
                System.out.println("avarageWeight: " + averageWeight);
                
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
				
			calculatedMatchingValue = calculatedMatchingValue / (demandProfile.getCollectProfileElements().size() * averageWeight);
										
			
			// create the profileComparison
			
			if (calculatedMatchingValue >= mockRule1.getMatchingProfileValueThreshold()) {
				profileComparison.setCalculatedMatchingValue(calculatedMatchingValue.intValue());
				profileComparison.setDemandProfile(demandProfile);
				profileComparison.setMatchingSupplyProfile(supplyProfile);
			}
			
			return profileComparison;	
			
		} else {
			
			return null;
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
		
		List<ProfileComparison> profileComparisons = new ArrayList<ProfileComparison>();
		
		final ProfileTypeMatchingRule mockRule1 = profileTypeMatchingRules.createProfileTypeMatchingRule("mockrule1", "SAME_PROFILE_TYPE", 1);
		
		for (Profile supplyProfile: profiles.allSupplyProfiles()) {
			
			
			if (
					// loop over all supply profiles owned by different owner (username) then the one on the demand profile
					supplyProfile.getOwnedBy() != demandProfile.getOwnedBy()
					&&
					// implement mockRule1
					demandProfile.getProfileType() == supplyProfile.getProfileType()
					
					) 
			{
				
				// implement mockRule1
				if (this.getProfileComparison(demandProfile, supplyProfile).getCalculatedMatchingValue() >= mockRule1.getMatchingProfileValueThreshold() ) {
					
					profileComparisons.add(this.getProfileComparison(demandProfile, supplyProfile));
					
				}
				
			}
			
		}
		
		return profileComparisons;
		
	}
	
	//********************************************************************* END collectProfileComparisons ************************************************************************	
	
    @Action(semantics=SemanticsOf.SAFE, invokeOn=InvokeOn.OBJECT_ONLY)
    @ActionLayout(contributed=Contributed.AS_ASSOCIATION)
    @CollectionLayout(render=RenderType.EAGERLY)
    @Render(Type.EAGERLY) // because of bug @CollectionLayout
    public List<ProfileComparison> collectProfileMatches(Profile demandProfile) {
        
    	List<ProfileComparison> profileComparisons = new ArrayList<ProfileComparison>();
        
        //***********INIT**************//
        //Init Test: Only if there are any Profiles
        if (container.allInstances(Profile.class).isEmpty()) {
            return profileComparisons;
        }
        
        profileComparisons = this.collectProfileComparisons(demandProfile);
 
        Collections.sort(profileComparisons);
        Collections.reverse(profileComparisons);
        
        return profileComparisons;
    }
    
    // this one is meant for demand profiles only
    public boolean hideCollectProfileMatches(Profile demandProfile){
        return demandProfile.getDemandOrSupply() != DemandOrSupply.DEMAND;
    }
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @javax.inject.Inject
    private Profiles profiles;
        

}
