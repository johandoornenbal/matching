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
public class BasicRequestsTest {

    FinderInteraction finderInteraction;

    BasicRequests basicRequests;

    @Before
    public void setup() {
        basicRequests = new BasicRequests() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicRequest> allInstances() {
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

    public static class FindRequestByOwner extends BasicRequestsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicRequests.findRequestByOwner(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRequest.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicRequestByOwner"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("owner"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindRequestDeniedByOwner extends BasicRequestsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicRequests.findDeniedRequestByOwner(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRequest.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicDeniedRequestByOwner"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("owner"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindRequestByRequestReceiver extends BasicRequestsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicRequests.findRequestByRequestReceiver(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicRequest.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicRequestByRequestReceiver"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("requestReceiver"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }


}
