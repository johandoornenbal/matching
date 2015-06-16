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

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.value.Blob;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = Demand.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Demands extends MatchingDomainService<Demand> {

    public Demands() {
        super(Demands.class, Demand.class);
    }
    
    @Programmatic
    public List<Demand> allDemands() {
        return allInstances();
    }
    
    @Programmatic
    // for fixtures
    public List<Demand> findDemandByDescription(String demandDescription, String ownedBy) {
        return allMatches("findDemandByDescription",
                "demandDescription", demandDescription,
                "ownedBy", ownedBy);
    }

    @Programmatic
    // for Api
    public List<Demand> findDemandByDescription(String demandDescription) {
        return allMatches("findDemandByDescriptionOnly",
                "demandDescription", demandDescription.toLowerCase());
    }
    
    @Programmatic
    // for Api
    public List<Demand> findDemandByUniqueItemId(UUID uniqueItemId) {
        return allMatches("findDemandByUniqueItemId",
        		"uniqueItemId", uniqueItemId);
    }

    @Programmatic
    public Demand createDemand(
            final String demandDescription,
            final String demandSummary,
            final String demandStory,
            final Blob demandAttachment,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner,
            final String ownedBy) {
        final Demand newNeed = newTransientInstance(Demand.class);
        final UUID uuid=UUID.randomUUID();
        newNeed.setUniqueItemId(uuid);
        newNeed.setDemandDescription(demandDescription);
        newNeed.setDemandSummary(demandSummary);
        newNeed.setDemandStory(demandStory);
        newNeed.setDemandAttachment(demandAttachment);
        newNeed.setDemandOrSupplyProfileStartDate(demandOrSupplyProfileStartDate);
        newNeed.setDemandOrSupplyProfileEndDate(demandOrSupplyProfileEndDate);
        newNeed.setWeight(weight);
        newNeed.setDemandType(demandSupplyType);
        newNeed.setDemandOwner(demandOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

    // Api v1
    @Programmatic
    public Demand matchDemandApiId(final String id) {

        for (Demand d : allInstances(Demand.class)) {
            String[] parts = d.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            String ApiId = "L_".concat(part1);
            if (id.equals(ApiId)) {
                return d;
            }
        }

        return null;

    }
    
    

}
