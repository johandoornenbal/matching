package info.matchingservice.integtest.dom;

import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DemandSupplyTest extends MatchingIntegrationTest {
    
    @Inject
    Persons persons;
    
    @Inject
    Demands demands;
    
    @Inject
    Supplies supplies;
    
    @Inject 
    Profiles profiles;
    
    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                executionContext.executeChild(this, new TestPersons());
                
            }
        });
    }
    
    public static class NewDemand extends DemandSupplyTest {
        
        Demand d1;
        
        private static final String DEMAND_DESCRIPTION = "Dit is de vraagomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        
        @Before
        public void setUp() throws Exception {
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "" , null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
        }
        
        @Test
        public void findDemand() throws Exception {
            Integer maxindex = demands.allDemands().size() - 1;
            d1 = demands.allDemands().get(maxindex);
            assertThat(d1.getDemandDescription(), is(DEMAND_DESCRIPTION));
            assertThat(d1.getWeight(), is(WEIGHT));
            assertThat(d1.getDemandType(), is(DEMAND_SUPPLY_TYPE));
            assertThat(d1.getDemandOwner(), is((Actor) persons.findPersons("Hals").get(0)));
            assertThat(d1.getOwnedBy(), is(OWNED_BY));
        }
        
        
    }

    public static class NewSupply extends DemandSupplyTest {
        
        Supply s1;
        
        private static final String SUPPLY_DESCRIPTION = "Dit is de aanbodomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        
        @Before
        public void setUp() throws Exception {
            s1=supplies.createSupply(SUPPLY_DESCRIPTION, WEIGHT, null, null, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
        }
        
        @Test
        public void findDemand() throws Exception {
            Integer maxindex = supplies.allSupplies().size() - 1;
            s1 = supplies.allSupplies().get(maxindex);
            assertThat(s1.getSupplyDescription(), is(SUPPLY_DESCRIPTION));
            assertThat(s1.getWeight(), is(WEIGHT));
            assertThat(s1.getSupplyType(), is(DEMAND_SUPPLY_TYPE));
            assertThat(s1.getSupplyOwner(), is((Actor) persons.findPersons("Hals").get(0)));
            assertThat(s1.getOwnedBy(), is(OWNED_BY));
        }
        
        
    }    
    
    public static class NewDemandAndProfile extends DemandSupplyTest {
        
        Demand d1;
        Profile pf1;
        Integer numberOfDemandProfiles;
        Integer numberOfSupplyProfiles;
        
        private static final String DEMAND_DESCRIPTION = "Dit is de vraagomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        private static final String DEMAND_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;  
        
        @Before
        public void setUp() throws Exception {
            numberOfDemandProfiles=profiles.allDemandProfiles().size();
            numberOfSupplyProfiles=profiles.allSupplyProfiles().size();
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "", null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            d1.createDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, d1, null, OWNED_BY);
        }
        
        @Test
        public void findProfile() throws Exception {
            Integer maxindex = demands.allDemands().size() - 1;
            d1 = demands.allDemands().get(maxindex);
            pf1=d1.getCollectDemandProfiles().first();
            assertThat(pf1.getProfileName(), is(DEMAND_PROFILE_DESCRIPTION));  
            assertThat(pf1.getWeight(), is(WEIGHT)); 
            assertThat(pf1.getProfileType(), is(PROFILE_TYPE));
            assertThat(pf1.getDemandProfileOwner(), is(d1));
            assertThat(pf1.getOwnedBy(), is(OWNED_BY));
            // these test show that the profile is added to demands and not to supplies
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles + 1));
            assertThat(profiles.allSupplyProfiles().size(), is(numberOfSupplyProfiles));
        }
    }
    
    public static class NewSupplyAndProfile extends DemandSupplyTest {
        
        Supply s1;
        Profile pf1;
        Integer numberOfDemandProfiles;
        Integer numberOfSupplyProfiles;
        
        private static final String SUPPLY_DESCRIPTION = "Dit is de aanbodomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        private static final String SUPPLY_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;  
        
        @Before
        public void setUp() throws Exception {
            numberOfDemandProfiles=profiles.allDemandProfiles().size();
            numberOfSupplyProfiles=profiles.allSupplyProfiles().size();
            s1=supplies.createSupply(SUPPLY_DESCRIPTION, WEIGHT, null, null, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            s1.createSupplyProfile(SUPPLY_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, s1, OWNED_BY);
        }
        
        @Test
        public void findProfile() throws Exception {
            Integer maxindex = supplies.allSupplies().size() - 1;
            s1 = supplies.allSupplies().get(maxindex);
            pf1=s1.getCollectSupplyProfiles().first();
            assertThat(pf1.getProfileName(), is(SUPPLY_PROFILE_DESCRIPTION));  
            assertThat(pf1.getWeight(), is(WEIGHT)); 
            assertThat(pf1.getProfileType(), is(PROFILE_TYPE));
            assertThat(pf1.getSupplyProfileOwner(), is(s1));
            assertThat(pf1.getOwnedBy(), is(OWNED_BY));
            // these test show that the profile is added to supplies and not to demands
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles));
            assertThat(profiles.allSupplyProfiles().size(), is(numberOfSupplyProfiles + 1));
        }  
    }
    
