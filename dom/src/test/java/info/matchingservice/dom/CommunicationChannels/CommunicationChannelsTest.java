/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.CommunicationChannels;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.FinderInteraction;
import info.matchingservice.dom.FinderInteraction.FinderMethod;

import java.util.List;

import org.apache.isis.applib.query.Query;
import org.junit.Before;
import org.junit.Test;

public class CommunicationChannelsTest {

    info.matchingservice.dom.FinderInteraction finderInteraction;

    CommunicationChannels communicationChannels;

    @Before
    public void setup() {
    	communicationChannels = new CommunicationChannels() {

            @Override
            protected <T> T firstMatch(Query<T> query) {
                finderInteraction = new FinderInteraction(query, FinderMethod.FIRST_MATCH);
                return null;
            }

            @Override
            protected List<CommunicationChannel> allInstances() {
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



    public static class AllCommunicationChannels extends CommunicationChannelsTest {

        @Test
        public void happyCase() {
        	
        	communicationChannels.allCommunicationChannels();

            assertThat(finderInteraction.getFinderMethod(), is(FinderMethod.ALL_INSTANCES));




        }

        @Test
        public void testDelete(){




        }




    }
    

   
}