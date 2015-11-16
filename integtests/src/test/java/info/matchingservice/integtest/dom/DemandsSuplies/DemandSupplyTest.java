package info.matchingservice.integtest.dom.DemandsSuplies;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.*;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

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

    
    public static class NewDemand extends DemandSupplyTest {

        @Before
        public void setupData() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new info.matchingservice.fixture.actor.TestPersons());
        }
        
        Demand d1;
        
        private static final String DEMAND_DESCRIPTION = "Dit is de vraagomschrijving";
        private static final Integer WEIGHT = 10;
        private static final DemandSupplyType DEMAND_SUPPLY_TYPE = DemandSupplyType.GENERIC_DEMANDSUPPLY;
        private static final String OWNED_BY = "frans";
        
        @Test
        public void findDemand() throws Exception {

            // given

            // when
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "" , null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), "linkje", OWNED_BY);
            Integer maxindex = demands.allDemands().size() - 1;
            d1 = demands.allDemands().get(maxindex);

            // then
            assertThat(d1.getDescription(), is(DEMAND_DESCRIPTION));
            assertThat(d1.getWeight(), is(WEIGHT));
            assertThat(d1.getDemandType(), is(DEMAND_SUPPLY_TYPE));
            assertThat(d1.getOwner(), is((Actor) persons.findPersons("Hals").get(0)));
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
        public void setupData() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new info.matchingservice.fixture.actor.TestPersons());
        }
        
        @Test
        public void findSupply() throws Exception {

            // given

            // when
            s1=supplies.createSupply(SUPPLY_DESCRIPTION, WEIGHT, null, null, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            Integer maxindex = supplies.allSupplies().size() - 1;
            s1 = supplies.allSupplies().get(maxindex);

            // then
            assertThat(s1.getDescription(), is(SUPPLY_DESCRIPTION));
            assertThat(s1.getWeight(), is(WEIGHT));
            assertThat(s1.getSupplyType(), is(DEMAND_SUPPLY_TYPE));
            assertThat(s1.getOwner(), is((Actor) persons.findPersons("Hals").get(0)));
            assertThat(s1.getOwnedBy(), is(OWNED_BY));

            assertThat(supplies.findSupplyByOwnedByAndType(OWNED_BY,DEMAND_SUPPLY_TYPE).getOwnedBy(), is(OWNED_BY));
            assertThat(supplies.findSupplyByOwnedByAndType(OWNED_BY,DEMAND_SUPPLY_TYPE).getSupplyType(), is(DEMAND_SUPPLY_TYPE));
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
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new info.matchingservice.fixture.actor.TestPersons());
        }
        
        @Test
        public void findProfile() throws Exception {

            // when
            numberOfDemandProfiles=profiles.allDemandProfiles().size();
            numberOfSupplyProfiles=profiles.allSupplyProfiles().size();
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "", null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), "linkje", OWNED_BY);
            d1.createDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, d1, null, OWNED_BY);
            Integer maxindex = demands.allDemands().size() - 1;
            d1 = demands.allDemands().get(maxindex);
            pf1=d1.getProfiles().first();

            // then
            assertThat(pf1.getName(), is(DEMAND_PROFILE_DESCRIPTION));
            assertThat(pf1.getWeight(), is(WEIGHT)); 
            assertThat(pf1.getType(), is(PROFILE_TYPE));
            assertThat(pf1.getDemand(), is(d1));
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
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new info.matchingservice.fixture.actor.TestPersons());
        }
        
        @Test
        public void findProfile() throws Exception {

            // when
            numberOfDemandProfiles=profiles.allDemandProfiles().size();
            numberOfSupplyProfiles=profiles.allSupplyProfiles().size();
            s1=supplies.createSupply(SUPPLY_DESCRIPTION, WEIGHT, null, null, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), OWNED_BY);
            s1.createSupplyProfile(SUPPLY_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, s1, OWNED_BY);

            Integer maxindex = supplies.allSupplies().size() - 1;
            s1 = supplies.allSupplies().get(maxindex);
            pf1=s1.getProfiles().first();

            // then
            assertThat(pf1.getName(), is(SUPPLY_PROFILE_DESCRIPTION));
            assertThat(pf1.getWeight(), is(WEIGHT)); 
            assertThat(pf1.getType(), is(PROFILE_TYPE));
            assertThat(pf1.getSupply(), is(s1));
            assertThat(pf1.getOwnedBy(), is(OWNED_BY));

            // these test show that the profile is added to supplies and not to demands
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles));
            assertThat(profiles.allSupplyProfiles().size(), is(numberOfSupplyProfiles + 1));
        }  
    }

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
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new info.matchingservice.fixture.actor.TestPersons());
            scenarioExecution().install(new TestDemands());
            scenarioExecution().install(new TestSupplies());
            scenarioExecution().install(new TestDemandProfiles());
            scenarioExecution().install(new TestSupplyProfiles());
        }
        
        @Test
        public void deleteDemand() throws Exception {

            // when
            d1=demands.createDemand(DEMAND_DESCRIPTION, "", "", null, null, null, WEIGHT, DEMAND_SUPPLY_TYPE, persons.findPersons("Hals").get(0), "linkje", OWNED_BY);
            numberOfdemands=demands.allDemands().size();
            d1.createDemandProfile(DEMAND_PROFILE_DESCRIPTION, WEIGHT, null, null, PROFILE_TYPE, d1, null, OWNED_BY);
            numberOfDemandProfiles=profiles.allDemandProfiles().size();

            // then
            assertThat(demands.allDemands().size(), is(numberOfdemands));
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles));
            d1.deleteDemand(true);
            assertThat(demands.allDemands().size(), is(numberOfdemands-1));
            assertThat(profiles.allDemandProfiles().size(), is(numberOfDemandProfiles - 1));
        }
        
    }

}
