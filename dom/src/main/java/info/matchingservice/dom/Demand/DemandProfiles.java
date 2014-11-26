package info.matchingservice.dom.Demand;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = DemandProfile.class)
public class DemandProfiles extends MatchingDomainService<DemandProfile> {

    public DemandProfiles() {
        super(DemandProfiles.class, DemandProfile.class);
    }
    
    public List<DemandProfile> allDemandProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public DemandProfile newDemand(
            final String demandProfileDescription,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final DemandProfile newDemandProfile = newTransientInstance(DemandProfile.class);
        newDemandProfile.setProfileName(demandProfileDescription);
        newDemandProfile.setDemandProfileOwner(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        persist(newDemandProfile);
        return newDemandProfile;
    }
    
    @Programmatic
    public DemandProfile newDemand(
            final String demandProfileDescription,
            final Integer weight,
            final Demand demandProfileOwner,
            final String ownedBy
            ){
        final DemandProfile newDemandProfile = newTransientInstance(DemandProfile.class);
        newDemandProfile.setProfileName(demandProfileDescription);
        newDemandProfile.setWeight(weight);
        newDemandProfile.setDemandProfileOwner(demandProfileOwner);
        newDemandProfile.setOwnedBy(ownedBy);
        persist(newDemandProfile);
        return newDemandProfile;
    }

}
