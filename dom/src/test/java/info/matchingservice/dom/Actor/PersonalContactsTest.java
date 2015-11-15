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

import info.matchingservice.dom.FinderInteraction;
import org.apache.isis.applib.query.Query;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by jodo on 16/06/15.
 */
public class PersonalContactsTest {

    FinderInteraction finderInteraction;

    PersonalContacts personalContacts;

    @Before
    public void setup() {
        personalContacts = new PersonalContacts() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected <T> T uniqueMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.UNIQUE_MATCH);
                return null;
            }

            @Override
            protected List<PersonalContact> allInstances() {
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


    public static class FindUniquePersonalContact extends PersonalContactsTest {

        @Test
        public void happyCase() {

            String owner = new String();
            Person person = new Person();
            personalContacts.findUniquePersonalContact(owner, person);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.UNIQUE_MATCH));
            assertThat(finderInteraction.getResultType()).isEqualTo(PersonalContact.class);
            assertThat(finderInteraction.getQueryName(), is("findPersonalContactUniqueContact"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("ownedBy"), is((Object) owner));
            assertThat(finderInteraction.getArgumentsByParameterName().get("contact"), is((Object) person));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }
    }

}
