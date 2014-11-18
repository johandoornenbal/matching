package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Actors;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.PersonNeeds;
import info.matchingservice.dom.Need.VacancyProfiles;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfileElement;
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
            assertThat(n1.getNeedDescription(), is(NEEDDESCRIPTION));
            assertThat(n1.getNeedOwner(), is(Frans));
            assertThat(n1.getOwnedBy(), is(USERNAME));
            assertThat(n1.getVacancyProfiles().size(), is(1));
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
            assertThat(n1.getVacancyProfiles().size(), is(2));
            assertThat(n1.getVacancyProfiles().last().getVacancyDescription(), is("Test vacancy"));
            assertThat((PersonNeed) n1.getVacancyProfiles().last().getVacancyOwner(), is(n1));
            assertThat(n1.getVacancyProfiles().last().getOwnedBy(), is(USERNAME));
        }
        
    }
    
    public static class testVacancies extends NeedTest {
        
        private static final String VACANCYDESCRIPTION = "Junior hulpschilder";
        private static final String USERNAME = "frans";
        
        PersonNeed n1;
        VacancyProfile v1;
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            v1 = n1.getVacancyProfiles().last();
            assertThat(v1.getVacancyDescription(), is(VACANCYDESCRIPTION));
            assertThat(v1.getOwnedBy(), is(USERNAME));
            assertThat(v1.getVacancyProfileElement().size(), is(2));
        }
    }
    
    public static class testAddVacancyProfile extends NeedTest {

        private static final String USERNAME = "frans";
        private static final String TEST_VACANCY_DESCRIPTION = "test1357";
        private static final Integer TEST_VACANCY_WEIGHT = 10;
        
        Actor frans;
        PersonNeed n1;
        VacancyProfile v1; // new vacancy without vacancyprofile
        VacancyProfile v2; // has already vacancyprofile
        VacancyProfileElement pe1;
        
        @Before
        public void setUp() throws Exception {
            frans = persons.allActors().get(0);
            n1 = needs.allNeeds().get(0);
            v2 = n1.getVacancyProfiles().last();
            n1.newVacancyProfile(TEST_VACANCY_DESCRIPTION, TEST_VACANCY_WEIGHT, n1, USERNAME);
            n1 = needs.allNeeds().get(0); // get n1 again
            v1 = n1.getVacancyProfiles().last(); // new vacancy
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
        VacancyProfiles vacancies;
        
    }
        

}
