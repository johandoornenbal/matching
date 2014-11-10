package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;
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
            n1.newVacancy("Test vacancy", n1, USERNAME);
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
        

}
