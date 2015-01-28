package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementDropDownAndText;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfileTest extends MatchingIntegrationTest {
    
    @Inject
    Persons persons;
    
    @Inject
    Demands demands;
    
    @Inject
    Supplies supplies;
    
    @Inject 
    Profiles profiles;
    
    @Inject
    DropDownForProfileElements dropDowns;
    
    @Inject
    DomainObjectContainer container;
    
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    //Dit moet een test worden voor passie maar ik kan niet de methode newPassion() los testen lijkt...
    public static class SupplyProfileWithPassion extends ProfileTest{
    	Person p1;
    	Supply s1;
    	Profile pf1;
    	ProfileElementText passion;
    	
    	private static final String SUPPLY_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
    	private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;
    	private static final Integer WEIGHT = 10;
    	private static final String ELEMENT_PASSIONVALUE = "Dit is mijn passie";
    	private static final Integer ELEMENT_INTVALUE = 5;
    	
    	@Before
    	public void setUp() throws Exception {
    		p1=persons.newPerson("4321", "TESTvn", "", "TESTan", new LocalDate(1962,7,16));
    		s1=supplies.newSupply("TESTSUP", 10, DemandSupplyType.PERSON_DEMANDSUPPLY, p1, container.getUser().getName());
    		pf1=s1.newPersonSupplyProfile();
    		pf1.newPassion(ELEMENT_PASSIONVALUE, ELEMENT_INTVALUE);
    	}
    	
    	@Test
    	public void newSupplyWithPassion() throws Exception {
    		assertThat(p1.getFirstName(), is("TESTvn"));
    		assertThat(s1.getOwnedBy(), is("tester"));
    		assertThat(s1.getSupplyOwner().toString(), is(p1.toString()) );
    		assertThat(pf1.getActorOwner().toString(), is(p1.toString()) );
    		assertThat(pf1.getProfileType(), is(ProfileType.PERSON_PROFILE));
//    		assertThat(pf1.getProfileElement().isEmpty(), is(false));
    	}
    	
    }
    
    public static class NewProfileWithElements extends ProfileTest {
        
        Demand d1;
        Profile pf1;
        ProfileElementDropDown dropdown;
        ProfileElementNumeric numeric;
        ProfileElementText text;
        ProfileElementDropDownAndText dropdownAndText;
        DropDownForProfileElement dropDownValue;
        
        private static final String DEMAND_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;
        private static final Integer WEIGHT = 10;
        private static final String ELEMENT_DESCRIPTION = "Element omschrijving";
        private static final String ELEMENT_TEXTVALUE = "Tekst op element";
        private static final Integer ELEMENT_INTVALUE = 5;
        
        
        @Before
        public void setUp() throws Exception {
            d1=demands.allDemands().get(0);
            dropDownValue=dropDowns.allProfileElementDropDowns().get(0);
            pf1=d1.newDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, PROFILE_TYPE, d1, d1.getOwnedBy());
//            dropdown=pf1.newProfileElementDropDown(WEIGHT, dropDownValue);
            numeric=pf1.newProfileElementNumeric(ELEMENT_DESCRIPTION, WEIGHT, ELEMENT_INTVALUE);
            text=pf1.newProfileElementText(ELEMENT_DESCRIPTION, WEIGHT, ELEMENT_TEXTVALUE);
            dropdownAndText=pf1.newProfileElementDropDownAndText(ELEMENT_DESCRIPTION, WEIGHT, dropDownValue, ELEMENT_TEXTVALUE);
        }
        
        @Test
        public void newProfile() throws Exception {
//            assertThat(dropdown.getProfileElementDescription(), is(ELEMENT_DESCRIPTION));
            assertThat(numeric.getProfileElementDescription(), is(ELEMENT_DESCRIPTION));
            assertThat(text.getProfileElementDescription(), is(ELEMENT_DESCRIPTION));
            assertThat(dropdownAndText.getProfileElementDescription(), is(ELEMENT_DESCRIPTION));
            // the creator owns the element
//            assertThat(dropdown.getOwnedBy(), is("tester"));
            assertThat(numeric.getOwnedBy(), is("tester"));
            assertThat(text.getOwnedBy(), is("tester"));
            assertThat(dropdownAndText.getOwnedBy(), is("tester"));
            // the profile is ProfileElementOwner
//            assertThat(dropdown.getProfileElementOwner(), is(pf1));
            assertThat(numeric.getProfileElementOwner(), is(pf1));
            assertThat(text.getProfileElementOwner(), is(pf1));
            assertThat(dropdownAndText.getProfileElementOwner(), is(pf1));
            // test display values (Strings)
//            assertThat(dropdown.getDisplayValue(), is("ijverig"));
            assertThat(numeric.getDisplayValue(), is("5"));
            assertThat(text.getDisplayValue(), is(ELEMENT_TEXTVALUE));
            assertThat(dropdownAndText.getDisplayValue(), is("ijverig - " + ELEMENT_TEXTVALUE));
            // P Elements Types test TODO: this will change..
//            assertThat(dropdown.getProfileElementType(), is(ProfileElementType.QUALITY));
            assertThat(numeric.getProfileElementType(), is(ProfileElementType.NUMERIC));
            assertThat(text.getProfileElementType(), is(ProfileElementType.TEXT));
            assertThat(dropdownAndText.getProfileElementType(), is(ProfileElementType.QUALITY));
            
//            assertThat(dropdown.getWeight(), is(WEIGHT));
            assertThat(numeric.getWeight(), is(WEIGHT));
            assertThat(text.getWeight(), is(WEIGHT));
            assertThat(dropdownAndText.getWeight(), is(WEIGHT));
            
//            assertThat(dropdown.default0EditProfileDescription(), is(dropdown.getProfileElementDescription()));
            assertThat(numeric.default0EditProfileDescription(), is(ELEMENT_DESCRIPTION));
            assertThat(text.default0EditProfileDescription(), is(text.getProfileElementDescription()));
            assertThat(dropdownAndText.default0EditProfileDescription(), is(dropdownAndText.getProfileElementDescription()));
            
//            assertThat(dropdown.default0EditWeight(), is(WEIGHT));
            assertThat(numeric.default0EditWeight(), is(WEIGHT));
            assertThat(text.default0EditWeight(), is(WEIGHT));
            assertThat(dropdownAndText.default0EditWeight(), is(dropdownAndText.getWeight()));
            
//            assertThat(dropdown.getDropDownValue(), is(dropDownValue));
            assertThat(numeric.getNumericValue(), is(ELEMENT_INTVALUE));
            assertThat(text.getTextValue(), is(ELEMENT_TEXTVALUE));
            assertThat(dropdownAndText.getOptionalDropDownValue(), is(dropDownValue));
            assertThat(dropdownAndText.getText(), is(ELEMENT_TEXTVALUE));
        }     
    }
    
    public static class Deletions extends ProfileTest {
        
        Demand d1;
        Profile pf1;
        ProfileElementDropDown dropdown;
        ProfileElementNumeric numeric;
        ProfileElementText text;
        ProfileElementDropDownAndText dropdownAndText;
        DropDownForProfileElement dropDownValue;
        Integer numberOfDropDownElements;
        Integer numberOfNumericElements;
        Integer numberOfTextElements;
        Integer numberOfDropDownAndTextElements;
        
        private static final String DEMAND_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;
        private static final Integer WEIGHT = 10;
        private static final String ELEMENT_DESCRIPTION = "Element omschrijving";
        private static final String ELEMENT_TEXTVALUE = "Tekst op element";
        private static final Integer ELEMENT_INTVALUE = 5;
        
        
        @Before
        public void setUp() throws Exception {
            d1=demands.allDemands().get(0);
            dropDownValue=dropDowns.allProfileElementDropDowns().get(0);
            pf1=d1.newDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, PROFILE_TYPE, d1, d1.getOwnedBy());
//            dropdown=pf1.newProfileElementDropDown(ELEMENT_DESCRIPTION, WEIGHT, dropDownValue);
            numeric=pf1.newProfileElementNumeric(ELEMENT_DESCRIPTION, WEIGHT, ELEMENT_INTVALUE);
            text=pf1.newProfileElementText(ELEMENT_DESCRIPTION, WEIGHT, ELEMENT_TEXTVALUE);
            dropdownAndText=pf1.newProfileElementDropDownAndText(ELEMENT_DESCRIPTION, WEIGHT, dropDownValue, ELEMENT_TEXTVALUE);
            numberOfDropDownElements = container().allInstances(ProfileElementDropDown.class).size();
            numberOfNumericElements=container().allInstances(ProfileElementNumeric.class).size();
            numberOfTextElements=container().allInstances(ProfileElementText.class).size();
            numberOfDropDownAndTextElements=container().allInstances(ProfileElementDropDownAndText.class).size();
        }
        
        @Test
        public void deletions() throws Exception {
//            dropdown.DeleteProfileElement(true);
//            assertThat(container().allInstances(ProfileElementDropDown.class).size(), is(numberOfDropDownElements-1));
            numeric.DeleteProfileElement(true);
            assertThat(container().allInstances(ProfileElementNumeric.class).size(), is(numberOfNumericElements-1));
            text.DeleteProfileElement(true);
            assertThat(container().allInstances(ProfileElementText.class).size(), is(numberOfTextElements-1));
            dropdownAndText.DeleteProfileElement(true);
            assertThat(container().allInstances(ProfileElementDropDownAndText.class).size(), is(numberOfDropDownAndTextElements-1));
        }
        
    }
    
    /*
     * Actually we test the methods on ProfileElement, parent of ProfileElementDropDown etc.
     */
    public static class EditProfileElement extends ProfileTest {
        
        Demand d1;
        Profile pf1;
        ProfileElementDropDown dropdown;
        DropDownForProfileElement dropDownValue;
        
        private static final String DEMAND_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final String EDITED_DEMAND_PROFILE_DESCRIPTION = "Dit is de gewijzgde profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;
        private static final Integer WEIGHT = 10;
        private static final Integer EDITED_WEIGHT = 11;
        private static final String ELEMENT_DESCRIPTION = "Element omschrijving";
        
        @Before
        public void setUp() throws Exception {
            d1=demands.allDemands().get(0);
            dropDownValue=dropDowns.allProfileElementDropDowns().get(0);
            pf1=d1.newDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, PROFILE_TYPE, d1, d1.getOwnedBy());
//            dropdown=pf1.newProfileElementDropDown(ELEMENT_DESCRIPTION, WEIGHT, dropDownValue);
        }
        
        public void edit() throws Exception {
//            assertThat(dropdown.getProfileElementDescription(), is(DEMAND_PROFILE_DESCRIPTION));
//            assertThat(dropdown.default0EditProfileDescription(), is(DEMAND_PROFILE_DESCRIPTION));
//            dropdown.EditProfileDescription(EDITED_DEMAND_PROFILE_DESCRIPTION);
//            assertThat(dropdown.getProfileElementDescription(), is(EDITED_DEMAND_PROFILE_DESCRIPTION));
//            assertThat(dropdown.getWeight(), is(WEIGHT));
//            assertThat(dropdown.default0EditWeight(), is(WEIGHT));
//            dropdown.EditWeight(EDITED_WEIGHT);
//            assertThat(dropdown.getWeight(), is(EDITED_WEIGHT));
        }
    }
}
