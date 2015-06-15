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

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/06/15.
 */
@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class PersonViewModelService {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<DemandLine> demands(Person person) {

        List<DemandLine> newDemands = new ArrayList<DemandLine>();
        for (Demand demand : person.getCollectDemands()) {

            for (Profile profile: demand.getCollectDemandProfiles()) {

                for (ProfileElement profileElement : profile.getCollectProfileElements()) {

                    DemandLine newLine = new DemandLine(demand, profile, profileElement);
                    newDemands.add(newLine);

                }

            }

        }

        return newDemands;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<SupplyLine> supplies(Person person) {

        List<SupplyLine> newSupplies = new ArrayList<SupplyLine>();
        for (Supply supply : person.getCollectSupplies()) {

            for (Profile profile: supply.getCollectSupplyProfiles()) {

                for (ProfileElement profileElement : profile.getCollectProfileElements()) {

                    SupplyLine newLine = new SupplyLine(supply, profile, profileElement);
                    newSupplies.add(newLine);

                }

            }

        }

        return newSupplies;
    }

}
