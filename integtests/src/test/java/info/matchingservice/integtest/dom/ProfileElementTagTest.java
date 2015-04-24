package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementTags;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Tags.TagHolder;
import info.matchingservice.dom.Tags.TagHolders;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfileElementTagTest extends MatchingIntegrationTest {
	
	@Inject
	Persons persons;
	
	@Inject
	TagHolders tagholders;
	
	@Inject
	ProfileElementTags profileElementTags;
	
	Person gerard;
	Person frans;
	Profile pf1;
	Profile pf2;
	ProfileElementTag pet;
	TagHolder th;
	
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    public static class ProfileElementTagWeekDayTest extends ProfileElementTagTest {
    	
    	@Before
        public void setUp() throws Exception {	
    		gerard = persons.findPersons("Dou").get(0);
    		pf1 = gerard.getCollectSupplies().first().getCollectSupplyProfiles().first();
    		
    		frans = persons.findPersons("Hals").get(0);
    		pf2 = frans.getCollectSupplies().first().getCollectSupplyProfiles().first();
    		
    	}
    	
    	@Test
    	public void profileElementTagTests() throws Exception {
    		
    		// should not be hidden because a TagElement type WEEK_DAY is not already
    		assertThat(pf1.hideCreateWeekDayTagElement(10), is(false));
    		
    		// create a TagElement type WEEK_DAY
    		pet = profileElementTags.createProfileElementTag("WEEKDAY_TAGS_ELEMENT", 10, ProfileElementType.WEEKDAY_TAGS, pf1, "frans");
    		
    		// should be hidden because a TagElement type WEEK_DAY is created already
    		assertThat(pf1.hideCreateWeekDayTagElement(10), is(true));
    		assertThat(pf1.validateCreateWeekDayTagElement(10), is("ONE_INSTANCE_AT_MOST"));
    		
    		// should be hidden because a TagElement type WEEK_DAY is created already by fixtures for frans
    		assertThat(pf2.hideCreateWeekDayTagElement(10), is(true));
    		
    		pet = (ProfileElementTag) pf2.findProfileElementByOwnerProfileAndDescription("WEEKDAY").get(0);
    		assertThat(pet.getCollectTagHolders().size(), is(1));
    		assertThat(pet.getCollectTagHolders().first().getTag().getTagDescription(), is("maandag"));
    		
    		// because maandag is already chosen it cannot be chosen again
    		assertThat(pet.validateCreateWeekDayTagHolder("maandag"), is("NO_DOUBLES"));
    		// only weekdays (dutch) are allowed
    		assertThat(pet.validateCreateWeekDayTagHolder("maandagmiddag"), is("NOT_A_WEEKDAY"));



    	}
    	
    }
    
}
