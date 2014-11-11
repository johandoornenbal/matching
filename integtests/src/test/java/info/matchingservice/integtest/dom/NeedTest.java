package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;
import info.matchingservice.dom.Need.Vacancies;
import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Need.VacancyProfileElement;
import info.matchingservice.dom.Party.Person;
import info.matchingservice.dom.Party.Persons;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NeedTest extends MatchingIntegrationTest {
    
    @Inject 
    Needs needs;
    
    @Inject 
    Persons persons;
    
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    public static class testNeeds extends NeedTest {
        
        private static final String NEEDDESCRIPTION = "Een behoefte van Frans Hals: Ik zoek hulpschilders";
        private static final String USERNAME = "frans";
        
        Person Frans;
        Need n1;
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            Frans = persons.allPersons().get(0);
            assertThat(n1.getNeedDescription(), is(NEEDDESCRIPTION));
            assertThat(n1.getNeedOwner(), is(Frans));
            assertThat(n1.getOwnedBy(), is(USERNAME));
            assertThat(n1.getVacancies().size(), is(2));
        }
    }
    
    public static class addVacancy extends NeedTest {
        
        private static final String USERNAME = "frans";
        
        Need n1;
        
        @Before
        public void setUp() throws Exception {
            n1 = needs.allNeeds().get(0);
            n1.newVacancy("Test vacancy", "", n1, USERNAME);
        }
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            assertThat(n1.getVacancies().size(), is(3));
            assertThat(n1.getVacancies().last().getVacancyDescription(), is("Test vacancy"));
            assertThat(n1.getVacancies().last().getVacancyOwner(), is(n1));
            assertThat(n1.getVacancies().last().getOwnedBy(), is(USERNAME));
        }
        
    }
    
    public static class testVacancies extends NeedTest {
        
        private static final String VACANCYDESCRIPTION = "Junior hulpschilder";
        private static final String USERNAME = "frans";
        
        Need n1;
        Vacancy v1;
        
        @Test
        public void valuesSet() throws Exception {
            n1 = needs.allNeeds().get(0);
            v1 = n1.getVacancies().last();
            assertThat(v1.getVacancyDescription(), is(VACANCYDESCRIPTION));
            assertThat(v1.getOwnedBy(), is(USERNAME));
            assertThat(v1.getVacancyProfile().size(), is(1));
        }
    }
    
    public static class testAddVacancyProfile extends NeedTest {

        private static final String USERNAME = "frans";
        private static final String TEST_VACANCY_DECRIPTION = "test1357";
        private static final String TEST_VACANCY_MATCHINGTEXT = "testtekst";
        private static final String TEST_VACANCYPROFILEELEMENT_DESCR = "test element";
        Person Frans;
        Need n1;
        Vacancy v1; // new vacancy without vacancyprofile
        Vacancy v2; // has already vacancyprofile
        VacancyProfileElement pe1;
        
        @Before
        public void setUp() throws Exception {
            Frans = persons.allPersons().get(0);
            n1 = needs.allNeeds().get(0);
            v2 = n1.getVacancies().last();
            n1.newVacancy(TEST_VACANCY_DECRIPTION, TEST_VACANCY_MATCHINGTEXT, n1, USERNAME);
            n1 = needs.allNeeds().get(0); // get n1 again
            v1 = n1.getVacancies().last(); // new vacancy
            v1.newVacancyProfileElement(TEST_VACANCYPROFILEELEMENT_DESCR, v1, USERNAME);
            n1 = needs.allNeeds().get(0); // get n1 again
            v1 = n1.getVacancies().last(); // new last vacancy with element
        }
        
        @Test
        public void valuesSet() throws Exception {
            assertThat(n1.getVacancies().size(), is(3));
            assertThat(n1.getVacancies().last().getVacancyDescription(), is(TEST_VACANCY_DECRIPTION));
            assertThat(n1.getVacancies().last().getTestFieldForMatching(), is(TEST_VACANCY_MATCHINGTEXT));
            assertThat(n1.getVacancies().last().getOwnedBy(), is(USERNAME));
            assertThat(v1.getVacancyProfile().size(), is(1));
            assertThat(v1.getVacancyProfile().last().getVacancyProfileElementDescription(), is(TEST_VACANCYPROFILEELEMENT_DESCR));
            assertThat(v1.getVacancyProfile().last().getOwnedBy(), is(USERNAME));
            // tests if action addProfileElement will be hidden. This may alter because some elements may be repeated...
            assertThat(v1.hideNewVacancyProfileElement(TEST_VACANCYPROFILEELEMENT_DESCR, v1), is(true));
            assertThat(v1.validateNewVacancyProfileElement(TEST_VACANCYPROFILEELEMENT_DESCR, v1), is("This vacancy has this element already!"));
        }
        
        @Inject
        Vacancies vacancies;
        
    }
        

}
