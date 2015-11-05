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

import java.util.List;
import java.util.UUID;
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
    public List<Profile> allSupplyProfilesOfType(ProfileType profileType) {
        return allMatches("allSupplyProfilesOfType","profileType",profileType);
    }
    
    @Programmatic
    public List<Profile> allDemandProfilesOfType(ProfileType profileType) {
        return allMatches("allDemandProfilesOfType","profileType",profileType);
    }

    @Programmatic
    public List<Profile> allDemandProfilesOtherOwners(final String ownedBy) {
        return allMatches("allDemandProfilesOtherOwners", "ownedBy", ownedBy);
    }

    @Programmatic
    // for Api
    public List<Profile> findProfileByUniqueItemId(UUID uniqueItemId) {
        return allMatches("findProfileByUniqueItemId",
        		"uniqueItemId", uniqueItemId);
    }
    
    @Programmatic
    // for fixtures
    public List<Profile> searchNameOfProfilesOfTypeByOwner(String profileName, DemandOrSupply demandOrSupply, ProfileType profileType, String ownedBy) {
        return allMatches("searchNameOfProfilesOfTypeByOwner",
        		"profileName", profileName,
        		"demandOrSupply", demandOrSupply ,
        		"profileType",profileType, 
        		"ownedBy", ownedBy);
    }
    
    @Programmatic
    public List<Profile> findProfileByDemandProfileOwner(Demand demandProfileOwner) {
        return allMatches("findProfileByDemandProfileOwner","demandProfileOwner",demandProfileOwner);
    }
    
    @Programmatic
    public Profile createDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final Profile newDemandProfile = newTransientInstance(Profile.class);
        final UUID uuid=UUID.randomUUID();
        newDemandProfile.setUniqueItemId(uuid);
        newDemandProfile.setProfileName(demandProfileDescription);
        newDemandProfile.setWeight(weight);
        newDemandProfile.setProfileStartDate(demandOrSupplyProfileStartDate);
        newDemandProfile.setProfileEndDate(demandOrSupplyProfileEndDate);
        newDemandProfile.setDemandOrSupply(DemandOrSupply.DEMAND);
        newDemandProfile.setProfileType(profileType);
        newDemandProfile.setDemandProfileOwner(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        persist(newDemandProfile);
        return newDemandProfile;
    }
    
    @Programmatic
    public Profile createSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final Supply supplyProfileOwner,
            final String ownedBy
            ){
        final Profile newSupplyProfile = newTransientInstance(Profile.class);
        final UUID uuid=UUID.randomUUID();
        newSupplyProfile.setUniqueItemId(uuid);
        newSupplyProfile.setProfileName(supplyProfileDescription);
        newSupplyProfile.setWeight(weight);
        newSupplyProfile.setProfileStartDate(demandOrSupplyProfileStartDate);
        newSupplyProfile.setProfileEndDate(demandOrSupplyProfileEndDate);
        newSupplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
        newSupplyProfile.setProfileType(profileType);
        newSupplyProfile.setSupplyProfileOwner(supplyProfileOwner);
        newSupplyProfile.setOwnedBy(ownedBy);
        persist(newSupplyProfile);
        return newSupplyProfile;
    }

    // Api v1
    @Programmatic
    public Profile matchProfileApiId(final String id) {

        for (Profile p : allInstances(Profile.class)) {
            String[] parts = p.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            String ApiId = "L_".concat(part1);
            if (id.equals(ApiId)) {
                return p;
            }
        }

        return null;

    }
}
