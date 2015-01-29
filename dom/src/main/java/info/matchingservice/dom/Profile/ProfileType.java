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

package info.matchingservice.dom.Profile;

import info.matchingservice.dom.TitledEnum;

public enum ProfileType implements TitledEnum {

    PERSON_PROFILE("CV"),
    ORGANISATION_PROFILE("Organisatie profiel"),
    SERVICE_PROFILE("Dienst"),
    DEVICE_PROFILE("Voorwerp"),
    EVENT_PROFILE("Evenement"),
    COURSE_PROFILE("Cursus / Opleiding"),
    GENERIC_PROFILE("Algemeen");

    private String title;
    
    ProfileType (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }

}
