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
package info.matchingservice.dom.Profile;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.query.Query;
import org.apache.isis.core.commons.matchers.IsisMatchers;
import org.junit.Before;
import org.junit.Test;

public class ProfilesTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    Profiles profiles;

    @Before
    public void setup() {
    	profiles = new Profiles() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<Profile> allInstances() {
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



    public static class FindProfiles extends ProfilesTest {

        @Test
        public void happyCase() {

        	final UUID uuid = UUID.randomUUID();
        	
        	profiles.findProfileByUniqueItemId(uuid);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findProfileByUniqueItemId"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("uniqueItemId"), is((Object) uuid));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }
}