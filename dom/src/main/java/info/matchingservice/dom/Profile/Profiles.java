package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.ProfileType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = Profile.class)
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    public List<Profile> allProfiles() {
        return allInstances();
    }
    
    public List<Profile> allSupplyProfiles() {
        return allMatches("allSupplyProfiles");
    }
    
    public List<Profile> allDemandProfiles() {
        return allMatches("allDemandProfiles");
    }
    
    public List<Profile> allSupplyProfilesOfType(ProfileType profileType) {
        return allMatches("allSupplyProfilesOfType","profileType",profileType);
    }
    
    public List<Profile> allDemandProfilesOfType(ProfileType profileType) {
        return allMatches("allDemandProfilesOfType","profileType",profileType);
    }
    
    @Programmatic
    public Profile newDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final ProfileType profileType,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final Profile newDemandProfile = newTransientInstance(Profile.class);
        newDemandProfile.setProfileName(demandProfileDescription);
        newDemandProfile.setWeight(weight);
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
            final ProfileType profileType,
            final Supply supplyProfileOwner,
            final String ownedBy
            ){
        final Profile newSupplyProfile = newTransientInstance(Profile.class);
        newSupplyProfile.setProfileName(supplyProfileDescription);
        newSupplyProfile.setWeight(weight);
        newSupplyProfile.setProfileType(profileType);
        newSupplyProfile.setSupplyProfileOwner(supplyProfileOwner);
        newSupplyProfile.setOwnedBy(ownedBy);
        persist(newSupplyProfile);
        return newSupplyProfile;
    }
}
