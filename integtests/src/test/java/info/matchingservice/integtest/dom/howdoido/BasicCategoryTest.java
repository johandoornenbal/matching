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

import info.matchingservice.dom.Howdoido.Api;
import info.matchingservice.dom.Howdoido.BasicCategories;
import info.matchingservice.dom.Howdoido.BasicCategory;
import info.matchingservice.fixture.TeardownFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jodo on 03/09/15.
 */
public class BasicCategoryTest extends MatchingIntegrationTest {

    public static class BasicCategoryTreeTest extends BasicCategoryTest {

        BasicCategory t1;
        BasicCategory t2;
        BasicCategory t1s1;
        BasicCategory t1s2;

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        @Test
        public void simpleTreeTest() throws Exception {

            //given
            t1 = api.createBasicCategory("t1");
            t2 = api.createBasicCategory("t2");

            //when
            /*
                        t1 ------------          t2
                        |             |
                        t1s1          t1s2
             */
            t1.createChildCategory("t1s1");
            t1.createChildCategory("t1s2");
            t1s1 = basicCategories.findByName("t1s1");
            t1s2 = basicCategories.findByName("t1s2");


            //then
            assertThat(t1.getDirectChildCategories().size()).isEqualTo(2);
            assertThat(t1.getDirectChildCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1.getDirectChildCategories().contains(t1s2)).isEqualTo(true);

            assertThat(t1.getAllDescendantCategories().size()).isEqualTo(2);
            assertThat(t1.getAllDescendantCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1.getAllDescendantCategories().contains(t1s2)).isEqualTo(true);

            assertThat(t1.getDirectParentCategories().size()).isEqualTo(0);
            assertThat(t1.getAllAscendantCategories().size()).isEqualTo(0);

            assertThat(t1s1.getDirectParentCategories().size()).isEqualTo(1);
            assertThat(t1s1.getDirectParentCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s2.getDirectParentCategories().size()).isEqualTo(1);
            assertThat(t1s2.getDirectParentCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s1.getAllAscendantCategories().size()).isEqualTo(1);
            assertThat(t1s1.getAllAscendantCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s2.getAllAscendantCategories().size()).isEqualTo(1);
            assertThat(t1s2.getAllAscendantCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s1.getTopCategories().size()).isEqualTo(1);
            assertThat(t1s1.getTopCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s2.getTopCategories().size()).isEqualTo(1);
            assertThat(t1s2.getTopCategories().contains(t1)).isEqualTo(true);

            assertThat(t1.getAllFamilyCategories().size()).isEqualTo(3);
            assertThat(t1.getAllFamilyCategories().contains(t1)).isEqualTo(true);
            assertThat(t1.getAllFamilyCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1.getAllFamilyCategories().contains(t1s2)).isEqualTo(true);
            assertThat(t1.getAllFamilyCategories().contains(t2)).isEqualTo(false);

            assertThat(t1s1.getAllFamilyCategories().size()).isEqualTo(3);
            assertThat(t1s1.getAllFamilyCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1.getAllFamilyCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s1.getAllFamilyCategories().contains(t1s2)).isEqualTo(true);
            assertThat(t1s1.getAllFamilyCategories().contains(t2)).isEqualTo(false);

            assertThat(t1s2.getAllFamilyCategories().size()).isEqualTo(3);
            assertThat(t1s2.getAllFamilyCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s2.getAllFamilyCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s2.getAllFamilyCategories().contains(t1s2)).isEqualTo(true);
            assertThat(t1s2.getAllFamilyCategories().contains(t2)).isEqualTo(false);


            //when
            /*
                        t1 ------------          t2
                        |             |
                        t1s1          t1s2
                        |
                        t1s1s1
             */
            t1s1.createChildCategory("t1s1s1");
            BasicCategory t1s1s1 = basicCategories.findByName("t1s1s1");

            //then
            assertThat(t1.getDirectChildCategories().size()).isEqualTo(2);
            assertThat(t1.getDirectChildCategories().contains(t1s1s1)).isEqualTo(false);

            assertThat(t1.getAllDescendantCategories().size()).isEqualTo(3);
            assertThat(t1.getAllDescendantCategories().contains(t1s1s1)).isEqualTo(true);

            assertThat(t1s1.getDirectChildCategories().size()).isEqualTo(1);
            assertThat(t1s1.getDirectChildCategories().contains(t1s1s1)).isEqualTo(true);

            assertThat(t1s1.getAllDescendantCategories().size()).isEqualTo(1);
            assertThat(t1s1.getAllDescendantCategories().contains(t1s1s1)).isEqualTo(true);

            assertThat(t1s1s1.getDirectParentCategories().size()).isEqualTo(1);
            assertThat(t1s1s1.getDirectParentCategories().contains(t1s1)).isEqualTo(true);

            assertThat(t1s1s1.getAllAscendantCategories().size()).isEqualTo(2);
            assertThat(t1s1s1.getAllAscendantCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s1s1.getAllAscendantCategories().contains(t1s2)).isEqualTo(false);
            assertThat(t1s1s1.getAllAscendantCategories().contains(t1)).isEqualTo(true);

            assertThat(t1s1s1.getTopCategories().size()).isEqualTo(1);
            assertThat(t1s1s1.getTopCategories().contains(t1)).isEqualTo(true);

            assertThat(t1.getAllFamilyCategories().size()).isEqualTo(4);
            assertThat(t1.getAllFamilyCategories().contains(t1s1s1)).isEqualTo(true);

            assertThat(t1s1s1.getAllFamilyCategories().size()).isEqualTo(4);
            assertThat(t1s1s1.getAllFamilyCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1s1.getAllFamilyCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s1s1.getAllFamilyCategories().contains(t1s2)).isEqualTo(true);
            assertThat(t1s1s1.getAllFamilyCategories().contains(t1s1s1)).isEqualTo(true);
            assertThat(t1s1s1.getAllFamilyCategories().contains(t2)).isEqualTo(false);
        }

    }

