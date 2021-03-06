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
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.TrustLevel;

import org.apache.isis.applib.annotation.ViewModel;

@ViewModel
public class Referral {

    public Referral(Person referrer, TrustLevel trustLevel) {
        this.referrer = referrer;
        this.trustLevel = trustLevel;
    }

    private Person referrer;

    public Person getReferrer() {
        return referrer;
    }

    public void setReferrer(final Person ref) {
        this.referrer = ref;
    }

    private TrustLevel trustLevel;

    public TrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(final TrustLevel level) {
        this.trustLevel = level;
    }
}
