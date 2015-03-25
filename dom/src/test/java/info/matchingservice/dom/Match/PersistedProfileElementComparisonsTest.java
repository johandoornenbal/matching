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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;
import info.matchingservice.dom.Profile.ProfileElement;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.query.Query;
import org.apache.isis.core.commons.matchers.IsisMatchers;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

public class PersistedProfileElementComparisonsTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    PersistedProfileElementComparisons persistedProfileElementComparisons;

    @Before
    public void setup() {
    	persistedProfileElementComparisons = new PersistedProfileElementComparisons() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<PersistedProfileElementComparison> allInstances() {
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



    public static class FindElementComparisonByElement extends PersistedProfileElementComparisonsTest {

    	@Mock
    	ProfileElement element;
    	
        @Test
        public void happyCase() {

        	
        	
        	persistedProfileElementComparisons.findProfileElementComparisonsByElement(element);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(PersistedProfileElementComparison.class));
            assertThat(finderInteraction.getQueryName(), is("findProfileElementComparisonByElements"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("element"), is((Object) element));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }
    
    public static class FindElementComparisonByOwner extends PersistedProfileElementComparisonsTest {

    	@Mock
    	String owner;
    	
        @Test
        public void happyCase() {

        	
        	
        	persistedProfileElementComparisons.findProfileElementComparisons(owner);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(PersistedProfileElementComparison.class));
            assertThat(finderInteraction.getQueryName(), is("findProfileElementComparisonByOwner"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("ownedBy"), is((Object) owner));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }
}