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

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.joda.time.LocalDate;

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
    // for fixtures
    public List<Profile> searchNameOfProfilesOfTypeByOwner(String profileName, DemandOrSupply demandOrSupply, ProfileType profileType, String ownedBy) {
        return allMatches("searchNameOfProfilesOfTypeByOwner",
        		"profileName", profileName,
        		"demandOrSupply", demandOrSupply ,
        		"profileType",profileType, 
        		"ownedBy", ownedBy);
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
}
