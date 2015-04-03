/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
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
package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PhoneTest extends MatchingIntegrationTest {
    
    @Inject
    CommunicationChannels communicationChannels;
    
    @Inject
    Persons persons;
     
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
        
    }
    
    public static class createPhone extends PhoneTest {
    	
    	Phone ph;
    	Person p1;
    	
    	@Before
        public void setUp() throws Exception {
    		//given
    		p1 = persons.findPersons("Hals").get(0);
    	}
    	
        @Test
        public void valuesSet() throws Exception {
        	//when
        	ph = communicationChannels.createPhone(p1, CommunicationChannelType.PHONE_NUMBER, "123");
        	
        	//then
        	assertThat(ph.getPhoneNumber(), is("123"));
        	assertThat(ph.getPerson(), is(p1));

        }
        
    }
    
}
