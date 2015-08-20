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

package info.matchingservice.dom.Actor;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.FinderInteraction;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by jodo on 16/06/15.
 */
public class PersonsTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    Persons persons;

    @Before
    public void setup() {
        persons = new Persons() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<Person> allInstances() {
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


    public static class FindPersonsByName extends PersonsTest {

        @Test
        public void happyCase() {

            String search = new String();
            persons.findPersonsByName(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            assertThat(finderInteraction.getResultType()).isEqualTo(info.matchingservice.dom.Actor.Person.class);
            assertThat(finderInteraction.getQueryName(), is("matchPersonByNameContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("search"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

}