    public static class NoCircularRelationshipsCategoryTreeTest extends BasicCategoryTest {

        BasicCategory t1;
        BasicCategory t1s1;
        BasicCategory t1s2;

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        @Test
        public void checkedNoCircularRelationships() throws Exception {
            //given
            /*
                        t1 ------------
                        |             |
                        t1s1          t1s2
                        |
                        t1s1s1
             */
            t1 = api.createBasicCategory("t1");
            t1.createChildCategory("t1s1");
            t1.createChildCategory("t1s2");
            t1s1 = basicCategories.findByName("t1s1");
            t1s2 = basicCategories.findByName("t1s2");
            t1s1.createChildCategory("t1s1s1");
            BasicCategory t1s1s1 = basicCategories.findByName("t1s1s1");

            //when
            /* for example NOT ALLOWED
                        t1 ------------
                        |             |
                        t1s1<--|     t1s2
                        |      |
                     t1s1s1 -X-|
             */

            //then
            assertThat(t1s1s1.checkedNoCircularRelationships(t1s1)).isEqualTo(false);
            assertThat(t1s1s1.validateMakeCategoryAChild(t1s1)).isEqualTo("Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.");

            assertThat(t1s1s1.checkedNoCircularRelationships(t1)).isEqualTo(false);
            assertThat(t1s1s1.validateMakeCategoryAChild(t1)).isEqualTo("Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.");

            //allowed
            assertThat(t1s1s1.checkedNoCircularRelationships(t1s2)).isEqualTo(true);

            assertThat(t1s1.checkedNoCircularRelationships(t1)).isEqualTo(false);
            assertThat(t1s1.validateMakeCategoryAChild(t1)).isEqualTo("Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.");

            assertThat(t1s2.checkedNoCircularRelationships(t1)).isEqualTo(false);
            assertThat(t1s2.validateMakeCategoryAChild(t1)).isEqualTo("Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.");


        }

    }

    public static class ChildCanHaveMultipleParentsCategoryTreeTest extends BasicCategoryTest {

        BasicCategory t1;
        BasicCategory t1s1;
        BasicCategory t1s2;

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }



