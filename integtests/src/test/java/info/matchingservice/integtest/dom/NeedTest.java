package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Actors;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Demand.Demand;
import info.matchingservice.dom.Demand.DemandProfile;
import info.matchingservice.dom.Demand.DemandProfileElement;
import info.matchingservice.dom.Demand.DemandProfiles;
import info.matchingservice.dom.Demand.PersonNeed;
import info.matchingservice.dom.Demand.PersonNeeds;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NeedTest extends MatchingIntegrationTest {
    
    @Inject 
    PersonNeeds needs;
    
    @Inject 
    Actors persons;
    
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    public static class testNeeds extends NeedTest {
        
        private static final String NEEDDESCRIPTION = "Schilderproject";
        private static final String USERNAME = "frans";
        
        Actor Frans;
        PersonNeed n1;
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            Frans = persons.allActors().get(0);
            assertThat(n1.getDemandDescription(), is(NEEDDESCRIPTION));
            assertThat(n1.getDemandProfileOwner(), is(Frans));
            assertThat(n1.getOwnedBy(), is(USERNAME));
            assertThat(n1.getDemandProfiles().size(), is(1));
        }
    }
    
    public static class addVacancy extends NeedTest {
        
        private static final String USERNAME = "frans";
        
        PersonNeed n1;
        
        @Before
        public void setUp() throws Exception {
            n1 = needs.allNeeds().get(0);
            n1.newVacancyProfile("Test vacancy", n1, USERNAME);
        }
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            assertThat(n1.getDemandProfiles().size(), is(2));
            assertThat(n1.getDemandProfiles().last().getVacancyDescription(), is("Test vacancy"));
            assertThat((PersonNeed) n1.getDemandProfiles().last().getDemandProfileOwner(), is(n1));
            assertThat(n1.getDemandProfiles().last().getOwnedBy(), is(USERNAME));
        }
        
    }
    
    public static class testVacancies extends NeedTest {
        
        private static final String VACANCYDESCRIPTION = "Junior hulpschilder";
        private static final String USERNAME = "frans";
        
        PersonNeed n1;
        DemandProfile v1;
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            v1 = n1.getDemandProfiles().last();
            assertThat(v1.getVacancyDescription(), is(VACANCYDESCRIPTION));
            assertThat(v1.getOwnedBy(), is(USERNAME));
            assertThat(v1.getDemandProfileElement().size(), is(2));
        }
    }
    
    public static class testAddVacancyProfile extends NeedTest {

        private static final String USERNAME = "frans";
        private static final String TEST_VACANCY_DESCRIPTION = "test1357";
        private static final Integer TEST_VACANCY_WEIGHT = 10;
        
        Actor frans;
        PersonNeed n1;
        DemandProfile v1; // new vacancy without vacancyprofile
        DemandProfile v2; // has already vacancyprofile
        DemandProfileElement pe1;
        
        @Before
        public void setUp() throws Exception {
            frans = persons.allActors().get(0);
            n1 = needs.allNeeds().get(0);
            v2 = n1.getDemandProfiles().last();
            n1.newDemandProfile(TEST_VACANCY_DESCRIPTION, TEST_VACANCY_WEIGHT, n1, USERNAME);
            n1 = needs.allNeeds().get(0); // get n1 again
            v1 = n1.getDemandProfiles().last(); // new vacancy
//            v1.newVacancyProfileElement(TEST_VACANCYPROFILEELEMENT_DESCR, v1, USERNAME);
//            n1 = needs.allNeeds().get(0); // get n1 again
//            v1 = n1.getVacancyProfiles().last(); // new last vacancy with element
        }
        
        @Test
        public void valuesSet() throws Exception {
            assertThat(frans.getUniqueActorId(), is("111"));
//            assertThat(n1.getVacancyProfiles().size(), is(2));
//            assertThat(v1.getVacancyDescription(), is("TEST_VACANCY_DESCRIPTION"));
        }
        
        @Inject
        DemandProfiles vacancies;
        
    }
        

}
