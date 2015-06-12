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

package info.matchingservice.integtest.dom.match;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.RequiredProfileElementRole;
import info.matchingservice.fixture.MatchingDemoFixture;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jodo on 06/05/15.
 */
public class ProfileMatchingServiceTest extends MatchingIntegrationTest {


    @Inject
    Persons persons;

    @Inject
    ProfileElements profileElements;

    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new TeardownFixture());
    }

    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                executionContext.executeChild(this, new MatchingDemoFixture());
            }
        });
    }

    public static class CheckRequiredProfileElements extends ProfileMatchingServiceTest {

        Person frans;
        Person gerard;
        Profile demandProfile;
        RequiredProfileElementRole requiredProfileElementRole;
        List<ProfileComparison> profileComparisons;

        @Before
        public void setUp() {
            frans = persons.findPersons("Hals").get(0);
            gerard = persons.findPersons("Dou").get(0);
            profileComparisons = frans.getCollectDemands().first().getCollectDemandProfiles().first().actionCollectProfileComparisons();
            demandProfile = frans.getCollectDemands().first().getCollectDemandProfiles().first();
        }

        @Test
        public void whenSetUp() throws Exception {

            // given
            assertTrue(gerard.getIsStudent());
            assertTrue(gerard.getIsProfessional());
            assertFalse(gerard.getIsPrincipal());
            assertThat(demandProfile.getProfileName(), is("Gezocht: meelevende persoonlijkheden"));
            assertThat(profileComparisons.size(), is(4));

            // when adding a requiredProfileElementRole with check for student
            demandProfile.createRequiredProfileElementRole(1, true, false, false);
            // then
            profileComparisons = frans.getCollectDemands().first().getCollectDemandProfiles().first().actionCollectProfileComparisons();
            assertThat(profileComparisons.size(), is(2));

            // when checking out student on requiredProfileElementRole
            requiredProfileElementRole = (RequiredProfileElementRole) profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0);
            requiredProfileElementRole.setStudent(false);
            // then
            profileComparisons = frans.getCollectDemands().first().getCollectDemandProfiles().first().actionCollectProfileComparisons();
            assertThat(profileComparisons.size(), is(0));

            // when checking professional on requiredProfileElementRole
            requiredProfileElementRole = (RequiredProfileElementRole) profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0);
            requiredProfileElementRole.setProfessional(true);
            // then
            profileComparisons = frans.getCollectDemands().first().getCollectDemandProfiles().first().actionCollectProfileComparisons();
            assertThat(profileComparisons.size(), is(3));

            // when checking professional and student on requiredProfileElementRole
            requiredProfileElementRole = (RequiredProfileElementRole) profileElements.findProfileElementByOwnerProfileAndDescription("REQUIRED_ROLE_ELEMENT", demandProfile).get(0);
            requiredProfileElementRole.setStudent(true);
            // then
            profileComparisons = frans.getCollectDemands().first().getCollectDemandProfiles().first().actionCollectProfileComparisons();
            assertThat(profileComparisons.size(), is(4));

        }

    }

}
