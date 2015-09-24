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
public class BasicCategoryRelationshipTuplesTest {

    FinderInteraction finderInteraction;

    BasicCategoryRelationshipTuples basicCategoryRelationshipTuples;

    @Before
    public void setup() {
        basicCategoryRelationshipTuples = new BasicCategoryRelationshipTuples() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicCategoryRelationshipTuple> allInstances() {
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

    public static class FindByParent extends BasicCategoryRelationshipTuplesTest {

        @Test
        public void happyCase() {

            BasicCategory search = new BasicCategory();
            basicCategoryRelationshipTuples.findByParent(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicCategoryRelationshipTuple.class);
            assertThat(finderInteraction.getQueryName(), is("findByParent"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("parent"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByChild extends BasicCategoryRelationshipTuplesTest {

        @Test
        public void happyCase() {

            BasicCategory search = new BasicCategory();
            basicCategoryRelationshipTuples.findByChild(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicCategoryRelationshipTuple.class);
            assertThat(finderInteraction.getQueryName(), is("findByChild"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("child"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class findByParentAndChild extends BasicCategoryRelationshipTuplesTest {

        @Test
        public void happyCase() {

            BasicCategory search = new BasicCategory();
            BasicCategory search2 = new BasicCategory();
            basicCategoryRelationshipTuples.findByParentAndChild(search, search2);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicCategoryRelationshipTuple.class);
            assertThat(finderInteraction.getQueryName(), is("findByParentAndChild"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("parent"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().get("child"), is((Object) search2));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }
    }

}
