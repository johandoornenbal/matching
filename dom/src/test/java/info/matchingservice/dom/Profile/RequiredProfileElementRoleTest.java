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

package info.matchingservice.dom.Profile;

import org.junit.Test;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.PojoTester;
import junit.framework.TestCase;

/**
 * Created by jodo on 12/06/15.
 */
public class RequiredProfileElementRoleTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            final RequiredProfileElementRole pojo = new RequiredProfileElementRole();
            newPojoTester()
                    .withFixture(pojos(Profile.class, ProfileForTesting.class))
                    .exercise(pojo, PojoTester.FilterSet.excluding("uniqueItemId"));


            pojo.setStudent(true);
            assert(pojo.getStudent());
            pojo.setStudent(false);
            assert(!pojo.getStudent());
            pojo.setProfessional(true);
            assert(pojo.getProfessional());
            pojo.setProfessional(false);
            assert(!pojo.getProfessional());
            pojo.setPrincipal(true);
            assert(pojo.getPrincipal());
            pojo.setPrincipal(false);
            assert(!pojo.getPrincipal());


        }

    }

    public static class TestUpdates extends TestCase{

        @Test
        public void testUpdates(){

            // given
            RequiredProfileElementRole element = new RequiredProfileElementRole();

            // STUDENT //
            // when
            element.setStudent(true);
            assertTrue(element.getStudent());

            // then
            assertTrue(element.default0UpdateStudent());

            // when
            element.updateStudent(false);

            // then
            assertFalse(element.getStudent());
            assertFalse(element.default0UpdateStudent());

            // PROFESSIONAL //
            // when
            element.setProfessional(true);
            assertTrue(element.getProfessional());

            // then
            assertTrue(element.default0UpdateProfessional());

            // when
            element.updateProfessional(false);

            // then
            assertFalse(element.getProfessional());
            assertFalse(element.default0UpdateProfessional());

            // PRINCIPAL //
            // when
            element.setPrincipal(true);
            assertTrue(element.getPrincipal());

            // then
            assertTrue(element.default0UpdatePrincipal());

            // when
            element.updatePrincipal(false);

            // then
            assertFalse(element.getPrincipal());
            assertFalse(element.default0UpdatePrincipal());

        }

    }
}