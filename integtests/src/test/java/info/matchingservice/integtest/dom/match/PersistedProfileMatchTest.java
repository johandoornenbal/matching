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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Match.CandidateStatus;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.match.ProfileMatchesForTesting;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jodo on 06/05/15.
 */
public class PersistedProfileMatchTest extends MatchingIntegrationTest {


    @Inject
    Persons persons;

    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new TeardownFixture());
    }

    // given

    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {
                executionContext.executeChild(this, new ProfileMatchesForTesting());
            }
        });
    }

    public static class profileMatches extends PersistedProfileMatchTest {

        @Test
        public void whenSetUp() throws Exception {
            //when
            Person frans = persons.findPersons("Hals").get(0);

            //then
            assertThat(frans.getDemands().first().getProfiles().first().getCollectPersistedProfileMatches().size(), is(2));
            assertNull(frans.getDemands().first().getProfiles().first().getChosenProfileMatch());

            //when
            // the first persisted profileMatch gets status reserved
            frans.getDemands().first().getProfiles().first().getCollectPersistedProfileMatches().first().setCandidateStatus(CandidateStatus.RESERVED);

            //then
            assertThat(frans.getDemands().first().getProfiles().first().getChosenProfileMatch().getCandidateStatus(), is(CandidateStatus.RESERVED));

            //when
            // the SECOND and LAST persisted profileMatch gets status chosen
            frans.getDemands().first().getProfiles().first().getCollectPersistedProfileMatches().last().setCandidateStatus(CandidateStatus.CHOSEN);

            //then
            //status chosen overrules status reserved
            assertThat(frans.getDemands().first().getProfiles().first().getChosenProfileMatch().getCandidateStatus(), is(CandidateStatus.CHOSEN));
        }

    }

}
