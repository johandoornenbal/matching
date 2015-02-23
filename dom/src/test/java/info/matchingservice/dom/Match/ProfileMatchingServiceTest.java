package info.matchingservice.dom.Match;

import static org.junit.Assert.*;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;
import info.matchingservice.dom.Profile.ProfileType;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProfileMatchingServiceTest {
	
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
		
		ProfileMatchingService service = new ProfileMatchingService();
		
		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementUsePredicate supplyProfileElement = new ProfileElementUsePredicate();
		Profile demandProfile = new Profile();
		ProfileElementTimePeriod demandProfileElement = new ProfileElementTimePeriod();
		
		supply.setSupplyOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupplyProfileOwner(supply);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_TIME_PERIOD);
		
		assertNotNull(service);
		assertEquals(supplyProfile.getSupplyProfileOwner(), supply);
		assertEquals(supplyProfile.getActorOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getActorOwner(), supplier);
		
		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		
		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		ProfileElementComparison comp2 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);
		
		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);
		
		// exactly whole period
		demandProfileElement.setProfileElementType(ProfileElementType.TIME_PERIOD);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_TIME_PERIOD);
		supplyProfileElement.setUseTimePeriod(true);
		demandProfileElement.setStartDate(new LocalDate(2015, 3, 1));
		demandProfileElement.setEndDate(new LocalDate(2015, 3, 11));
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 1));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 11));
		ProfileElementComparison comp4 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp4.getCalculatedMatchingValue().intValue(), 100);
		
		// half of period, supply end before demand end
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 1));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 6));
		ProfileElementComparison comp5 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp5.getCalculatedMatchingValue().intValue(), 50);
		
		// third with supply start way before
		supplyProfile.setProfileStartDate(new LocalDate(2015, 1 , 1));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 4));
		ProfileElementComparison comp6 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp6.getCalculatedMatchingValue().intValue(), 30);
		
		// half of period, supply start after demand start
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 6));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 21));
		ProfileElementComparison comp7 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp7.getCalculatedMatchingValue().intValue(), 50);
		
		// half of period, supply start after demand start
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 8));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 21));
		ProfileElementComparison comp8 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp8.getCalculatedMatchingValue().intValue(), 30);
		
		// supply period in the middle of demand period
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 3));
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 8));
		ProfileElementComparison comp9 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp9.getCalculatedMatchingValue().intValue(), 50);
		
		// no supply end date, start date after demand start
		supplyProfile.setProfileStartDate(new LocalDate(2015, 3 , 5));
		supplyProfile.setProfileEndDate(null);
		ProfileElementComparison comp10 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp10.getCalculatedMatchingValue().intValue(), 60);
		
		// no supply start date, end date before demand end
		supplyProfile.setProfileStartDate(null);
		supplyProfile.setProfileEndDate(new LocalDate(2015, 3 , 7));
		ProfileElementComparison comp11 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp11.getCalculatedMatchingValue().intValue(), 60);
		
		// no supply start and end
		supplyProfile.setProfileStartDate(null);
		supplyProfile.setProfileEndDate(null);
		ProfileElementComparison comp12 = service.getProfileElementTimePeriodComparison(demandProfileElement, supplyProfileElement);
		assertEquals(comp12.getCalculatedMatchingValue().intValue(), 100);
		
	}
	//---------------- END TEST getProfileElementTimePeriodComparison --------------------//
	
	//************* TEST getProfileElementAgeComparison **********************************//
	
	// Very basic test because of use queries and container
	// The rest with integration tests
	
	@Test
    public void testProfileElementAgeComparison(){
		
		ProfileMatchingService service = new ProfileMatchingService();
		
		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		ProfileElementUsePredicate supplyProfileElement = new ProfileElementUsePredicate();
		Profile demandProfile = new Profile();
		ProfileElementNumeric demandProfileElement = new ProfileElementNumeric();
		
		supply.setSupplyOwner(supplier);
		supplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
		supplyProfile.setSupplyProfileOwner(supply);
		supplyProfile.setProfileType(ProfileType.PERSON_PROFILE);
		demandProfileElement.setProfileElementOwner(demandProfile);
		supplyProfileElement.setProfileElementOwner(supplyProfile);
		demandProfileElement.setProfileElementType(ProfileElementType.AGE);
		supplyProfileElement.setProfileElementType(ProfileElementType.USE_AGE);
		
		assertNotNull(service);
		assertEquals(supplyProfile.getSupplyProfileOwner(), supply);
		assertEquals(supplyProfile.getActorOwner(), supplier);
		assertEquals(supplyProfileElement.getProfileElementOwner().getActorOwner(), supplier);
		
		// wrong profile element type
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp = service.getProfileElementAgeComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp);
		demandProfileElement.setProfileElementType(ProfileElementType.AGE);
		
		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.AGE);
		ProfileElementComparison comp2 = service.getProfileElementAgeComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp2);
		
		// wrong profile element type again
		supplyProfileElement.setProfileElementType(ProfileElementType.AGE);
		demandProfileElement.setProfileElementType(ProfileElementType.BRANCHE_TAGS);
		ProfileElementComparison comp3 = service.getProfileElementAgeComparison(demandProfileElement, supplyProfileElement);
		assertNull(comp3);
	
	}
	
	//------------- TEST getProfileElementAgeComparison ----------------------------------//
	
}
