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

package info.matchingservice.integtest.dom.assessments;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Assessment.*;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.assessment.AssessmentsForFransFixture;
import info.matchingservice.fixture.assessment.AssessmentsForRembrandtFixture;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.match.ProfileMatchesForTesting;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by jodo on 08/05/15.
 */
public class AssessmentsTest extends MatchingIntegrationTest {

    @Inject
    Persons persons;

    @Inject
    Assessments assessments;

    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new TeardownFixture());
    }

    public static class CreateDemandAssessment extends AssessmentsTest {

        @Before
        public void setupData() {
            runScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new TestPersons());
                    executionContext.executeChild(this, new TestDemandProfiles());
                    executionContext.executeChild(this, new TestSupplyProfiles());
                }
            });
        }

        Person assessmentOwner;
        Person assessmentTargetOwner;
        Demand assessmentTarget;
        String ownedBy;

        private static final String DESCRIPTION = "Test on demand";
        private static final String FEEDBACK = "TEST FEEDBACK";

        @Test
        public void valuesSet() throws Exception {

            //given
            assessmentOwner = persons.findPersons("Hals").get(0);
            assessmentTargetOwner = persons.findPersons("Rijn").get(0);
            assessmentTarget = assessmentTargetOwner.getDemands().first();
            ownedBy = "frans";

            //when
            DemandFeedback demandFeedback = (DemandFeedback) assessments.createDemandAssessment(assessmentTarget, assessmentOwner, DESCRIPTION, FEEDBACK, ownedBy);

            //then
            assertThat(demandFeedback.getOwnedBy(), is(ownedBy));
            assertThat(demandFeedback.getAssessmentDescription(), is(DESCRIPTION));
            assertThat(demandFeedback.getAssessmentOwnerActor(), is((Object) assessmentOwner));
            assertThat(demandFeedback.getTargetOfAssessment(), is((Object) assessmentTarget));
            assertThat(demandFeedback.getTargetOwnerActor(), is((Object) assessmentTargetOwner));
            assertThat(demandFeedback.getFeedback(), is(FEEDBACK));
        }

    }

    public static class CreateSupplyAssessment extends AssessmentsTest {

        @Before
        public void setupData() {
            runScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    scenarioExecution().install(new TeardownFixture());
                    executionContext.executeChild(this, new TestPersons());
                    executionContext.executeChild(this, new TestDemandProfiles());
                    executionContext.executeChild(this, new TestSupplyProfiles());
                }
            });
        }

        Person assessmentOwner;
        Person assessmentTargetOwner;
        Supply assessmentTarget;
        String ownedBy;

        private static final String DESCRIPTION = "Test on supply";
        private static final String FEEDBACK = "TEST FEEDBACK";

        @Test
        public void valuesSet() throws Exception {

            //given
            assessmentOwner = persons.findPersons("Hals").get(0);
            assessmentTargetOwner = persons.findPersons("Rijn").get(0);
            assessmentTarget = assessmentTargetOwner.getSupplies().first();
            ownedBy = "frans";

            //when
            SupplyFeedback supplyFeedback = (SupplyFeedback) assessments.createSupplyAssessment(assessmentTarget, assessmentOwner, DESCRIPTION, FEEDBACK, ownedBy);

            //then
            assertThat(supplyFeedback.getOwnedBy(), is(ownedBy));
            assertThat(supplyFeedback.getAssessmentDescription(), is(DESCRIPTION));
            assertThat(supplyFeedback.getAssessmentOwnerActor(), is((Object) assessmentOwner));
            assertThat(supplyFeedback.getTargetOfAssessment(), is((Object) assessmentTarget));
            assertThat(supplyFeedback.getTargetOwnerActor(), is((Object) assessmentTargetOwner));
            assertThat(supplyFeedback.getFeedback(), is(FEEDBACK));
        }

    }

    public static class CreateProfileAssessment extends AssessmentsTest {

        @Before
        public void setupData() {
            runScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    scenarioExecution().install(new TeardownFixture());
                    executionContext.executeChild(this, new TestPersons());
                    executionContext.executeChild(this, new TestDemandProfiles());
                    executionContext.executeChild(this, new TestSupplyProfiles());
                }
            });
        }

        Person assessmentOwner;
        Person assessmentTargetOwner;
        Profile assessmentTarget;
        String ownedBy;

        private static final String DESCRIPTION = "Test on supply";
        private static final String FEEDBACK = "TEST FEEDBACK";

        @Test
        public void valuesSet() throws Exception {

            //given
            assessmentOwner = persons.findPersons("Hals").get(0);
            assessmentTargetOwner = persons.findPersons("Rijn").get(0);
            assessmentTarget = assessmentTargetOwner.getSupplies().first().getCollectSupplyProfiles().first();
            ownedBy = "frans";

            //when
            ProfileFeedback profileFeedback = (ProfileFeedback) assessments.createProfileAssessment(assessmentTarget, assessmentOwner, DESCRIPTION, FEEDBACK, ownedBy);

            //then
            assertThat(profileFeedback.getOwnedBy(), is(ownedBy));
            assertThat(profileFeedback.getAssessmentDescription(), is(DESCRIPTION));
            assertThat(profileFeedback.getAssessmentOwnerActor(), is((Object) assessmentOwner));
            assertThat(profileFeedback.getTargetOfAssessment(), is((Object) assessmentTarget));
            assertThat(profileFeedback.getTargetOwnerActor(), is((Object) assessmentTargetOwner));
            assertThat(profileFeedback.getFeedback(), is(FEEDBACK));
        }

    }

    public static class CreateProfileMatchAssessment extends AssessmentsTest {

        @Before
        public void setUp() throws Exception {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new ProfileMatchesForTesting());
        }


        Person assessmentOwner;
        Person assessmentTargetOwner;
        ProfileMatch assessmentTarget;
        String ownedBy;

        private static final String DESCRIPTION = "Test on profile match";

        @Test
        public void valuesSet() throws Exception {

            //given
            assessmentOwner = persons.findPersons("Rijn").get(0);
            assessmentTargetOwner = persons.findPersons("Hals").get(0);
            assessmentTarget = assessmentTargetOwner.getSavedMatches().first();
            ownedBy = "rembrandt";

            //when
            ProfileMatchAssessment profileFeedback = (ProfileMatchAssessment) assessments.createProfileMatchAssessment(assessmentTarget, assessmentOwner, DESCRIPTION, ownedBy);

            //then
            assertThat(profileFeedback.getOwnedBy(), is(ownedBy));
            assertThat(profileFeedback.getAssessmentDescription(), is(DESCRIPTION));
            assertThat(profileFeedback.getAssessmentOwnerActor(), is((Object) assessmentOwner));
            assertThat(profileFeedback.getTargetOfAssessment(), is((Object) assessmentTarget));
            assertThat(profileFeedback.getTargetOwnerActor(), is((Object) assessmentTargetOwner));
        }

    }

    public static class AssessmentCollectionsTest extends AssessmentsTest {

        Person frans;

        // given
        @Before
        public void setupData() {
            runScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    scenarioExecution().install(new TeardownFixture());
                    executionContext.executeChild(this, new TestPersons());
                    executionContext.executeChild(this, new TestDemandProfiles());
                    executionContext.executeChild(this, new TestSupplyProfiles());
                    executionContext.executeChild(this, new AssessmentsForFransFixture());
                    executionContext.executeChild(this, new AssessmentsForRembrandtFixture());
                }
            });

            // when
            frans = persons.findPersons("Hals").get(0);
        }


        @Test
        public void valuesSet() throws Exception {

            // then
            assertThat(frans.getAssessmentsReceived().size(), is(1));
            assertThat(frans.getAssessmentsGiven().size(), is(2));

            assertFalse(frans.getAssessmentsReceived().first().getOwnedBy().equals("frans"));
            assertTrue(frans.getAssessmentsGiven().first().getOwnedBy().equals("frans"));
        }

    }

}
