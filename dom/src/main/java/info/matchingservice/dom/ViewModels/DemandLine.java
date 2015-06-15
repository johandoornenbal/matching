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

package info.matchingservice.dom.ViewModels;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ViewModel;

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/06/15.
 */
@ViewModel
public class DemandLine {

    public DemandLine() {
    }

    public DemandLine(
            Demand demand,
            Profile demandProfile,
            ProfileElement demandProfileElement) {
        this.demand = demand;
        this.demandProfile = demandProfile;
        this.demandProfileElement = demandProfileElement;
    }

    //region > demand (property)
    private Demand demand;

    @MemberOrder(sequence = "1")
    public Demand getDemand() {
        return demand;
    }

    public void setDemand(final Demand demand) {
        this.demand = demand;
    }
    //endregion

    //region > demandProfile (property)
    private Profile demandProfile;

    @MemberOrder(sequence = "1")
    public Profile getDemandProfile() {
        return demandProfile;
    }

    public void setDemandProfile(final Profile demandProfile) {
        this.demandProfile = demandProfile;
    }
    //endregion

    //region > demandProfileElement (property)
    private ProfileElement demandProfileElement;

    @MemberOrder(sequence = "1")
    public ProfileElement getDemandProfileElement() {
        return demandProfileElement;
    }

    public void setDemandProfileElement(final ProfileElement demandProfileElement) {
        this.demandProfileElement = demandProfileElement;
    }
    //endregion

}
