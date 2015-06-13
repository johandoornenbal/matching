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
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import org.isisaddons.services.postalcode.postcodenunl.Haversine;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementLocation;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Profile.RequiredProfileElementRole;
import info.matchingservice.dom.Rules.ProfileElementTypeMatchingRules;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolder;

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
 * @version 0.3 13-02-2015
 */
@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ProfileMatchingService extends AbstractService {
	
	
	//********************************************************************* getProfileElementTimePeriodComparison ************************************************************************
	/**
	 * Returns a comparison between a demand TimePeriod element and a supply UseTimePeriod element
	 * Calculation is 'optimistic': when there are no start- or enddates on the supply profile availability is assumed  when Use Time Period exists and is set to true
	 * 
	 * Both start and endDate on the demand profile Time Period element are obligatory
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementTimePeriodComparison(
			final ProfileElementTimePeriod demandProfileElement,
			final ProfileElementUsePredicate supplyProfileElement
			)
	{
		
		// return null if types are not as expected
		if (
				demandProfileElement.getProfileElementType() != ProfileElementType.TIME_PERIOD
				
				||
				
				supplyProfileElement.getProfileElementType() != ProfileElementType.USE_TIME_PERIOD

			)
		{
			return null;
		}

		Integer matchValue = 0;
		
		// When supply profile dates are meant to be used, indicated by supplyProfileElement.getUseTimePeriod() == true
		if (supplyProfileElement.getUseTimePeriod()) {
			
			// Default for supply with Use Time Period set to true
			matchValue = 100;
						
			// if the endDate on demandProfile element is there and the startDate on supplyProfile also
			// and if startdate later than enddate value = 0;
			// 
			//	(pic)
			// 	demand -------------------*enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			//	supply xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx *startdate* ----------------
			if (
					supplyProfileElement.getProfileElementOwner().getProfileStartDate() !=null
					&&
					supplyProfileElement.getProfileElementOwner().getProfileStartDate().isAfter(demandProfileElement.getEndDate())
					
				)
			{
				matchValue = 0;
				
				System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
				System.out.println(supplyProfileElement.getProfileElementOwner().getActorOwner().toString() +  " >> start supply later than end demand");
				
			}
			
			
			// if the startDate on demandProfile element is there and the endDate on supplyProfile also
			// and if startdate later than enddate value = 0;
			//
			// 	(pic)
			// 	demand xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx *startdate* ----------------
			// 	supply -------------------*enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			
			if (
					supplyProfileElement.getProfileElementOwner().getProfileEndDate() !=null
					&&
					demandProfileElement.getStartDate().isAfter(supplyProfileElement.getProfileElementOwner().getProfileEndDate())
					
				)
			{
				matchValue = 0;
				
				System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
				System.out.println(supplyProfileElement.getProfileElementOwner().getActorOwner().toString() +  " >> end supply before start demand");
			}
			
			// if supply start <= demand start and supply end <= demand end
			// calculate relative value
			//
			//	(pic)
			// 	demand xxxxxxxxxxxxxxxxxxx *startdate* ------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
			// 	supply xxxxxx[*startdate*] ----------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			
			if (
					(	
						supplyProfileElement.getProfileElementOwner().getProfileEndDate() !=null
						&&
						supplyProfileElement.getProfileElementOwner().getProfileEndDate().isBefore(demandProfileElement.getEndDate().plusDays(1))
					)
					&&
					(
							(
									supplyProfileElement.getProfileElementOwner().getProfileStartDate() !=null
									&&
									supplyProfileElement.getProfileElementOwner().getProfileStartDate().isBefore(demandProfileElement.getStartDate().plusDays(1))
							)
							||
							(
									supplyProfileElement.getProfileElementOwner().getProfileStartDate() ==null
							)
					)
				)
			{
				
				final int demandDays = Days.daysBetween(demandProfileElement.getStartDate(), demandProfileElement.getEndDate()).getDays();
				final int deltaDays = Days.daysBetween(supplyProfileElement.getProfileElementOwner().getProfileEndDate(), demandProfileElement.getEndDate()).getDays();
				
				final double value = 100 * ( 1- (double) deltaDays / (double) demandDays);
				
				matchValue = (int) value;
				
			}
			
			// if supply start > demand start and supply end > demand end
			// calculate relative value
			//
			//	(pic)
			// 	demand xxxxxxxx *startdate* ------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
			// 	supply xxxxxxxxxxxxxxxxxxxxxxxx*startdate* ----------------------------- [*enddate*] xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			if (
					
					(
						supplyProfileElement.getProfileElementOwner().getProfileStartDate() !=null
						&&
						supplyProfileElement.getProfileElementOwner().getProfileStartDate().isAfter(demandProfileElement.getStartDate().minusDays(1))
					)
					&&
					(
						(
							supplyProfileElement.getProfileElementOwner().getProfileEndDate() !=null
							&&
							supplyProfileElement.getProfileElementOwner().getProfileEndDate().isAfter(demandProfileElement.getEndDate().minusDays(1))
						)
						||
						( 
								supplyProfileElement.getProfileElementOwner().getProfileEndDate() == null
						)
					)
					
				) 
			{
				
				final int demandDays = Days.daysBetween(demandProfileElement.getStartDate(), demandProfileElement.getEndDate()).getDays();
				final int deltaDays = Days.daysBetween(demandProfileElement.getStartDate(), supplyProfileElement.getProfileElementOwner().getProfileStartDate()).getDays();
				
				final double value = 100 * ( 1- (double) deltaDays / (double) demandDays);
				
				matchValue = (int) value;
				
			}
			
			// if supply start > demand start and supply end < demand end
			// calculate relative value
			//
			//	(pic)
			// 	demand xxxxxxxx *startdate* -------------------------------------------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxx
			// 	supply xxxxxxxxxxxxxxxxxxxxxxxx*startdate* ----------------------------- *enddate* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
			if (
					
					supplyProfileElement.getProfileElementOwner().getProfileStartDate() !=null
					&&
					supplyProfileElement.getProfileElementOwner().getProfileEndDate() !=null
					&&
					supplyProfileElement.getProfileElementOwner().getProfileStartDate().isAfter(demandProfileElement.getStartDate())
					&&
					supplyProfileElement.getProfileElementOwner().getProfileEndDate().isBefore(demandProfileElement.getEndDate().plusDays(1))
					
				) 
			{
				
				final int demandDays = Days.daysBetween(demandProfileElement.getStartDate(), demandProfileElement.getEndDate()).getDays();
				final int deltaDays = 
						Days.daysBetween(demandProfileElement.getStartDate(), 
						supplyProfileElement.getProfileElementOwner().getProfileStartDate()).
						getDays()
						+ 
						Days.daysBetween(supplyProfileElement.getProfileElementOwner().
								getProfileEndDate(),
								demandProfileElement.getEndDate()
						).getDays();
				
				final double value = 100 * ( 1- (double) deltaDays / (double) demandDays);
				
				matchValue = (int) value;
				
				System.out.println("value: " + value);
				System.out.println("match from getProfileElementTimePeriodComparison() in ProfileMatchingService.class:");
				System.out.println(supplyProfileElement.getProfileElementOwner().getActorOwner().toString() +  " >> start supply after start demand and end supply before end demand");
				System.out.println("matchValue:  " + matchValue);
				System.out.println("demand:  " + demandProfileElement.getStartDate().toString() + " - " + demandProfileElement.getEndDate().toString());
				System.out.println("supply:  " + supplyProfileElement.getProfileElementOwner().getProfileStartDate().toString() + " - " + supplyProfileElement.getProfileElementOwner().getProfileEndDate().toString());
				System.out.println("demandDays: " + demandDays + "  deltaDays: " + deltaDays);
				
			}
			
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
	
	
	//********************************************************************* END getProfileElementTimePeriodComparison ************************************************************************

	//********************************************************************* getProfileElementLocationComparison ************************************************************************

	/**
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementLocationComparison(
			final ProfileElementLocation demandProfileElement,
			final ProfileElementLocation supplyProfileElement
			)
	{
		
		// return null if types are not as expected
		if (
				demandProfileElement.getProfileElementType() != ProfileElementType.LOCATION
				
				||
				
				supplyProfileElement.getProfileElementType() != ProfileElementType.LOCATION

			)
		{
			return null;
		}

		// stub
		Integer matchValue = 0;
		double distance = Haversine.haversine(supplyProfileElement.getLatitude(), supplyProfileElement.getLongitude(), demandProfileElement.getLatitude(), demandProfileElement.getLongitude());		
		
		if (distance < 200 ) {
			matchValue = 10;
		}
		if (distance < 175 ) {
			matchValue = 20;
		}
		if (distance < 150 ) {
			matchValue = 30;
		}
		if (distance < 125 ) {
			matchValue = 40;
		}
		if (distance < 100 ) {
			matchValue = 50;
		}
		if (distance < 75 ) {
			matchValue = 60;
		}
		if (distance < 50 ) {
			matchValue = 65;
		}
		if (distance < 40 ) {
			matchValue = 70;
		}
		if (distance < 35 ) {
			matchValue = 75;
		}
		if (distance < 30 ) {
			matchValue = 80;
		}
		if (distance < 25 ) {
			matchValue = 85;
		}
		if (distance < 20 ) {
			matchValue = 90;
		}
		if (distance < 15 ) {
			matchValue = 95;
		}
		if (distance < 10 ) {
			matchValue = 100;
		}
		
		System.out.println("match from getProfileElementLocationComparison() in ProfileMatchingService.class:");
		
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
	
	
	//********************************************************************* END getProfileElementLocationComparison ************************************************************************

	//********************************************************************* getProfileElementAgeComparison ************************************************************************

	/**
	 * 
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementAgeComparison(
			final ProfileElementNumeric demandProfileElement,
			final ProfileElementUsePredicate supplyProfileElement
			)
	{
		
		// return null if types are not as expected
		if (
				demandProfileElement.getProfileElementType() != ProfileElementType.AGE
				
				||
				
				supplyProfileElement.getProfileElementType() != ProfileElementType.USE_AGE

			)
		{
			return null;
		}

		Integer matchValue = 0;
		
		// When supply profile date of birth is meant to be used, indicated by supplyProfileElement.getUseAge() == true
		if (supplyProfileElement.getUseAge()) {
			
			// Make sure the supplyProfileElement belongs to a Person Profile of a Person with dateOfBirth property not null
			QueryDefault<Person> query = 
	                QueryDefault.create(
	                		Person.class, 
	                    "findPersonUnique",
	                    "ownedBy", supplyProfileElement.getOwnedBy());
			
			if (
					container.firstMatch(query)!= null 
					
					&& 
					
					container.firstMatch(query).getDateOfBirth() != null
				
					&&
					
					supplyProfileElement.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE
				)
			{
				LocalDate supplyDateOfBirth = container.firstMatch(query).getDateOfBirth();
				new LocalDate();
				LocalDate toDay = LocalDate.now();
				Integer age = Years.yearsBetween(supplyDateOfBirth, toDay).getYears();
				Integer delta = Math.abs(age - demandProfileElement.getNumericValue());
				
				// The value of the match is 100 minus 5 x the difference in years between the demand age and the supplied age
				matchValue = 100 - 5*delta;
				if (matchValue < 0 ) { matchValue = 0; }
				
				System.out.println("match from getProfileElementAgeComparison() in ProfileMatchingService.class:");
				System.out.println("owner username: " + container.firstMatch(query).getOwnedBy() + " - dateOfBirth: " + container.firstMatch(query).getDateOfBirth());
				System.out.println("demanded age: " + demandProfileElement.getNumericValue() + " - matchValue: " + matchValue);
			}
			
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
	
	
	//********************************************************************* END getProfileElementAgeComparison ************************************************************************

	//********************************************************************* getProfileElementHourlyRateComparison ************************************************************************

	/**
	 *
	 * @param demandProfileElement
	 * @param supplyProfileElement
	 * @return
	 */
	@Programmatic
	public ProfileElementComparison getProfileElementHourlyRateComparison(
			final ProfileElementNumeric demandProfileElement,
			final ProfileElementNumeric supplyProfileElement
	)
	{

		// return null if types are not as expected
		if (
				demandProfileElement.getProfileElementType() != ProfileElementType.HOURLY_RATE

						||

						supplyProfileElement.getProfileElementType() != ProfileElementType.HOURLY_RATE

				)
		{
			return null;
		}

		Integer matchValue = 0;

		if (supplyProfileElement.getNumericValue() <= demandProfileElement.getNumericValue()) {
			matchValue = 100;
		} else {
			Long delta = Long.valueOf(supplyProfileElement.getNumericValue() - demandProfileElement.getNumericValue());
			System.out.println("delta: " + delta);
			// delta more than 40% of demand adds no points
			Long percentageDelta = delta * 100 / demandProfileElement.getNumericValue();
			System.out.println("percentageDelta: " + percentageDelta);
			if (percentageDelta >= 40 ){
				matchValue = 0;
			} else {
				//divide 100 points over 40% and subtract from max matchingValue of 100
				Long subtraction = percentageDelta * 100 / 40;
				matchValue = (int) (long)  (100 - subtraction);
			}

		}

		System.out.println("match from getProfileElementHourlyRateComparison() in ProfileMatchingService.class:");
		System.out.println("supplied hourlyRate: " + supplyProfileElement.getNumericValue());
		System.out.println("demanded hourlyRate: " + demandProfileElement.getNumericValue() + " - matchValue: " + matchValue);


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


	//********************************************************************* END getProfileElementHourlyRateComparison ************************************************************************



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

		if (
				(
						demandProfileElement.getProfileElementType() == ProfileElementType.BRANCHE_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS
						||
						demandProfileElement.getProfileElementType() == ProfileElementType.WEEKDAY_TAGS
				)
				&&
				demandProfileElement.getProfileElementType() == supplyProfileElement.getProfileElementType()
			)
		{
			
			if (
					getProfileElementTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementTag) supplyProfileElement).getCalculatedMatchingValue()
					>= 1
				) 
			{
				return getProfileElementTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementTag) supplyProfileElement);
			}
            
		}
		
		if (
				demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.PASSION
				
			)
		{
			if (
					getProfileElementPassionTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementText) supplyProfileElement).getCalculatedMatchingValue()
					>= 1
				)
			{
				return getProfileElementPassionTagComparison((ProfileElementTag) demandProfileElement, (ProfileElementText) supplyProfileElement);
			}
			
		}
		
		if (
				demandProfileElement.getProfileElementType() == ProfileElementType.TIME_PERIOD
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.USE_TIME_PERIOD
				
			)
		{
			if (

					getProfileElementTimePeriodComparison((ProfileElementTimePeriod) demandProfileElement, (ProfileElementUsePredicate) supplyProfileElement).getCalculatedMatchingValue()
					>= 1
					
				)
			{
				
				return getProfileElementTimePeriodComparison((ProfileElementTimePeriod) demandProfileElement, (ProfileElementUsePredicate) supplyProfileElement);
		
			}
		}
		
		if (
				demandProfileElement.getProfileElementType() == ProfileElementType.AGE
				&&
				supplyProfileElement.getProfileElementType() == ProfileElementType.USE_AGE
				
			)
		{
			if (

					getProfileElementAgeComparison((ProfileElementNumeric) demandProfileElement, (ProfileElementUsePredicate) supplyProfileElement).getCalculatedMatchingValue()
					>= 1
					
				)
			{
				
				return getProfileElementAgeComparison((ProfileElementNumeric) demandProfileElement, (ProfileElementUsePredicate) supplyProfileElement);
		
			}
		}

		if (
				demandProfileElement.getProfileElementType() == ProfileElementType.HOURLY_RATE
						&&
						supplyProfileElement.getProfileElementType() == ProfileElementType.HOURLY_RATE

				)
		{
			if (

					getProfileElementHourlyRateComparison((ProfileElementNumeric) demandProfileElement, (ProfileElementNumeric) supplyProfileElement).getCalculatedMatchingValue()
							>= 1
					)
			{
				return getProfileElementHourlyRateComparison((ProfileElementNumeric) demandProfileElement, (ProfileElementNumeric) supplyProfileElement);
			}
		}
		
		if (
				demandProfileElement.getProfileElementType() == ProfileElementType.LOCATION
				&&
				demandProfileElement.getProfileElementType() == supplyProfileElement.getProfileElementType()
			)
		{
			
			if (
					getProfileElementLocationComparison((ProfileElementLocation) demandProfileElement, (ProfileElementLocation) supplyProfileElement).getCalculatedMatchingValue()
					>= 1
				) 
			{
				return getProfileElementLocationComparison((ProfileElementLocation) demandProfileElement, (ProfileElementLocation) supplyProfileElement);
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
				demandProfile.getActorOwner().getActivated()

				&&

				supplyProfile.getActorOwner().getActivated()

				&&

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

				&&

				checkRequiredProfileElements(demandProfile, supplyProfile)
				
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
			
			if (
					calculatedMatchingValue >=	1
				) 
			{
				profileComparisons.createProfileComparison(demandProfile, supplyProfile,calculatedMatchingValue.intValue());
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
					demandProfile.getProfileType() == supplyProfile.getProfileType()
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
		
		return profileComparisons.allProfileComparisons();
		
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
					supplyProfile.getProfileType() == demandProfile.getProfileType()
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

		return profileComparisons.allProfileComparisons();

	}


	
	//********************************************************************* END collectProfileComparisons ************************************************************************	

	//********************************************************************* checkRequiredProfileElements ************************************************************************

	private boolean checkRequiredProfileElements(final Profile demandProfile, final Profile supplyProfile) {

		RequiredProfileElementRole profileElement;

		// business rule: when there is a element of ProfileElementType.ROLE_REQUIRED filter out the roles excluded by returning false; defaults to true
		if (profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).size() > 0) {
			profileElement = (RequiredProfileElementRole) profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0);

			Person  supplyActorOwner;
			supplyActorOwner = (Person) supplyProfile.getActorOwner();

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
