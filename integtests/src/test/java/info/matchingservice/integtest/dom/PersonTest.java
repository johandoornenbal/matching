/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.joda.time.LocalDate;
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
        private static final String OWNED_BY = "frans";
        
        Person p1;
        
        @Test
        public void valuesSet() throws Exception {
            p1 = persons.allPersons().get(0);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            assertThat(p1.getIsStudent(), is(false));
            assertThat(p1.getIsProfessional(), is(true));
            assertThat(p1.getIsPrincipal(), is(true));
        }
        
    }
    
    public static class FindPersonTest extends PersonTest {               
        
        @Test
        public void findFrans() throws Exception {
            assertThat(persons.findPersons("Hals").size(), is(1));
        }
        
        @Test
        public void findFransAgain() throws Exception {
            assertThat(persons.findPersons("als").size(), is(1));
        }
        
        @Test
        public void findNothing() throws Exception {
            assertThat(persons.findPersons("*xyz*").size(), is(0));
        }
        
        @Test
        public void findFransAgain2() throws Exception {
            assertThat(persons.findPersons("Hal").size(), is(1));
        }
        
        @Test
        public void findNothingAgain2() throws Exception {
            assertThat(persons.findPersons("xyz").size(), is(0));
        }
    }
    
    
    public static class hideNewPersonMethod extends PersonTest {
        
        @Test
        public void shouldBeHidden() throws Exception {
            assertThat(persons.hideCreatePerson("frans"), is(true));
        }
        
        @Test
        public void shouldNotBeHidden() throws Exception {
            assertThat(persons.hideCreatePerson("piet"), is(false));
        }
    }

    public static class NewPerson extends PersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
        private static final String OWNED_BY = "test1";
        
        Person p1;
        Person p2;
        
        
        @Before
        public void setUp() throws Exception {
            p1=persons.createPerson(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null, OWNED_BY);
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));

            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            
//            assertThat(p1.getUniqueActorId(), is(UNIQUE_ID));
            
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            
//            assertThat(p2.getUniqueActorId(), is(UNIQUE_ID));
            assertThat(p2.getOwnedBy(), is(OWNED_BY));
            
            assertThat(p1,is(p2));
            
        }
        
    }
    
    public static class validateNewPerson extends PersonTest {
        
        @Test
        public void shouldNotBeValidNewPerson() throws Exception {
            assertThat(persons.validateCreatePerson("Frans", "van", "Oldenbarneveld", new LocalDate(1962,7,16), "frans", null).isEmpty(), is(false));
        }
        
        @Test
        public void shouldBeValidNewPerson() throws Exception {
            assertTrue(persons.validateCreatePerson("Johan", "van", "Oldenbarneveld", new LocalDate(1962,7,16), "johan", null) == null);
        }        
        
    }
    
    public static class thisIsYouTest extends PersonTest {
        
        @Test
        public void shouldBeYou() throws Exception {
//          assertThat(persons.thisIsYou("frans").get(0).getUniqueActorId(), is("111"));  
        }
        
        @Test
        public void onlyOneYou() throws Exception {
            assertThat(persons.activePerson("frans").size(), is(1));
        }
        
        @Test
        public void notAYou() throws Exception {
            assertThat(persons.activePerson("xyz").size(), is(0));
        }
    }
    
    public static class allPersonTest extends PersonTest {
        
        @Test
        public void allPersons() throws Exception {
            assertThat(persons.allPersons().size(), is(5));
        }
        
        @Test
        public void allOtherPersons() throws Exception {
            Person thisIsMe = persons.activePerson("frans").get(0);
            assertThat(persons.AllOtherPersons(thisIsMe).size(), is(4));
        }
    }
   
    public static class addRole extends PersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