//    public static class DeleteSupply extends DemandSupplyTest{
//        
//        Supply s1;
//        Integer numberOfSupplies;
//        Integer numberOfSupplyProfiles;
//        
//        private static final String SUPPLY_DESCRIPTION = "Dit is de aanbodomschrijving";
//        private static final Integer WEIGHT = 10;
//        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
//        private static final String OWNED_BY = "frans";
//        private static final String SUPPLY_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
//        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;  
//        
//        @Before
//        public void setUp() throws Exception {
//            s1=supplies.createSupply(SUPPLY_DESCRIPTION, WEIGHT, null, null, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
//            numberOfSupplies=supplies.allSupplies().size();
//            s1.createSupplyProfile(SUPPLY_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, s1, OWNED_BY);
//            numberOfSupplyProfiles=profiles.allSupplyProfiles().size();
//        }
//        
//        @Test
//        public void deleteSupply() throws Exception {
//            assertThat(supplies.allSupplies().size(), is(numberOfSupplies));
//            assertThat(profiles.allSupplyProfiles().size(), is(numberOfSupplyProfiles));
//            s1.deleteSupply(true);
//            // supply should be deleted
//            assertThat(supplies.allSupplies().size(), is(numberOfSupplies-1));
//            // supply profile should be deleted
//            assertThat(profiles.allSupplyProfiles().size(), is(numberOfSupplyProfiles-1));
//        }
//        
//    }
    
    public static class DeleteDemand extends DemandSupplyTest{
        
        Demand d1;
        Integer numberOfdemands;
        Integer numberOfDemandProfiles;
        
        private static final String DEMAND_DESCRIPTION = "Dit is de vraagomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        private static final String DEMAND_PROFILE_DESCRIPTION = "Dit is de profielomschrijving";
        private static final ProfileType PROFILE_TYPE = ProfileType.PERSON_PROFILE;  
        
        @Before
        public void setUp() throws Exception {
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "", null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            numberOfdemands=demands.allDemands().size();
            d1.createDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, d1, null, OWNED_BY);
            numberOfDemandProfiles=profiles.allDemandProfiles().size();
        }
        
        @Test
        public void deleteDemand() throws Exception {
            assertThat(demands.allDemands().size(), is(numberOfdemands));
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles));
            d1.deleteDemand(true);
            assertThat(demands.allDemands().size(), is(numberOfdemands-1));
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles - 1));
        }
        
    }
    
public static class UpdateDemand extends DemandSupplyTest{
        
        Demand d1;
        UUID unid;
        Integer numberOfdemands;
        Integer numberOfDemandProfiles;
        
        private static final String DEMAND_DESCRIPTION = "Dit is de vraagomschrijving";
        private static final String DEMAND_SUMMARY = "Dit is de samenvatting";
        private static final String DEMAND_STORY = "Dit is het hele verhaal";
        private static final LocalDate START_DATE = new LocalDate(2015, 01, 01);
        private static final LocalDate END_DATE = new LocalDate(2015, 01, 31);
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String IMAGE_URL = "http://some.img.url";
        private static final String OWNED_BY = "frans";

        
        @Before
        public void setUp() throws Exception {
            d1=demands.createDemand(DEMAND_DESCRIPTION, DEMAND_SUMMARY, DEMAND_STORY, null, START_DATE, END_DATE, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            unid = d1.getUniqueItemId();
            assertThat(d1.getDemandDescription(), is(DEMAND_DESCRIPTION));
            assertThat(d1.getUniqueItemId(), is(unid));
            assertThat(d1.getOwnedBy(), is(OWNED_BY));
            d1.updateDemand(DEMAND_DESCRIPTION + "test123", DEMAND_SUMMARY + "test456", DEMAND_STORY + "test789", null, START_DATE, new LocalDate(2015,12,31), IMAGE_URL);
            //unid should not change
            assertThat(d1.getUniqueItemId(), is(unid));
            //owner should not change
            assertThat(d1.getOwnedBy(), is(OWNED_BY));
            assertThat(d1.getDemandDescription(), is("Dit is de vraagomschrijvingtest123"));
            assertThat(d1.getDemandSummary(), is("Dit is de samenvattingtest456"));
            assertThat(d1.getDemandStory(), is("Dit is het hele verhaaltest789"));
            assertThat(d1.getDemandOrSupplyProfileStartDate(), is(START_DATE));
            assertThat(d1.getDemandOrSupplyProfileEndDate(), is(new LocalDate(2015,12,31)));
            assertThat(d1.getImageUrl(), is(IMAGE_URL));
        }
        
        @Test
        public void updateDemand() throws Exception {
            
        }
        
    }
}
