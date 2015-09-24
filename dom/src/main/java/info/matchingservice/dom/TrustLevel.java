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
package info.matchingservice.dom;


// compare to kan gebruikt worden op enum: de eerst gedeclareerde waarde heeft de laagste ordening
// dus compare(BANNED, OUTERCIRCLE) levert negatieve waarde
public enum TrustLevel implements TitledEnum, info.matchingservice.dom.Howdoido.Interfaces.TrustLevel{
    BANNED("5_BANNED"),
    OUTER_CIRCLE("4_OUTER"),
    ENTRY_LEVEL("3_ENTRY"),
    INNER_CIRCLE("2_INNER"),
    INTIMATE("1_INTIMATE");

    private String title;
    
    TrustLevel (String title) {
        this.title = title;
    }
    
    public String title() {
        return title;
    }
}