//        private static final String UNIQUE_ID = "321";
        private static final String OWNED_BY = "test1";
        
        Person p1; // new person with all roles
        Person p2; // new person with no roles
        Person p3; // "FRANS HALS" - first testuser
        
        @Before
        public void setUp() throws Exception {
            p1=persons.createPerson(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null, OWNED_BY);
            p2=persons.createPerson(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null, "different_owner");
            p3=persons.allPersons().get(0);
            p1.addRoleStudent(OWNED_BY);
            p1.addRoleProfessional(OWNED_BY);
            p1.addRolePrincipal(OWNED_BY);
        }
        
        @Test
        public void hasRoleStudent() throws Exception {
            assertThat(p1.getIsStudent(), is(true));
            assertThat(p1.getIsProfessional(), is(true));
            assertThat(p1.getIsPrincipal(), is(true));
            assertThat(p2.getIsStudent(), is(false));
            assertThat(p2.getIsProfessional(), is(false));
            assertThat(p2.getIsPrincipal(), is(false));
            assertThat(p3.getIsStudent(), is(false));
            assertThat(p3.getIsProfessional(), is(true));
            assertThat(p3.getIsPrincipal(), is(true));
        }
        
    }
    
    public static class deleteRole extends PersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
//        private static final String UNIQUE_ID = "321";
        private static final String OWNED_BY = "test1";
        
        Person p1; // new person with all roles
        
        @Before
        public void setUp() throws Exception {
            p1=persons.createPerson(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null, OWNED_BY);
            p1.addRoleStudent(OWNED_BY);
            p1.addRoleProfessional(OWNED_BY);
            p1.addRolePrincipal(OWNED_BY);
        }
        
        @Test
        public void hasNotRoleAnymore() throws Exception {
            assertThat(p1.getIsStudent(), is(true));
            p1.deleteRoleStudent(OWNED_BY);
            assertThat(p1.getIsStudent(), is(false));
            assertThat(p1.getIsProfessional(), is(true));
            p1.deleteRoleProfessional(OWNED_BY);
            assertThat(p1.getIsProfessional(), is(false));
            assertThat(p1.getIsPrincipal(), is(true));
            p1.deleteRolePrincipal(OWNED_BY);
            assertThat(p1.getIsPrincipal(), is(false));
        }
        
    }

    public static class hideAddAndDeleteRole extends PersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
//        private static final String UNIQUE_ID = "321";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
        private static final String OWNED_BY = "test1";
        
        Person p1; // new person with all roles
        
        @Before
        public void setUp() throws Exception {
            p1=persons.createPerson(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null, OWNED_BY);
            p1.addRoleStudent(OWNED_BY);
            p1.addRoleProfessional(OWNED_BY);
            p1.addRolePrincipal(OWNED_BY);
        }
        
        @Test
        public void addRoleShouldBeHiddenFirst() throws Exception {
            assertThat(p1.hideAddRoleStudent(p1, OWNED_BY), is(true));
            assertThat(p1.hideDeleteRoleStudent(p1, OWNED_BY), is(false));
            p1.deleteRoleStudent(OWNED_BY);
            assertThat(p1.hideAddRoleStudent(p1, OWNED_BY), is(false));
            assertThat(p1.hideDeleteRoleStudent(p1, OWNED_BY), is(true));
            
            assertThat(p1.hideAddRoleProfessional(p1, OWNED_BY), is(true));
            assertThat(p1.hideDeleteRoleProfessional(p1, OWNED_BY), is(false));
            p1.deleteRoleProfessional(OWNED_BY);
            assertThat(p1.hideAddRoleProfessional(p1, OWNED_BY), is(false));
            assertThat(p1.hideDeleteRoleProfessional(p1, OWNED_BY), is(true));
 
            assertThat(p1.hideAddRolePrincipal(p1, OWNED_BY), is(true));
            assertThat(p1.hideDeleteRolePrincipal(p1, OWNED_BY), is(false));           
            p1.deleteRolePrincipal(OWNED_BY);
            assertThat(p1.hideAddRolePrincipal(p1, OWNED_BY), is(false));
            assertThat(p1.hideDeleteRolePrincipal(p1, OWNED_BY), is(true));   
        }
        
    }   
}
