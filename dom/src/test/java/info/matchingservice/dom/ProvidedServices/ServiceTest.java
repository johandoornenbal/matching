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

package info.matchingservice.dom.ProvidedServices;

import org.junit.Test;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonForTesting;
import info.matchingservice.dom.PojoTester;

public class ServiceTest {
    public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {


            final Service service = new Service();

            newPojoTester().withFixture(pojos(Person.class, PersonForTesting.class))
                    .exercise(service, PojoTester.FilterSet.excluding("uniqueItemId"));


        }

    }

}