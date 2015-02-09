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
import info.matchingservice.dom.Profile.ProfileType;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Where;
import org.joda.time.LocalDate;

@DomainService(repositoryFor = Profile.class)
@DomainServiceLayout(
        named="Aanbod en vraag",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    @ActionLayout(named="Alle profielen", hidden=Where.EVERYWHERE)
    public List<Profile> allProfiles() {
        return allInstances();
    }
    
    @ActionLayout(named="Alle aanbod profielen")
    public List<Profile> allSupplyProfiles() {
        return allMatches("allSupplyProfiles");
    }
    
    @ActionLayout(named="Alle vraagprofielen")
    public List<Profile> allDemandProfiles() {
        return allMatches("allDemandProfiles");
    }
    
    @ActionLayout(named="Alle aanbodprofielen van type...")
    public List<Profile> allSupplyProfilesOfType(ProfileType profileType) {
        return allMatches("allSupplyProfilesOfType","profileType",profileType);
    }
    
    @ActionLayout(named="Alle vraagprofielen van type...")
    public List<Profile> allDemandProfilesOfType(ProfileType profileType) {
        return allMatches("allDemandProfilesOfType","profileType",profileType);
    }
    
    @ActionLayout(named="Zoek profiel ...")
    @Programmatic
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
        newDemandProfile.setDemandOrSupplyProfileStartDate(demandOrSupplyProfileStartDate);
        newDemandProfile.setDemandOrSupplyProfileEndDate(demandOrSupplyProfileEndDate);
        newDemandProfile.setDemandOrSupply(DemandOrSupply.DEMAND);
        newDemandProfile.setProfileType(profileType);
        newDemandProfile.setDemandProfileOwner(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        persist(newDemandProfile);
        return newDemandProfile;
    }
    
    @Programmatic
    public Profile newSupplyProfile(
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
        newSupplyProfile.setDemandOrSupplyProfileStartDate(demandOrSupplyProfileStartDate);
        newSupplyProfile.setDemandOrSupplyProfileEndDate(demandOrSupplyProfileEndDate);
        newSupplyProfile.setDemandOrSupply(DemandOrSupply.SUPPLY);
        newSupplyProfile.setProfileType(profileType);
        newSupplyProfile.setSupplyProfileOwner(supplyProfileOwner);
        newSupplyProfile.setOwnedBy(ownedBy);
        persist(newSupplyProfile);
        return newSupplyProfile;
    }
}
