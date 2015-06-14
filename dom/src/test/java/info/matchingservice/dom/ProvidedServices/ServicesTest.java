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

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.query.Query;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServicesTest {

    FinderInteraction finderInteraction;

    Services services;

    @Before
    public void setup() {
    	services = new Services() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<Service> allInstances() {
                finderInteraction = new FinderInteraction(null, FinderMethod.ALL_INSTANCES);
                return null;
            }

            @Override
            protected <T> List<T> allMatches(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.ALL_MATCHES);
                return null;
            }
        };
    }



    public static class FindServicesByOwner extends ServicesTest {


        @Test
        public void happyCase() {

            final Person owner = new Person();
        	
        	services.findServicesByOwner(owner);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
//            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByOwner"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("serviceOwner"), is((Object) owner));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByUser extends ServicesTest {


        @Test
        public void happyCase() {

            final String ownedBy = new String();

            services.findServicesByUser(ownedBy);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByOwnedBy"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("ownedBy"), is((Object) ownedBy));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByUUID extends ServicesTest {


        @Test
        public void happyCase() {

            final UUID uuid = UUID.randomUUID();

            services.findServicesByUUID(uuid);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByUniqueItemId"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("uniqueItemId"), is((Object) uuid));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByDescription extends ServicesTest {


        @Test
        public void happyCase() {

            final String description = new String();

            services.findServicesByDescription(description);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByDescription"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("serviceDescription"), is((Object) description));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByDescriptionContains extends ServicesTest {

        @Test
        public void happyCase() {

            final String description = new String();

            services.findServicesByDescriptionContains(description);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByDescriptionContains"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("serviceDescription"), is((Object) description));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByType extends ServicesTest {

        @Test
        public void happyCase() {

            final ServiceType serviceType = ServiceType.SERVICE_COURSE;

            services.findServicesByType(serviceType);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByType"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("serviceType"), is((Object) serviceType));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }

    public static class FindServicesByStatus extends ServicesTest {

        @Test
        public void happyCase() {

            final ServiceStatus serviceStatus = ServiceStatus.OPEN;

            services.findServicesByStatus(serviceStatus);

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_MATCHES));
            //            assertThat(finderInteraction.getResultType(), IsisMatchers.classEqualTo(Profile.class));
            assertThat(finderInteraction.getQueryName(), is("findServiceByStatus"));
            assertThat(finderInteraction.getArgumentsByParameterName().get("serviceStatus"), is((Object) serviceStatus));
            assertThat(finderInteraction.getArgumentsByParameterName().size(), is(1));
        }

    }


   
}