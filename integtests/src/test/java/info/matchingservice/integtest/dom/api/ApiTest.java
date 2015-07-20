/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package info.matchingservice.integtest.dom.api;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiTest extends MatchingIntegrationTest {
    
    @Inject
    Api api;

    @Inject
    Persons persons;
    
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new TeardownFixture());
    }
    
    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {  	
                executionContext.executeChild(this, new TestPersons());
                executionContext.executeChild(this, new TestRoles());
            }
        });
    }


    public static class CreateStudent extends ApiTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
        private static final String PICTURE_LINK = "picturelink";
        
        Person p1;
        Person p2;
        
        
        @Before
        public void setUp() throws Exception {
            p1=api.createStudentApi(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null,PICTURE_LINK);
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertTrue(p1.getIsStudent());
            assertFalse(p1.getIsProfessional());
            assertFalse(p1.getIsPrincipal());
            
            assertThat(p1,is(p2));
            
        }
        
    }

    public static class CreateProfessional extends ApiTest {

        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962,7,16);
        private static final String PICTURE_LINK = "picturelink";

        Person p1;
        Person p2;


        @Before
        public void setUp() throws Exception {
            p1=api.createProfessionalApi(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null,PICTURE_LINK);
        }

        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);

            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertTrue(p1.getIsProfessional());
            assertFalse(p1.getIsStudent());
            assertFalse(p1.getIsPrincipal());

            assertThat(p1, is(p2));

        }

    }

    public static class CreatePrincipal extends ApiTest {

        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962, 7, 16);
        private static final String PICTURE_LINK = "picturelink";

        Person p1;
        Person p2;

        @Before
        public void setUp() throws Exception {
            p1 = api.createPrincipalApi(FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH, null,PICTURE_LINK);
        }

        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);

            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertTrue(p1.getIsPrincipal());
            assertFalse(p1.getIsStudent());
            assertFalse(p1.getIsProfessional());

            assertThat(p1, is(p2));

        }
    }
    

}
