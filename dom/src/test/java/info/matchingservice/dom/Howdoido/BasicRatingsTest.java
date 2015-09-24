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

package info.matchingservice.dom.Howdoido;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jodo on 07/09/15.
 */
public class BasicRatingsTest {

    FinderInteraction finderInteraction;

    BasicRatings basicRatings;

    @Before
    public void setup() {
        basicRatings = new BasicRatings() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicRating> allInstances() {
                finderInteraction = new FinderInteraction(null, FinderInteraction.FinderMethod.ALL_INSTANCES);
                return null;
            }

            @Override
            protected <T> List<T> allMatches(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.ALL_MATCHES);
                return null;
            }
        };
    }

    public static class FindByReceiver extends BasicRatingsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicRatings.findByReceiver(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRating.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicRatingByReceiver"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("receiver"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByCreator extends BasicRatingsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicRatings.findByCreator(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRating.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicRatingByCreator"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("creator"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByCategory extends BasicRatingsTest {

        @Test
        public void happyCase() {

            BasicCategory search = new BasicCategory();
            basicRatings.findByCategory(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRating.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicRatingByCategory"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("basicCategory"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

}
