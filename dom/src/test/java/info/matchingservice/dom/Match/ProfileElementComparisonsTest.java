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
package info.matchingservice.dom.Match;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;
import info.matchingservice.dom.Profile.Profile;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProfileElementComparisonsTest {

    FinderInteraction finderInteraction;

    ProfileComparisons profileComparisons;

    @Before
    public void setup() {
        profileComparisons = new ProfileComparisons() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<ProfileComparison> allInstances() {
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


    public static class collectDemandProfileComparisons extends ProfileElementComparisonsTest {

        @Mock
        Profile supplyProfile;

        @Test
        public void happyCase() {


            profileComparisons.collectDemandProfileComparisons(supplyProfile);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Match.ProfileComparison.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileComparisonBySupplyProfile"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("supplyProfile"), is((Object) supplyProfile));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class findProfileComparisonByDemandAndSupplyProfile extends ProfileElementComparisonsTest {

    	@Mock
        Profile demandProfile;

        @Mock
        Profile supplyProfile;
    	
        @Test
        public void happyCase() {


            profileComparisons.findProfileComparisonByDemandAndSupplyProfile(demandProfile, supplyProfile);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.FIRST_MATCH));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Match.ProfileComparison.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileComparisonByDemandAndSupplyProfile"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("demandProfile"), is((Object) demandProfile));
            assertThat(finderInteraction.getArgumentsByParameterName().get("matchingSupplyProfile"), is((Object) supplyProfile));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }

    }

}