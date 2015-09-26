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

package info.matchingservice.integtest.dom.howdoido;

import info.matchingservice.dom.Howdoido.*;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.howdoido.BasicQuestionFixtures;
import info.matchingservice.fixture.howdoido.BasicTemplateFixtures;
import info.matchingservice.fixture.howdoido.BasicUserFixtures;
import info.matchingservice.fixture.security.MatchingRegularRoleAndPermissions;
import info.matchingservice.integtest.MatchingIntegrationTest;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jodo on 08/09/15.
 */
public class BasicTemplateTest extends MatchingIntegrationTest {

    public static class CreateQuestion extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new BasicUserFixtures());
            scenarioExecution().install(new BasicTemplateFixtures());
            scenarioExecution().install(new BasicQuestionFixtures());

            basicTemplate = basicTemplates.allBasicTemplates().get(0);
        }

        BasicQuestion basicQuestion;
        BasicTemplate basicTemplate;

        final static String QUESTION = "This is my question";

        @Test
        public void happyCase() throws Exception {

            //TODO: how to test this method?

//            //given
//            basicTemplate = basicTemplates.allBasicTemplates().get(0);
//            assertThat(basicTemplate.getBasicQuestions().size()).isEqualTo(1);
//
//            //when
//            basicTemplate.createBasicQuestion(QUESTION, BasicRepresentationType.RATING);
//
//            //then
//            assertThat(basicTemplate.getBasicQuestions().size()).isEqualTo(2);
//            basicQuestion = basicTemplate.getBasicQuestions().last();
//            assertThat(basicQuestion.getBasicQuestion()).isEqualTo(QUESTION);
//            assertThat(basicQuestion.getOwnedBy()).isEqualTo("user1");
//            assertThat(basicQuestion.getBasicFormType()).isEqualTo(BasicRepresentationType.RATING);

        }

    }

    public static class CreateFeedbackRequest extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new BasicUserFixtures());
            scenarioExecution().install(new BasicTemplateFixtures());
            scenarioExecution().install(new BasicQuestionFixtures());
            scenarioExecution().install(new MatchingRegularRoleAndPermissions());
        }

        BasicTemplate basicTemplate;
        BasicUser basicUser1;
        BasicUser basicUser2;
        BasicRequest basicRequest;

        @Test
        public void happyCase() throws Exception {

            //given
            basicTemplate = basicTemplates.allBasicTemplates().get(0);
            basicUser1 = basicUsers.findBasicUserByName("user1");
            basicUser2 = basicUsers.findBasicUserByName("user2");

            //when
            basicRequest = (BasicRequest) basicTemplate.createRequest("user2", null);

            //then
            assertThat(basicRequest.getProvider()).isEqualTo(basicUser2);
            assertThat(basicRequest.getRequestReceiver()).isEqualTo(basicUser2);
            assertThat(basicRequest.getRequestOwner()).isEqualTo(basicUser1);
            assertThat(basicRequest.getReceiver()).isEqualTo(basicUser1);
            assertThat(basicRequest.getTemplate()).isEqualTo(basicTemplate);
            assertThat(basicRequest.getRequestDenied()).isEqualTo(false);
            assertThat(basicRequest.getRequestHonoured()).isEqualTo(false);

            //and when
            basicRequest = (BasicRequest) basicTemplate.createRequest("", basicUser2);

            //then the same
            assertThat(basicRequest.getProvider()).isEqualTo(basicUser2);
            assertThat(basicRequest.getRequestReceiver()).isEqualTo(basicUser2);
            assertThat(basicRequest.getRequestOwner()).isEqualTo(basicUser1);
            assertThat(basicRequest.getReceiver()).isEqualTo(basicUser1);
            assertThat(basicRequest.getTemplate()).isEqualTo(basicTemplate);
            assertThat(basicRequest.getRequestDenied()).isEqualTo(false);
            assertThat(basicRequest.getRequestHonoured()).isEqualTo(false);

            //and when
            basicRequest = (BasicRequest) basicTemplate.createRequest("nonexisting@mailadress.com", null);

            //then (a new user is created)
            assertThat(basicRequest.getRequestReceiver().getEmail()).isEqualTo("nonexisting@mailadress.com");
            assertThat(basicRequest.getRequestReceiver().getName()).isEqualTo("nonexisting@mailadress.com");
            assertThat(basicRequest.getRequestReceiver().getOwnedBy()).isEqualTo("nonexisting@mailadress.com");
            assertThat(basicRequest.getRequestOwner()).isEqualTo(basicUser1);
            assertThat(basicRequest.getReceiver()).isEqualTo(basicUser1);
            assertThat(basicRequest.getTemplate()).isEqualTo(basicTemplate);
            assertThat(basicRequest.getRequestDenied()).isEqualTo(false);
            assertThat(basicRequest.getRequestHonoured()).isEqualTo(false);

        }

    }

    public static class CreateAnonymousFeedbackRequest extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

    }

    @Inject
    BasicTemplates basicTemplates;

    @Inject
    BasicUsers basicUsers;

}
