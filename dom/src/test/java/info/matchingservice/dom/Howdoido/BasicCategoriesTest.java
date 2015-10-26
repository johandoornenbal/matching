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

import info.matchingservice.dom.FinderInteraction;
import org.apache.isis.applib.query.Query;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by jodo on 07/09/15.
 */
public class BasicCategoriesTest {

    FinderInteraction finderInteraction;

    BasicCategories basicCategories;

    @Before
    public void setup() {
        basicCategories = new BasicCategories() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicCategory> allInstances() {
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

    public static class FindByNameContains extends BasicCategoriesTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicCategories.findByNameContains(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicCategory.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicCategoryByNameContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("name"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByName extends BasicCategoriesTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicCategories.findByName(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.FIRST_MATCH));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicCategory.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicCategoryByName"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("name"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

}
