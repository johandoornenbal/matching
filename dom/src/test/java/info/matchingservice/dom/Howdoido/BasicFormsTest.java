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
public class BasicFormsTest {

    FinderInteraction finderInteraction;

    BasicForms basicForms;

    @Before
    public void setup() {
        basicForms = new BasicForms() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderInteraction.FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<BasicForm> allInstances() {
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

    public static class FindByCreator extends BasicFormsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicForms.findByCreator(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicForm.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicFormByCreator"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("formCreator"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByReceiver extends BasicFormsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicForms.findByReceiver(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicForm.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicFormByReceiver"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("formReceiver"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }
    }

    public static class FindByReceiverAndUnRated extends BasicFormsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicForms.findByReceiverAndUnRated(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicForm.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicFormByReceiverAndRated"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("formReceiver"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().get("formRated"), is((Object) false));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }
    }

    public static class FindByReceiverAndPublished extends BasicFormsTest {

        @Test
        public void happyCase() {

            BasicUser search = new BasicUser();
            basicForms.findByReceiverAndPublished(search);

            assertThat(finderInteraction.getFinderMethod(), is(FinderInteraction.FinderMethod.ALL_MATCHES));
            Assertions.assertThat(finderInteraction.getResultType()).isEqualTo(BasicForm.class);
            assertThat(finderInteraction.getQueryName(), is("findBasicFormByReceiverAndPublished"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("formReceiver"), is((Object) search));
            assertThat(finderInteraction.getArgumentsByParameterName().get("published"), is((Object) true));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(2));
        }
    }

}
