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

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.regex.Pattern;

@DomainService(repositoryFor = Profile.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    @Programmatic
    public List<Profile> allProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public List<Profile> allSupplyProfiles() {
        return allMatches("allSupplyProfiles");
    }

    @Programmatic
    public List<Profile> allSupplyProfilesOtherOwners(final String ownedBy) {
        return allMatches("allSupplyProfilesOtherOwners", "ownedBy", ownedBy);
    }
    
    @Programmatic
    public List<Profile> allDemandProfiles() {
        return allMatches("allDemandProfiles");
    }
    
    @Programmatic
    public List<Profile> allSupplyProfilesOfType(ProfileType type) {
        return allMatches("allSupplyProfilesOfType","type",type);
    }
    
    @Programmatic
    public List<Profile> allDemandProfilesOfType(ProfileType type) {
        return allMatches("allDemandProfilesOfType","type",type);
    }

    @Programmatic
    public List<Profile> allDemandProfilesOtherOwners(final String ownedBy) {
        return allMatches("allDemandProfilesOtherOwners", "ownedBy", ownedBy);
    }
    
    @Programmatic
    // for fixtures
    public List<Profile> searchNameOfProfilesOfTypeByOwner(String name, DemandOrSupply demandOrSupply, ProfileType type, String ownedBy) {
        return allMatches("searchNameOfProfilesOfTypeByOwner",
        		"name", name,
        		"demandOrSupply", demandOrSupply ,
        		"type",type,
        		"ownedBy", ownedBy);
    }
    
    @Programmatic
    public List<Profile> findProfileByDemandProfileOwner(Demand demand) {
        return allMatches("findProfileByDemandProfileOwner","demand",demand);
    }
    
    @Programmatic
    public Profile createDemandProfile(
            final String name,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final String imageUrl,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final Profile newDemandProfile = newTransientInstance(Profile.class);
        newDemandProfile.setName(name);
        newDemandProfile.setWeight(weight);
        newDemandProfile.setStartDate(demandOrSupplyProfileStartDate);
        newDemandProfile.setEndDate(demandOrSupplyProfileEndDate);
        newDemandProfile.setDemandOrSupply(DemandOrSupply.DEMAND);
        newDemandProfile.setType(profileType);
        newDemandProfile.setImageUrl(imageUrl);
        newDemandProfile.setDemand(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        newDemandProfile.setTimeStamp(LocalDateTime.now());
        persist(newDemandProfile);
        getContainer().flush();
        return newDemandProfile;
    }
    
    @Programmatic
    public Profile createSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final String imageUrl,
            final Supply supplyProfileOwner,
            final String ownedBy
            ){
        final Profile newSupplyProfile = newTransientInstance(Profile.class);
        newSupplyProfile.setName(supplyProfileDescription);
        newSupplyProfile.setWeight(weight);
        newSupplyProfile.setStartDate(demandOrSupplyProfileStartDate);
        newSupplyProfile.setEndDate(demandOrSupplyProfileEndDate);
        newSupplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
        newSupplyProfile.setType(profileType);
        newSupplyProfile.setImageUrl(imageUrl);
        newSupplyProfile.setSupply(supplyProfileOwner);
        newSupplyProfile.setOwnedBy(ownedBy);
        newSupplyProfile.setTimeStamp(LocalDateTime.now());
        persist(newSupplyProfile);
        getContainer().flush();
        return newSupplyProfile;
    }

    // Api v2
    @Programmatic
    public Profile matchProfileApiId(final Integer id) {

        for (Profile p : allInstances(Profile.class)) {
            String[] parts = p.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            if (id.toString().equals(part1)) {
                return p;
            }
        }

        return null;

    }
}
