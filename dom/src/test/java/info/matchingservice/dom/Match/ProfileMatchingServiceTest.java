package info.matchingservice.dom.Match;

import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementUsePredicate;
import static org.junit.Assert.assertNotNull;

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

	/*
	NOTE: All profileElement comparisons JUnit tests are moved to: ProfileElementTypeTest
	The method is part of the ENUM
	 */

	//************* TEST checkRequiredProfileElements *********************//
	@Test
	public void testCheckRequiredProfileElements() {

		ProfileMatchingService service = new ProfileMatchingService();
		Person supplier = new Person();
		Supply supply = new Supply();
		Profile supplyProfile = new Profile();
		Profile demandProfile = new Profile();

		assertNotNull(service);

		// TODO: afmaken; nu in integtests opgenomen


	}
	//---------------- END TEST checkRequiredProfileElements --------------------//
	

}
