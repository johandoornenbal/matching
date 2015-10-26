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
import info.matchingservice.fixture.howdoido.BasicSubCategoryFixtures;
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

        //TODO: finish this test
        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

    }

    public static class DuplicateBasicTemplate extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new BasicQuestionFixtures());
        }

        @Test
        public void happyCase() throws Exception {

            BasicTemplate basicTemplate;
            BasicTemplate basicTemplateCopy;

            //given
            basicTemplate = basicTemplates.allBasicTemplates().get(0);

            //when
            basicTemplateCopy = basicTemplate.duplicateBasicTemplate();

            //then
            assertThat(basicTemplates.allBasicTemplates().size()).isEqualTo(4);
            assertThat(basicUsers.findBasicUserByName("user1").getMyTemplates().last()).isEqualTo(basicTemplateCopy);

        }

    }

    public static class DeleteBasicTemplate extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new BasicQuestionFixtures());
        }

        @Test
        public void happyCase() throws Exception {

            BasicTemplate basicTemplate1;
            BasicTemplate basicTemplate2;

            //given
            basicTemplate1 = basicTemplates.allBasicTemplates().get(0);
            basicTemplate2 = basicTemplates.allBasicTemplates().get(1);
            assertThat(basicTemplates.allBasicTemplates().size()).isEqualTo(3);
            assertThat(basicUsers.findBasicUserByName("user1").getMyTemplates().first()).isEqualTo(basicTemplate1);
            assertThat(basicUsers.findBasicUserByName("user1").getMyTemplates().last()).isEqualTo(basicTemplate2);

            //when
            basicTemplate1.deleteBasicTemplate(true);

            //then
            assertThat(basicTemplates.allBasicTemplates().size()).isEqualTo(2);
            assertThat(basicUsers.findBasicUserByName("user1").getMyTemplates().first()).isEqualTo(basicTemplate2);

        }

    }

    public static class CreateBasicTemplateWithCategorySuggestion extends BasicTemplateTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new BasicUserFixtures());
            scenarioExecution().install(new BasicSubCategoryFixtures());
        }

        @Test
        public void happyCase() throws Exception {

            BasicUser basicUser;
            BasicCategory category;

            //given
            basicUser = basicUsers.findBasicUserByName("user1");
            category = basicCategoryRepo.allBasicCategories().get(0);

            //when
            BasicTemplate basicTemplate = (BasicTemplate) basicUser.createTemplateWithCategorySuggestion("A template", category, "A subcategory suggestion");

            //then
            assertThat(basicTemplates.allBasicTemplates().size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByName("user1").getMyTemplates().first()).isEqualTo(basicTemplate);
            assertThat(basicTemplate.getName()).isEqualTo("a template");
            assertThat(basicTemplate.getBasicCategory()).isEqualTo(category);
            assertThat(basicTemplate.getCategorySuggestion()).isEqualTo("a subcategory suggestion");
            assertThat(basicCategorySuggestionRepo.allBasicCategorySuggestions().size()).isEqualTo(1);
            assertThat(basicCategorySuggestionRepo.allBasicCategorySuggestions().get(0).getName()).isEqualTo("a subcategory suggestion");
            assertThat(basicCategorySuggestionRepo.allBasicCategorySuggestions().get(0).getParentCategory()).isEqualTo(category);
        }

    }

    @Inject
    BasicTemplates basicTemplates;

    @Inject
    BasicUsers basicUsers;

    @Inject
    BasicCategories basicCategoryRepo;

    @Inject
    BasicCategorySuggestions basicCategorySuggestionRepo;

}
