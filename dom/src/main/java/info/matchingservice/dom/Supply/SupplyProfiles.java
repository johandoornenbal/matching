package info.matchingservice.dom.Supply;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = SupplyProfile.class)
public class SupplyProfiles extends MatchingDomainService<SupplyProfile> {

    public SupplyProfiles() {
        super(SupplyProfiles.class, SupplyProfile.class);
    }
    
    @Named("Alle aanbod")
    public List<SupplyProfile> allSupplyProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public SupplyProfile newSupplyProfile(
            final String supplyProfileDescription,
            final Supply supplyProfileOwner,
            final String ownedBy
            ){
        final SupplyProfile supplyProfile = newTransientInstance(SupplyProfile.class);
        supplyProfile.setProfileName(supplyProfileDescription);
        supplyProfile.setSupplyProfileOwner(supplyProfileOwner);
        supplyProfile.setOwnedBy(ownedBy);
        persist(supplyProfile);
        return supplyProfile;
    }

}
