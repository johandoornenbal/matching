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

package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.TitledEnum;

public enum DemandSupplyType implements TitledEnum {

    GENERIC_DEMANDSUPPLY("Algemeen type"),
    PERSON_DEMANDSUPPLY("Personen type"),
    COURSE_DEMANDSUPPLY("Cursus type"),
    EVENT_DEMANDSUPPLY("Evenement type"),
    ORGANISATION_DEMANDSUPPLY("Organisatie type");

    private String title;
    
    DemandSupplyType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
