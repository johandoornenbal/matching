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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import info.matchingservice.dom.Howdoido.Api;
import info.matchingservice.dom.Howdoido.BasicCategory;
import info.matchingservice.dom.Howdoido.BasicTemplate;
import info.matchingservice.dom.Howdoido.BasicUser;
import info.matchingservice.dom.Howdoido.BasicUsers;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.fixture.security.MatchingRegularRoleAndPermissions;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jodo on 08/09/15.
 */
public class BasicUserTest extends MatchingIntegrationTest {

    public static class FindOrCreateBasicUserByEmail extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new MatchingRegularRoleAndPermissions());
        }

        final static String EMAIL = "test@test.com";
        final static String SIMILAR_EMAIL = "TeSt@TeST.cOm";
        final static String OTHER_EMAIL = "TeSt@TeST123.cOm";
        final static String OTHER_EMAIL_LOWERCASE = "test@test123.com";

        @Test
        public void happyCase() throws Exception {

            //when
            basicUsers.findOrCreateNewBasicUserByEmail(EMAIL);

            //then
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getEmailAddress()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getStatus()).isEqualTo(ApplicationUserStatus.ENABLED);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getRoles().size()).isEqualTo(1);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getRoles().first().getName()).isEqualTo("matching-regular-role");

            //and when
            basicUsers.findOrCreateNewBasicUserByEmail(SIMILAR_EMAIL);

            //then nothing changed
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getEmailAddress()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getStatus()).isEqualTo(ApplicationUserStatus.ENABLED);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getRoles().size()).isEqualTo(1);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getRoles().first().getName()).isEqualTo("matching-regular-role");

            assertThat(basicUsers.findBasicUserByEmailContains(SIMILAR_EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(SIMILAR_EMAIL).getOwnedBy()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(SIMILAR_EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(SIMILAR_EMAIL).getEmail()).isEqualTo(EMAIL);

            //and when
            basicUsers.findOrCreateNewBasicUserByEmail(OTHER_EMAIL);

            //then
            assertThat(basicUsers.findBasicUserByEmailContains("test").size()).isEqualTo(2);
            assertThat(basicUsers.findBasicUserByEmailContains(OTHER_EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(OTHER_EMAIL).getOwnedBy()).isEqualTo(OTHER_EMAIL_LOWERCASE);
            assertThat(basicUsers.findBasicUserByEmail(OTHER_EMAIL).getName()).isEqualTo(OTHER_EMAIL_LOWERCASE);
            assertThat(basicUsers.findBasicUserByEmail(OTHER_EMAIL).getEmail()).isEqualTo(OTHER_EMAIL_LOWERCASE);
            assertThat(applicationUsers.findUserByUsername(OTHER_EMAIL_LOWERCASE).getEmailAddress()).isEqualTo(OTHER_EMAIL_LOWERCASE);
            assertThat(applicationUsers.findUserByUsername(OTHER_EMAIL_LOWERCASE).getName()).isEqualTo(OTHER_EMAIL_LOWERCASE);
            assertThat(applicationUsers.findUserByUsername(OTHER_EMAIL_LOWERCASE).getStatus()).isEqualTo(ApplicationUserStatus.ENABLED);
            assertThat(applicationUsers.findUserByUsername(OTHER_EMAIL_LOWERCASE).getRoles().size()).isEqualTo(1);
            assertThat(applicationUsers.findUserByUsername(OTHER_EMAIL_LOWERCASE).getRoles().first().getName()).isEqualTo("matching-regular-role");
        }

    }

    public static class FindOrCreateBasicUserByEmailReversed extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new MatchingRegularRoleAndPermissions());
        }

        final static String LOWERCASE_EMAIL = "test@test.com";
        final static String EMAIL = "TeSt@TeST.cOm";

        @Test
        public void happyCaseReversed() throws Exception {

            //when
            basicUsers.findOrCreateNewBasicUserByEmail(EMAIL);

            //then
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getEmailAddress()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getName()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getStatus()).isEqualTo(ApplicationUserStatus.ENABLED);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getRoles().size()).isEqualTo(1);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getRoles().first().getName()).isEqualTo("matching-regular-role");

            //and when
            basicUsers.findOrCreateNewBasicUserByEmail(LOWERCASE_EMAIL);

            //then nothing changed
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getEmailAddress()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getName()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getStatus()).isEqualTo(ApplicationUserStatus.ENABLED);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getRoles().size()).isEqualTo(1);
            assertThat(applicationUsers.findUserByUsername(LOWERCASE_EMAIL).getRoles().first().getName()).isEqualTo("matching-regular-role");

            assertThat(basicUsers.findBasicUserByEmailContains(LOWERCASE_EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(LOWERCASE_EMAIL).getOwnedBy()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(LOWERCASE_EMAIL).getName()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(LOWERCASE_EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);
        }

    }

    public static class createSimpleBasicUser extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        final static String NAME = "Pietje Puk";
        final static String LOWERCASE_EMAIL = "test@test.com";
        final static String EMAIL = "TeSt@TeST.cOm";

        @Test
        public void happyCase() throws Exception {

            //when
            basicUsers.createBasicUser(NAME, EMAIL);

            //then
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmailContains(LOWERCASE_EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo("tester");
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(NAME);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);

        }

    }

    public static class createSimpleBasicUserWithOwnedBy extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        final static String NAME = "Pietje Puk";
        final static String LOWERCASE_EMAIL = "test@test.com";
        final static String EMAIL = "TeSt@TeST.cOm";
        final static String OWNED_BY = "pietje";

        @Test
        public void happyCase() throws Exception {

            //when
            basicUsers.createBasicUser(NAME, EMAIL, OWNED_BY);

            //then
            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmailContains(LOWERCASE_EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(OWNED_BY);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(NAME);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);

        }

    }

    public static class FindOrCreateBasicUserByEmailWithExistingUser extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new MatchingRegularRoleAndPermissions());
        }

        final static String NAME = "Pietje Puk";
        final static String LOWERCASE_EMAIL = "test@test.com";
        final static String EMAIL = "TeSt@TeST.cOm";
        final static String OWNED_BY = "pietje";

        BasicUser basicUser;

        @Test
        public void happyCase() throws Exception {

            //given
            basicUsers.createBasicUser(NAME, EMAIL, OWNED_BY);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(NAME);

            //when
            basicUser = basicUsers.findOrCreateNewBasicUserByEmail(EMAIL);

            //then
            assertThat(basicUser.getEmail()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUser.getName()).isEqualTo(NAME);
            assertThat(basicUser.getOwnedBy()).isEqualTo(OWNED_BY);

            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(OWNED_BY);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(NAME);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);

            //and when
            basicUser = basicUsers.findOrCreateNewBasicUserByEmail(LOWERCASE_EMAIL);

            //then the same
            assertThat(basicUser.getEmail()).isEqualTo(LOWERCASE_EMAIL);
            assertThat(basicUser.getName()).isEqualTo(NAME);
            assertThat(basicUser.getOwnedBy()).isEqualTo(OWNED_BY);

            assertThat(basicUsers.findBasicUserByEmailContains(EMAIL).size()).isEqualTo(1);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getOwnedBy()).isEqualTo(OWNED_BY);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(NAME);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getEmail()).isEqualTo(LOWERCASE_EMAIL);
        }


    }

    public static class UpdateEmail extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
            scenarioExecution().install(new MatchingRegularRoleAndPermissions());
        }

        BasicUser basicUser;

        final static String EMAIL = "test@test.com";

        @Test
        public void happyCase() throws Exception {

            //given
            basicUser = basicUsers.findOrCreateNewBasicUserByEmail(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail(EMAIL).getName()).isEqualTo(EMAIL);
            assertThat(applicationUsers.findUserByUsername(EMAIL).getEmailAddress()).isEqualTo(EMAIL);

            //when
            basicUser.updateEmail("TEST@test123.com");

            //then email is converted to lowerCase
            assertThat(basicUsers.findBasicUserByEmail("TEST@test123.com").getName()).isEqualTo(EMAIL);
            assertThat(basicUsers.findBasicUserByEmail("TEST@test123.com").getEmail()).isEqualTo("test@test123.com");
            assertThat(applicationUsers.findUserByUsername(EMAIL).getEmailAddress()).isEqualTo("test@test123.com");
        }


    }

    public static class CreateTemplate extends BasicUserTest {

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        final static String NAME = "Pietje Puk";
        final static String EMAIL = "test@test.com";

        final static String TEMPLATE_NAME = "some name";
        final static String CATEGORY_NAME = "academisch";

        BasicUser basicUser;
        BasicTemplate basicTemplate;
        BasicCategory category;

        @Test
        public void happCase() throws Exception {

            // given
            basicUser = basicUsers.createBasicUser(NAME, EMAIL);
            category = api.createTopCategory(CATEGORY_NAME);

            //when
            basicTemplate = (BasicTemplate) basicUser.createTemplate(TEMPLATE_NAME, category);

            //then
            assertThat(basicTemplate.getName()).isEqualTo(TEMPLATE_NAME);
            assertThat(basicTemplate.getBasicCategory().getName()).isEqualTo(CATEGORY_NAME);
            assertThat(basicTemplate.getTemplateOwner()).isEqualTo(basicUser);

        }

    }

    @Inject
    BasicUsers basicUsers;

    @Inject
    ApplicationUsers applicationUsers;

    @Inject
    Api api;

}
