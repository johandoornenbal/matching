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
public class BasicUsersTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    BasicUsers basicUsers;

    @Before
    public void setup() {
        basicUsers = new BasicUsers() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicUser> allInstances() {
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


    public static class FindBasicUserByName extends BasicUsersTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicUsers.findBasicUserByName(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.FIRST_MATCH));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Howdoido.BasicUser.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicUserByName"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("name"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindBasicUserByLogin extends BasicUsersTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicUsers.findBasicUserByLogin(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.FIRST_MATCH));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Howdoido.BasicUser.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicUserByLogin"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("ownedBy"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindBasicUserByEmail extends BasicUsersTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicUsers.findBasicUserByEmail(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.FIRST_MATCH));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Howdoido.BasicUser.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicUserByEmail"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("email"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindBasicUserByNameContains extends BasicUsersTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicUsers.findBasicUserByNameContains(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Howdoido.BasicUser.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicUserByNameContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("name"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindBasicUserByEmailContains extends BasicUsersTest {

        @Test
        public void happyCase() {

            String search = new String();
            basicUsers.findBasicUserByEmailContains(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Howdoido.BasicUser.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicUserByEmailContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("email"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

}
