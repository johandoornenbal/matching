package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import info.matchingservice.dom.Party.Person;
import info.matchingservice.dom.Party.Persons;
import info.matchingservice.dom.Party.RoleType;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonTest extends MatchingIntegrationTest {
    
    //TODO: ThisIsYou test and AllOtherPersons test
    
    @Inject
    Persons persons;
    
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    public static class TestPerson extends PersonTest {
        
        private static final String LAST_NAME = "Hals";
        private static final String MIDDLE_NAME = "";
        private static final String FIRST_NAME = "Frans";
        private static final String UNIQUE_ID = "111";
        private static final RoleType ROLE = RoleType.STUDENT;
        private static final String OWNED_BY = "frans";
        
        Person p1;
        
        @Test
        public void valuesSet() throws Exception {
            p1 = persons.allPersons().get(0);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertThat(p1.getUniquePartyId(), is(UNIQUE_ID));
            assertThat(p1.getRole(), is(ROLE));
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            
        }
        
    }
    
    public static class FindPersonTest extends PersonTest {               
        
        @Test
        public void findFrans() throws Exception {
            assertThat(persons.findPersons("Hals").size(), is(1));
        }
        
        @Test
        public void findFransAgain() throws Exception {
            assertThat(persons.findPersonsContains("als").size(), is(1));
        }
        
        @Test
        public void findNothing() throws Exception {
            assertThat(persons.findPersons("*xyz*").size(), is(0));
        }
        
        @Test
        public void findNothingAgain() throws Exception {
            assertThat(persons.findPersons("Hal").size(), is(0));
        }
        
        @Test
        public void findNothingAgain2() throws Exception {
            assertThat(persons.findPersonsContains("xyz").size(), is(0));
        }
    }
    
    public static class hideNewPersonMethod extends PersonTest {
        
        @Test
        public void shouldBeHidden() throws Exception {
            assertThat(persons.hideNewPerson("frans"), is(true));
        }
        
        @Test
        public void shouldNotBeHidden() throws Exception {
            assertThat(persons.hideNewPerson("piet"), is(false));
        }
    }

    public static class NewPerson extends PersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final String UNIQUE_ID = "321";
        private static final RoleType ROLE = RoleType.STUDENT;
        private static final String OWNED_BY = "test1";
        
        Person p1;
        Person p2;
        
        
        @Before
        public void setUp() throws Exception {
            p1=persons.newPerson(UNIQUE_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, ROLE, OWNED_BY);
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));

            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            
            assertThat(p1.getUniquePartyId(), is(UNIQUE_ID));
            
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            
            assertThat(p2.getUniquePartyId(), is(UNIQUE_ID));
            assertThat(p2.getOwnedBy(), is(OWNED_BY));
            
            assertThat(p1,is(p2));
            
        }
        
    }
    
    public static class validateNewPerson extends PersonTest {
        
        @Test
        public void shouldNotBeValidNewPerson() throws Exception {
            assertThat(persons.validateNewPerson("333", "Frans", "van", "Oldenbarneveld", RoleType.STUDENT ,"frans").isEmpty(), is(false));
        }
        
        @Test
        public void shouldBeValidNewPerson() throws Exception {
            assertTrue(persons.validateNewPerson("333", "Johan", "van", "Oldenbarneveld", RoleType.STUDENT, "johan") == null);
        }        
        
    }
    
    public static class thisIsYouTest extends PersonTest {
        
        @Test
        public void shouldBeYou() throws Exception {
          assertThat(persons.thisIsYou("frans").get(0).getUniquePartyId(), is("111"));  
        }
        
        @Test
        public void onlyOneYou() throws Exception {
            assertThat(persons.thisIsYou("frans").size(), is(1));
        }
        
        @Test
        public void notAYou() throws Exception {
            assertThat(persons.thisIsYou("xyz").size(), is(0));
        }
    }
    
    public static class allPersonTest extends PersonTest {
        
        @Test
        public void allPersons() throws Exception {
            assertThat(persons.allPersons().size(), is(5));
        }
        
        @Test
        public void allOtherPersons() throws Exception {
            Person thisIsMe = persons.thisIsYou("frans").get(0);
            assertThat(persons.AllOtherPersons(thisIsMe).size(), is(4));
        }
    }
      
}
