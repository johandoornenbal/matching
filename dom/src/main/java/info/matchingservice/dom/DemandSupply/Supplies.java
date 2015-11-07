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

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.regex.Pattern;

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
    public List<Supply> findSupplyByDescription(String description, String ownedBy) {
        return allMatches("findSupplyByDescription",
        		"description", description,
        		"ownedBy", ownedBy);
    }

    @Programmatic
    public Supply createSupply(
            final String supplyDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner,
            final String ownedBy) {
        final Supply newSupply = newTransientInstance(Supply.class);
        newSupply.setDescription(supplyDescription);
        newSupply.setStartDate(demandOrSupplyProfileStartDate);
        newSupply.setEndDate(demandOrSupplyProfileEndDate);
        newSupply.setSupplyType(demandSupplyType);
        newSupply.setWeight(weight);
        newSupply.setSupplyOwner(supplyOwner);
        newSupply.setOwnedBy(ownedBy);
        persist(newSupply);
        getContainer().flush();
        return newSupply;
    }

    // Api v1
    @Programmatic
    public Supply matchSupplyApiId(final Integer id) {

        for (Supply s : allInstances(Supply.class)) {
            String[] parts = s.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            if (id.toString().equals(part1)) {
                return s;
            }
        }

        return null;

    }

}
