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
package info.matchingservice.integtest.dom;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelContributions;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.communicationChannels.TestEmails;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmailTest extends MatchingIntegrationTest {
    
    @Inject
    CommunicationChannels communicationChannels;
    
    @Inject
    Persons persons;

     
    @BeforeClass
    public static void setupTransactionalData() throws Exception {

        scenarioExecution().install(new TeardownFixture());
        scenarioExecution().install(new TestPersons());
        scenarioExecution().install(new TestEmails());
    }
    
    public static class updateEmail extends EmailTest {

        Email em;
        Person p1;

        @Before
        public void setUp() throws Exception {
            //given
            p1 = persons.findPersons("Hals").get(0);
            em = (Email) communicationChannels.findCommunicationChannelByPersonAndType(p1, CommunicationChannelType.EMAIL_HOME).get(0);

        }

        @Test
        public void valuesSet() throws Exception {

            assertThat(em.getEmail(), is("Frans.Hals@Xtalus.com"));

            //when
            em.updateEmail("johan@test123.nl");

            //then
            assertThat(em.getEmail(), is("johan@test123.nl"));
            assertThat(em.getPerson(), is(p1));

        }

        @Inject
        CommunicationChannels communicationChannels;

    }

    public static class createSecondMainEmail extends EmailTest {

        Email em;
        Person p1;

        @Before
        public void setUp() throws Exception {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new TestPersons());
            scenarioExecution().install(new TestEmails());
            //given
            p1 = persons.findPersons("Hals").get(0);
            em = (Email) communicationChannels.findCommunicationChannelByPersonAndType(p1, CommunicationChannelType.EMAIL_MAIN).get(0);

        }

        @Test
        public void valuesSet() throws Exception {

            //given
            assertThat(em.getEmail(), is("frans@example.com"));
            assertThat(em.getType(), is((Object) CommunicationChannelType.EMAIL_MAIN));

            //when, then
            assertThat(communicationChannelContributions.validateCreateEmail(CommunicationChannelType.EMAIL_MAIN, "some@address.com", p1), is("ONE_INSTANCE_AT_MOST"));

        }

        public static class updateMainEmail extends EmailTest {

            Email em;
            Person p1;
            ApplicationUser user = new ApplicationUser();

            @Before
            public void setUp() throws Exception {
                //given
                p1 = persons.findPersons("Hals").get(0);
                em = (Email) communicationChannels.findCommunicationChannelByPersonAndType(p1, CommunicationChannelType.EMAIL_MAIN).get(0);

            }

            @Test
            public void valuesSet() throws Exception {

                //given
                assertThat(em.getEmail(), is("frans@example.com"));
                assertThat(em.getType(), is((Object) CommunicationChannelType.EMAIL_MAIN));

                //when
                em.updateEmail("some.other@email.com");

                //then
                assertThat(em.getEmail(), is("some.other@email.com"));

            }
        }

        public static class hideDeleteMainEmail extends EmailTest {

            Email em;
            Person p1;
            ApplicationUser user = new ApplicationUser();

            @Before
            public void setUp() throws Exception {
                //given
                p1 = persons.findPersons("Hals").get(0);
                em = (Email) communicationChannels.findCommunicationChannelByPersonAndType(p1, CommunicationChannelType.EMAIL_MAIN).get(0);

            }

            @Test
            public void valuesSet() throws Exception {

                //given
                assertThat(em.getEmail(), is("frans@example.com"));
                assertThat(em.getType(), is((Object) CommunicationChannelType.EMAIL_MAIN));

                //when, then
                assertThat(em.hideDeleteCommunicationChannel(true), is(true));

            }


        }

        @Inject
        CommunicationChannels communicationChannels;

        @Inject
        CommunicationChannelContributions communicationChannelContributions;

    }

    
}
