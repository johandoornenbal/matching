/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
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
package info.matchingservice.dom.Match;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;
import info.matchingservice.dom.Profile.Profile;
import org.apache.isis.applib.query.Query;
import org.assertj.core.api.Assertions;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProfileMatchesTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    ProfileMatches profileMatches;

    @Before
    public void setup() {
    	profileMatches = new ProfileMatches() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<ProfileMatch> allInstances() {
                finderInteraction = new FinderInteraction(null, FinderMethod.ALL_INSTANCES);
                return null;
            }

            @Override
            protected <T> List<T> allMatches(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.ALL_MATCHES);
                return null;
            }
        };
    }



    public static class FindProfileMatchesByDemandProfile extends ProfileMatchesTest {

    	@Mock
    	Profile demandprofile;

        @Mock
        CandidateStatus candidateStatus;
    	
        @Test
        public void happyCase() {

        	
        	
        	profileMatches.findProfileMatchesByDemandProfile(demandprofile);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Match.ProfileMatch.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileMatchesByDemandProfile"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("profile"), is((Object) demandprofile));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));

            profileMatches.findProfileMatchesByDemandProfileAndStatus(demandprofile, candidateStatus);
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Match.ProfileMatch.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileMatchesByDemandProfileAndStatus"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("profile"), is((Object) demandprofile));
            assertThat(finderInteraction.getArgumentsByParameterName().get("candidateStatus"), is((Object) candidateStatus));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));

        }

    }

    public static class FindProfileMatchesBySupplyCandidate extends ProfileMatchesTest {

        @Mock
        Actor supplyCandidate;

        @Test
        public void happyCase() {
            profileMatches.collectProfileMatches(supplyCandidate);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Match.ProfileMatch.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileMatchesBySupplyCandidate"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("supplyCandidate"), is((Object) supplyCandidate));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }


    }
 
}