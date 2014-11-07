/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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
package nl.socrates.fixture.party;

import org.joda.time.LocalDate;

import nl.socrates.dom.party.PersonGenderType;

public class PersonForJohanDo extends PersonAbstract {

    public static final String PARTY_REFERENCE = "JDOORN";

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(PARTY_REFERENCE, 
                "J", 
                "Johan",
                "",
                "Doornenbal", 
                "Johannes", 
                PersonGenderType.MALE, 
                new LocalDate(1962,7,16), 
                "Leeuwarden", 
                "Nederland", 
                "Bongastate 11",
                "", 
                "8926 PJ", 
                "Leeuwarden", 
                "NL-FRI", 
                "NLD", 
                "+31 58 266 1357", 
                "+31 6 227 666 28", 
                "johan@filternet.nl", 
                executionContext);
    }
}
