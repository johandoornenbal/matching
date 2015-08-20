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
package info.matchingservice.dom.Assessment;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;
import info.matchingservice.dom.Match.ProfileMatch;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AssessmentsTest {

    FinderInteraction finderInteraction;

    ProfileMatchAssessments assessments;

    @Before
    public void setup() {
    	assessments = new ProfileMatchAssessments() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<ProfileMatchAssessment> allInstances() {
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

    
    public static class findProfileMatchesAssessmentByProfileMatch extends AssessmentsTest {
    	
    	@Mock
        ProfileMatch profileMatch;
    	
        @Test
        public void happyCase() {
        	
        	assessments.findProfileMatchesAssessmentByProfileMatch(profileMatch);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Assessment.ProfileMatchAssessment.class);
            assertThat(finderInteraction.getQueryName(), is("findProfileMatchesAssessmentByProfileMatch"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("profileMatch"), is((Object) profileMatch));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }
   
}