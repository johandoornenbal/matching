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
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.fixture.tag.TagsForFrans;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScript.ExecutionContext;
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
	    
    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
            	executionContext.executeChild(this, new TeardownFixture());
            	executionContext.executeChild(this, new TestSupplyProfiles());
            	executionContext.executeChild(this, new TestSupplyProfileElementsPersonProfiles());
            	executionContext.executeChild(this, new TagsForFrans());
            }
        });
    }
    
    public static class ProfileElementTagWeekDayTest extends ProfileElementTagTest {
    	
    	// given, when
    	
    	@Before
        public void setUp() throws Exception {	
    		gerard = persons.findPersons("Dou").get(0);
    		pf1 = gerard.getCollectSupplies().first().getCollectSupplyProfiles().first();
    		
    		frans = persons.findPersons("Hals").get(0);
    		pf2 = frans.getCollectSupplies().first().getCollectSupplyProfiles().first();
    		
    	}
    	
    	@Test
    	public void profileElementTagTests() throws Exception {
    		
    		//then
    		// should not be hidden because a TagElement type WEEK_DAY is not already created
    		assertThat(pf1.hideCreateWeekDayTagElement(10), is(false));
    		
    		//when
    		// create a TagElement type WEEK_DAY
    		pet = profileElementTags.createProfileElementTag("WEEKDAY_TAGS_ELEMENT", 10, ProfileElementType.WEEKDAY_TAGS, pf1, "frans");
    		
    		//then
    		// should be hidden because a TagElement type WEEK_DAY is created already
    		assertThat(pf1.hideCreateWeekDayTagElement(10), is(true));
    		assertThat(pf1.validateCreateWeekDayTagElement(10), is("ONE_INSTANCE_AT_MOST"));
    		
    		// should be hidden because a TagElement type WEEK_DAY is created already by fixtures for frans
    		assertThat(pf2.hideCreateWeekDayTagElement(10), is(true));
    		
    		//when
    		pet = (ProfileElementTag) pf2.findProfileElementByOwnerProfileAndDescription("WEEKDAY").get(0);
    		
    		//then
    		assertThat(pet.getCollectTagHolders().size(), is(1));
    		assertThat(pet.getCollectTagHolders().first().getTag().getTagDescription(), is("maandag"));
    		// because maandag is already chosen it cannot be chosen again
    		assertThat(pet.validateCreateWeekDayTagHolder("maandag"), is("NO_DOUBLES"));
    		// only weekdays (dutch) are allowed
    		assertThat(pet.validateCreateWeekDayTagHolder("maandagmiddag"), is("NOT_A_WEEKDAY"));
    		
    	}
    	
    }
    
}