        @Test
        public void t1s1ChildOft1s2TreeTest() throws Exception {
            //given
            /*
                        t1 ------------
                        |             |
                        t1s1          t1s2
             */
            t1 = api.createBasicCategory("t1");
            t1.createChildCategory("t1s1");
            t1.createChildCategory("t1s2");
            t1s1 = basicCategories.findByName("t1s1");
            t1s2 = basicCategories.findByName("t1s2");

            //when
            /*
                        t1 ------------
                        |             |
                        t1s1 <-------t1s2
             */
            t1s2.makeCategoryAChild(t1s1);

            //then
            assertThat(t1s2.getDirectChildCategories().size()).isEqualTo(1);
            assertThat(t1s2.getDirectChildCategories().contains(t1s1)).isEqualTo(true);

            assertThat(t1s1.getDirectParentCategories().size()).isEqualTo(2);
            assertThat(t1s1.getDirectParentCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1.getDirectParentCategories().contains(t1s2)).isEqualTo(true);

            assertThat(t1s1.getTopCategories().size()).isEqualTo(1);
            assertThat(t1s1.getTopCategories().contains(t1)).isEqualTo(true);

        }

        @Test
        public void t1s1Andt1s1s1ChildOft1s2TreeTest() throws Exception {
            //given
            /*
                        t1 ------------
                        |             |
                        t1s1          t1s2
                        |
                        t1s1s1
             */
            t1 = api.createBasicCategory("t1");
            t1.createChildCategory("t1s1");
            t1.createChildCategory("t1s2");
            t1s1 = basicCategories.findByName("t1s1");
            t1s2 = basicCategories.findByName("t1s2");
            t1s1.createChildCategory("t1s1s1");
            BasicCategory t1s1s1 = basicCategories.findByName("t1s1s1");

            //when
            /*
                        t1 ------------
                        |             |
                        t1s1  <------t1s2
                        |             |
                        t1s1s1 <------|
             */
            t1s2.makeCategoryAChild(t1s1);
            t1s2.makeCategoryAChild(t1s1s1);

            //then
            assertThat(t1s2.getDirectChildCategories().size()).isEqualTo(2);
            assertThat(t1s2.getDirectChildCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s2.getDirectChildCategories().contains(t1s1s1)).isEqualTo(true);

            assertThat(t1s1s1.getDirectParentCategories().size()).isEqualTo(2);
            assertThat(t1s1s1.getDirectParentCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t1s1s1.getDirectParentCategories().contains(t1s2)).isEqualTo(true);

            assertThat(t1s1s1.getTopCategories().size()).isEqualTo(1);
            assertThat(t1s1s1.getTopCategories().contains(t1)).isEqualTo(true);

        }

    }

    public static class JoiningTreeTest extends BasicCategoryTest {

        BasicCategory t1;
        BasicCategory t2;
        BasicCategory t1s1;
        BasicCategory t1s2;
        BasicCategory t2s1;
        BasicCategory t2s2;
        BasicCategory t2s1s1;

        @Before
        public void setup() {
            scenarioExecution().install(new TeardownFixture());
        }

        @Test
        public void joiningTreeTest() throws Exception {

            //given
            t1 = api.createBasicCategory("t1");
            t2 = api.createBasicCategory("t2");
            /*
                        t1 ------------           t2 ----------
                        |             |           |           |
                        t1s1          t1s2        t2s1        t2s2
                                                  |
                                                  t2s1s1
             */
            t1.createChildCategory("t1s1");
            t1.createChildCategory("t1s2");
            t1s1 = basicCategories.findByName("t1s1");
            t1s2 = basicCategories.findByName("t1s2");
            t2.createChildCategory("t2s1");
            t2.createChildCategory("t2s2");
            t2s1 = basicCategories.findByName("t2s1");
            t2s2 = basicCategories.findByName("t2s2");
            t2s1.createChildCategory("t2s1s1");
            t2s1s1 = basicCategories.findByName("t2s1s1");

            //when
            /*
                        t1 ------------           t2 ----------
                        |             |           |           |
                        t1s1          t1s2        |           |
                        |                         |           |
                        |--------------------->  t2s1        t2s2
                                                  |
                                                  t2s1s1
             */
            t1s1.makeCategoryAChild(t2s1);

            //then
            assertThat(t1.getAllDescendantCategories().size()).isEqualTo(4);
            assertThat(t1.getAllDescendantCategories().contains(t2s1)).isEqualTo(true);
            assertThat(t1.getAllDescendantCategories().contains(t2s1s1)).isEqualTo(true);

            assertThat(t2s1.getDirectParentCategories().size()).isEqualTo(2);
            assertThat(t2s1.getDirectParentCategories().contains(t2)).isEqualTo(true);
            assertThat(t2s1.getDirectParentCategories().contains(t1s1)).isEqualTo(true);

            assertThat(t2s1.getAllAscendantCategories().size()).isEqualTo(3);
            assertThat(t2s1.getAllAscendantCategories().contains(t2)).isEqualTo(true);
            assertThat(t2s1.getAllAscendantCategories().contains(t1)).isEqualTo(true);
            assertThat(t2s1.getAllAscendantCategories().contains(t1s1)).isEqualTo(true);

            assertThat(t2s1.getTopCategories().size()).isEqualTo(2);
            assertThat(t2s1.getTopCategories().contains(t2)).isEqualTo(true);
            assertThat(t2s1.getTopCategories().contains(t1)).isEqualTo(true);

            assertThat(t2s1s1.getTopCategories().size()).isEqualTo(2);
            assertThat(t2s1s1.getTopCategories().contains(t2)).isEqualTo(true);
            assertThat(t2s1s1.getTopCategories().contains(t1)).isEqualTo(true);

            assertThat(t2s1.getAllFamilyCategories().size()).isEqualTo(7);
            assertThat(t1.getAllFamilyCategories().size()).isEqualTo(7);
            assertThat(t2.getAllFamilyCategories().size()).isEqualTo(7);
            //etc
            assertThat(t2s1s1.getAllFamilyCategories().contains(t1)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t2)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t1s2)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t2s1)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t2s2)).isEqualTo(true);
            assertThat(t2s1s1.getAllFamilyCategories().contains(t2s1s1)).isEqualTo(true);

            //when
            /*
                        t1 ------------           t2 ----------
                        |             |           |           |
                      |>t1s1          t1s2        |           |
                      | |                         |           |
                      | |--------------------->  t2s1        t2s2
                      |                           |           |
                      |                           t2s1s1      |
                      |---------------------------------------|
             */
            t2s2.makeCategoryAChild(t1s1);

            //then
            assertThat(t1s1.getTopCategories().size()).isEqualTo(2);
            assertThat(t1s1.getTopCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1.getTopCategories().contains(t2)).isEqualTo(true);

            assertThat(t1s1.getDirectParentCategories().size()).isEqualTo(2);
            assertThat(t1s1.getDirectParentCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1.getDirectParentCategories().contains(t2s2)).isEqualTo(true);

            assertThat(t1s1.getAllAscendantCategories().size()).isEqualTo(3);
            assertThat(t1s1.getAllAscendantCategories().contains(t1)).isEqualTo(true);
            assertThat(t1s1.getAllAscendantCategories().contains(t2)).isEqualTo(true);
            assertThat(t1s1.getAllAscendantCategories().contains(t2s2)).isEqualTo(true);

            assertThat(t2s2.getDirectChildCategories().size()).isEqualTo(1);
            assertThat(t2s2.getDirectChildCategories().contains(t1s1)).isEqualTo(true);

            assertThat(t2s2.getAllDescendantCategories().size()).isEqualTo(3);
            assertThat(t2s2.getAllDescendantCategories().contains(t1s1)).isEqualTo(true);
            assertThat(t2s2.getAllDescendantCategories().contains(t2s1)).isEqualTo(true);
            assertThat(t2s2.getAllDescendantCategories().contains(t2s1s1)).isEqualTo(true);

            // this makes it not possible for t2s1 to have t2s2 as a child
            assertThat(t2s1.checkedNoCircularRelationships(t2s2)).isEqualTo(false);
            assertThat(t2s1.validateMakeCategoryAChild(t2s2)).isEqualTo("Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.");

            // but the other way around is still possible (for t2s2 to have t2s1 as a (direct)child)
            assertThat(t2s2.checkedNoCircularRelationships(t2s1)).isEqualTo(true);

        }

    }

    @Inject
    Api api;

    @Inject
    BasicCategories basicCategories;

}
