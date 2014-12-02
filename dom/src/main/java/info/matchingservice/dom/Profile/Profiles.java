package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Demand.Demand;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Supply.Supply;

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
    
    @Programmatic
    public Profile newDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final ProfileNature profileNature,
            final ProfileType profileType,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final Profile newDemandProfile = newTransientInstance(Profile.class);
        newDemandProfile.setProfileName(demandProfileDescription);
        newDemandProfile.setWeight(weight);
        newDemandProfile.setProfileNature(profileNature);
        newDemandProfile.setProfileType(profileType);
        newDemandProfile.setProfileOwner(demandProfileOwner);
        newDemandProfile.setDemandProfileOwner(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        persist(newDemandProfile);
        return newDemandProfile;
    }
    
    @Programmatic
    public Profile newSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight,
            final ProfileNature profileNature,
            final ProfileType profileType,
            final Supply supplyProfileOwner,
            final String ownedBy
            ){
        final Profile newSupplyProfile = newTransientInstance(Profile.class);
        newSupplyProfile.setProfileName(supplyProfileDescription);
        newSupplyProfile.setWeight(weight);
        newSupplyProfile.setProfileNature(profileNature);
        newSupplyProfile.setProfileType(profileType);
        newSupplyProfile.setProfileOwner(supplyProfileOwner);
        newSupplyProfile.setOwnedBy(ownedBy);
        persist(newSupplyProfile);
        return newSupplyProfile;
    }
}
