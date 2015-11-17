/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.dom.Profile;

import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Match.ProfileElementComparison;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProfileElementTypeTest {
	
	@Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);
	
	@Mock
	private ProfileElementTimePeriod demandProfileElement;
	
	@Mock
	private ProfileElementNumeric demandProfileElementAge;
	
	@Mock
	private ProfileElementUsePredicate supplyProfileElement;
	
	@Mock
	private Profile demandProfile;
	
	@Mock
	private Profile supplyProfile;
	
	@Mock
	private Supply supply;
	
	@Mock
	private Person supplier;
	
	@Before
    public void setup() {
		
	}

	//************* TEST getProfileElementTimePeriodComparison *********************//

	@Test
    public void testProfileElementTimePeriodComparison(){

		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementUsePredicate supplyProfileElement = new ProfileElementUsePredicate();
		Profile demandProfile = new Profile();
		ProfileElementTimePeriod demandProfileElement = new ProfileElementTimePeriod();

		supply.setOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupply(supply);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_TIME_PERIOD);

		assertEquals(supplyProfile.getSupply(), supply);
		assertEquals(supplyProfile.getOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getOwner(), supplier);

		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		ProfileElementComparison comp2 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);

		// exactly whole period
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_TIME_PERIOD);
		supplyProfileElement.setUseTimePeriod(true);
		demandProfileElement.setStartDate(new LocalDate(2015, 3, 1));
		demandProfileElement.setEndDate(new LocalDate(2015, 3, 11));
		supplyProfile.setStartDate(new LocalDate(2015, 3, 1));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 11));
		ProfileElementComparison comp4 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp4.getCalculatedMatchingValue().intValue(), 100);

		// half of period, supply end before demand end
		supplyProfile.setStartDate(new LocalDate(2015, 3, 1));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 6));
		ProfileElementComparison comp5 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp5.getCalculatedMatchingValue().intValue(), 50);

		// third with supply start way before
		supplyProfile.setStartDate(new LocalDate(2015, 1, 1));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 4));
		ProfileElementComparison comp6 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp6.getCalculatedMatchingValue().intValue(), 30);

		// half of period, supply start after demand start
		supplyProfile.setStartDate(new LocalDate(2015, 3, 6));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 21));
		ProfileElementComparison comp7 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp7.getCalculatedMatchingValue().intValue(), 50);

		// half of period, supply start after demand start
		supplyProfile.setStartDate(new LocalDate(2015, 3, 8));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 21));
		ProfileElementComparison comp8 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp8.getCalculatedMatchingValue().intValue(), 30);

		// supply period in the middle of demand period
		supplyProfile.setStartDate(new LocalDate(2015, 3, 3));
		supplyProfile.setEndDate(new LocalDate(2015, 3, 8));
		ProfileElementComparison comp9 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp9.getCalculatedMatchingValue().intValue(), 50);

		// no supply end date, start date after demand start
		supplyProfile.setStartDate(new LocalDate(2015, 3, 5));
		supplyProfile.setEndDate(null);
		ProfileElementComparison comp10 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp10.getCalculatedMatchingValue().intValue(), 60);

		// no supply start date, end date before demand end
		supplyProfile.setStartDate(null);
		supplyProfile.setEndDate(new LocalDate(2015, 3, 7));
		ProfileElementComparison comp11 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp11.getCalculatedMatchingValue().intValue(), 60);

		// no supply start and end
		supplyProfile.setStartDate(null);
		supplyProfile.setEndDate(null);
		ProfileElementComparison comp12 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp12.getCalculatedMatchingValue().intValue(), 100);

	}
	//---------------- END TEST getProfileElementTimePeriodComparison --------------------//

	//************* TEST getProfileElementAgeComparison **********************************//

	@Test
    public void testProfileElementAgeComparison(){

		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementUsePredicate supplyProfileElement = new ProfileElementUsePredicate();
		Profile demandProfile = new Profile();
		ProfileElementNumeric demandProfileElement = new ProfileElementNumeric();

		supply.setOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupply(supply);
		supplyProfile.setType(ProfileType.PERSON_PROFILE);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.AGE);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_AGE);

		assertEquals(supplyProfile.getSupply(), supply);
		assertEquals(supplyProfile.getOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getOwner(), supplier);

		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.AGE);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.AGE);
		ProfileElementComparison comp2 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.AGE);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);

		// exactly right Age
		demandProfileElement.setProfileElementType(ProfileElementType.AGE);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_AGE);
		supplyProfileElement.setUseAge(true);
		demandProfileElement.setNumericValue(20);
		Person person = (Person) supplyProfile.getOwner();
		person.setDateOfBirth(LocalDate.now().minusYears(20));
		ProfileElementComparison comp4 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp4.getCalculatedMatchingValue().intValue(), 100);

		// 1 yr too old
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(21));
		ProfileElementComparison comp5 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp5.getCalculatedMatchingValue().intValue(), 95);

		// 1 yr too old again
		demandProfileElement.setNumericValue(30);
		person.setDateOfBirth(LocalDate.now().minusYears(31));
		ProfileElementComparison comp6 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp6.getCalculatedMatchingValue().intValue(), 95);


		// 1 yr too young
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(19));
		ProfileElementComparison comp7 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp7.getCalculatedMatchingValue().intValue(), 95);

		// 1 yr too young again
		demandProfileElement.setNumericValue(30);
		person.setDateOfBirth(LocalDate.now().minusYears(29));
		ProfileElementComparison comp8 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp8.getCalculatedMatchingValue().intValue(), 95);

		// 2 yrs too old
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(22));
		ProfileElementComparison comp9 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp9.getCalculatedMatchingValue().intValue(), 90);

		// 2 yrs too young
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(18));
		ProfileElementComparison comp10 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp10.getCalculatedMatchingValue().intValue(), 90);

		// just some points at 19 yrs difference
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(39));
		ProfileElementComparison comp11 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp11.getCalculatedMatchingValue().intValue(), 5);

		// no points at 20 yrs difference
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(40));
		ProfileElementComparison comp12 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp12.getCalculatedMatchingValue().intValue(), 0);

		// no points when USE_AGE = false on supply profile element
		supplyProfileElement.setUseAge(false);
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(20));
		ProfileElementComparison comp13 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp13.getCalculatedMatchingValue().intValue(), 0);

		// no points when no dateOfBirth on supply actor owner
		supplyProfileElement.setUseAge(true);
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(null);
		ProfileElementComparison comp14 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp14.getCalculatedMatchingValue().intValue(), 0);

		// no points when ProfileType not PERSON_PROFILE for supply profile
		supplyProfile.setType(ProfileType.COURSE_PROFILE);
		supplyProfileElement.setUseAge(true);
		demandProfileElement.setNumericValue(20);
		person.setDateOfBirth(LocalDate.now().minusYears(20));
		ProfileElementComparison comp15 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp15.getCalculatedMatchingValue().intValue(), 0);

	}

	//------------- TEST getProfileElementAgeComparison ----------------------------------//

	//************* TEST getProfileElementHourlyRateComparison *********************//

	@Test
	public void testProfileElementHourlyRateComparison(){

		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementNumeric supplyProfileElement = new ProfileElementNumeric();
		Profile demandProfile = new Profile();
		ProfileElementNumeric demandProfileElement = new ProfileElementNumeric();

		supply.setOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupply(supply);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);

		assertEquals(supplyProfile.getSupply(), supply);
		assertEquals(supplyProfile.getOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getOwner(), supplier);

		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		ProfileElementComparison comp2 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);

		//same supply and demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(50);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(50);
		ProfileElementComparison comp4 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp4.getCalculatedMatchingValue().intValue(), 100);

		//supply lower than demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(50);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(49);
		ProfileElementComparison comp5 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp5.getCalculatedMatchingValue().intValue(), 100);

		//supply higher than demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(100);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(140);
		ProfileElementComparison comp6 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp6.getCalculatedMatchingValue().intValue(), 0);

		//supply higher than demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(100);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(120);
		ProfileElementComparison comp7 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp7.getCalculatedMatchingValue().intValue(), 50);

		//supply higher than demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(100);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(110);
		ProfileElementComparison comp8 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp8.getCalculatedMatchingValue().intValue(), 75);

		//supply higher than demand
		demandProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		demandProfileElement.setNumericValue(100);
		supplyProfileElement.setProfileElementType(ProfileElementType.HOURLY_RATE);
		supplyProfileElement.setNumericValue(130);
		ProfileElementComparison comp9 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp9.getCalculatedMatchingValue().intValue(), 25);


	}
	//---------------- END TEST getProfileElementHourlyRateComparison --------------------//

	//************* TEST getProfileElementEducationLevelComparison *********************//

	DropDownForProfileElement dropDownHbo = new DropDownForProfileElement();
	DropDownForProfileElement dropDownMbo = new DropDownForProfileElement();

	@Test
	public void testProfileElementEducationLevelComparison(){

		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementDropDown supplyProfileElement = new ProfileElementDropDown();
		Profile demandProfile = new Profile();
		ProfileElementDropDown demandProfileElement = new ProfileElementDropDown();

		supply.setOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupply(supply);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);
		supplyProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);

		assertEquals(supplyProfile.getSupply(), supply);
		assertEquals(supplyProfile.getOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getOwner(), supplier);

		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		ProfileElementComparison comp2 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);

		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);

		//same supply and demand
		demandProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);
		demandProfileElement.setDropDownValue(dropDownHbo);
		supplyProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);
		supplyProfileElement.setDropDownValue(dropDownHbo);
		ProfileElementComparison comp4 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp4.getCalculatedMatchingValue().intValue(), 100);

		//not same supply and demand
		demandProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);
		demandProfileElement.setDropDownValue(dropDownHbo);
		supplyProfileElement.setProfileElementType(ProfileElementType.EDUCATION_LEVEL);
		supplyProfileElement.setDropDownValue(dropDownMbo);
		ProfileElementComparison comp5 = demandProfileElement.getProfileElementType().getProfileElementComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp5.getCalculatedMatchingValue().intValue(), 0);

	}
	//---------------- END TEST getProfileElementEducationLevelComparison --------------------//
	
}
