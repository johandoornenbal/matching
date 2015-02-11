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

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = Supply.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Supplies extends MatchingDomainService<Supply> {

    public Supplies() {
        super(Supplies.class, Supply.class);
    }
    
    @Programmatic
    public List<Supply> allSupplies() {
        return allInstances();
    }
    
    @Programmatic
    // for fixtures
    public List<Supply> findSupplyByDescription(String supplyDescription, String ownedBy) {
        return allMatches("findSupplyByDescription",
        		"supplyDescription", supplyDescription,
        		"ownedBy", ownedBy);
    }

    @Programmatic
    public Supply createSupply(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner,
            final String ownedBy) {
        final Supply newSupply = newTransientInstance(Supply.class);
        final UUID uuid=UUID.randomUUID();
        newSupply.setUniqueItemId(uuid);
        newSupply.setSupplyDescription(supplyDescription);
        newSupply.setSupplyType(demandSupplyType);
        newSupply.setWeight(weight);
        newSupply.setSupplyOwner(supplyOwner);
        newSupply.setOwnedBy(ownedBy);
        persist(newSupply);
        return newSupply;
    }

}
