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

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiTest extends MatchingIntegrationTest {
    
    @Inject
    Api api;

    @Inject
    Persons persons;

    private static final String LAST_NAME = "Test1";
    private static final String MIDDLE_NAME = "van der";
    private static final String FIRST_NAME = "T.";
    private static final LocalDate DATE_OF_BIRTH = new LocalDate(1962, 7, 16);
    private static final String PICTURE_LINK = "picturelink";
    private static final String USERNAME = "username";
    private static final String MAIN_ADDRES = "address";
    private static final String MAIN_POSTALCODE = "1234AB";
    private static final String MAIN_TOWN = "Town";
    private static final String MAIN_PHONE = "0123456789";
    private static final String DATE_OF_BIRTH_STRING = "2000-12-31";
    
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

    public static class CreateNewPersonTest extends ApiTest {

        Person newPerson;

        @Test
        public void create() throws Exception {

            newPerson = api.createNewPerson(
                FIRST_NAME, MIDDLE_NAME, LAST_NAME, DATE_OF_BIRTH_STRING, PICTURE_LINK,
                    PersonRoleType.STUDENT, USERNAME, MAIN_ADDRES, MAIN_POSTALCODE, MAIN_TOWN, MAIN_PHONE
            );

            // person created
            assertThat(newPerson.getFirstName(), is(FIRST_NAME));
            assertThat(newPerson.getLastName(), is(LAST_NAME));
            assertThat(newPerson.getMiddleName(), is(MIDDLE_NAME));
            assertThat(newPerson.getDateOfBirth().toString(), is(DATE_OF_BIRTH_STRING));
            assertThat(newPerson.getImageUrl(), is(PICTURE_LINK));
            assertThat(newPerson.getOwnedBy(), is(USERNAME));
            assertFalse(newPerson.getIsPrincipal());
            assertTrue(newPerson.getIsStudent());
            assertFalse(newPerson.getIsProfessional());

            // personsupply and profile created
            assertThat(newPerson.getSupplies().size(), is(1));
            assertThat(newPerson.getSupplies().first().getSupplyType(), is(DemandSupplyType.PERSON_DEMANDSUPPLY));
            assertThat(newPerson.getSupplies().first().getProfiles().size(), is(1));
            assertThat(newPerson.getSupplies().first().getProfiles().first().getType(), is(ProfileType.PERSON_PROFILE));

            // phone and address created
            assertThat(communicationChannelsRepo.
                    findCommunicationChannelByPersonAndType(
                            newPerson,
                            CommunicationChannelType.ADDRESS_MAIN).size(),
                    is(1));
            assertThat(communicationChannelsRepo.
                            findCommunicationChannelByPersonAndType(
                                    newPerson,
                                    CommunicationChannelType.PHONE_MAIN).size(),
                    is(1));

            Address address = (Address) communicationChannelsRepo.findCommunicationChannelByPersonAndType(
                    newPerson,
                    CommunicationChannelType.ADDRESS_MAIN)
                    .get(0);
            assertThat(address.getAddress(), is(MAIN_ADDRES));
            assertThat(address.getPostalCode(), is(MAIN_POSTALCODE));
            assertThat(address.getTown(), is(MAIN_TOWN));

            Phone phone = (Phone) communicationChannelsRepo.findCommunicationChannelByPersonAndType(
                            newPerson,
                            CommunicationChannelType.PHONE_MAIN)
                    .get(0);
            assertThat(phone.getPhoneNumber(), is(MAIN_PHONE));

        }

        @Inject
        private CommunicationChannels communicationChannelsRepo;

    }



}
