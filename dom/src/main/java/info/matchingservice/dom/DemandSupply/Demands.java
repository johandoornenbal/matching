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
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.regex.Pattern;

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
                "description", demandDescription,
                "ownedBy", ownedBy);
    }

    @Programmatic
    // for Api
    public List<Demand> findDemandByDescription(String demandDescription) {
        return allMatches("findDemandByDescriptionOnly",
                "description", demandDescription.toLowerCase());
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
            final String imageUrl,
            final String ownedBy) {
        final Demand newDemand = newTransientInstance(Demand.class);
        newDemand.setDescription(demandDescription);
        newDemand.setSummary(demandSummary);
        newDemand.setStory(demandStory);
        newDemand.setAttachment(demandAttachment);
        newDemand.setStartDate(demandOrSupplyProfileStartDate);
        newDemand.setEndDate(demandOrSupplyProfileEndDate);
        newDemand.setWeight(weight);
        newDemand.setDemandType(demandSupplyType);
        newDemand.setOwner(demandOwner);
        newDemand.setOwnedBy(ownedBy);
        newDemand.setTimeStamp(LocalDateTime.now());
        newDemand.setImageUrl(imageUrl);
        persist(newDemand);
        getContainer().flush();
        return newDemand;
    }

    // Api v1
    @Programmatic
    public Demand matchDemandApiId(final String id) {

        for (Demand d : allInstances(Demand.class)) {
            String[] parts = d.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            if (id.equals(part1)) {
                return d;
            }
        }

        return null;

    }
    
    

}
