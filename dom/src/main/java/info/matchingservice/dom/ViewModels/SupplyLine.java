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

import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/06/15.
 */
@ViewModel
public class SupplyLine {

    public SupplyLine() {
    }

    public SupplyLine(
            Supply supply,
            Profile supplyProfile,
            ProfileElement supplyProfileElement) {
        this.supply = supply;
        this.supplyProfile = supplyProfile;
        this.supplyProfileElement = supplyProfileElement;
    }

    //region > demand (property)
    private Supply supply;

    @MemberOrder(sequence = "1")
    public Supply getSupply() {
        return supply;
    }

    public void setSupply(final Supply supply) {
        this.supply = supply;
    }
    //endregion

    //region > supplyProfile (property)
    private Profile supplyProfile;

    @MemberOrder(sequence = "1")
    public Profile getSupplyProfile() {
        return supplyProfile;
    }

    public void setSupplyProfile(final Profile supplyProfile) {
        this.supplyProfile = supplyProfile;
    }
    //endregion

    //region > supplyProfileElement (property)
    private ProfileElement supplyProfileElement;

    @MemberOrder(sequence = "1")
    public ProfileElement getSupplyProfileElement() {
        return supplyProfileElement;
    }

    public void setSupplyProfileElement(final ProfileElement supplyProfileElement) {
        this.supplyProfileElement = supplyProfileElement;
    }
    //endregion

}
