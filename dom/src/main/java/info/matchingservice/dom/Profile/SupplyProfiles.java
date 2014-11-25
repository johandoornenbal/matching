package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "30", repositoryFor = SupplyProfile.class)
@Named("Profielen")
public class SupplyProfiles extends MatchingDomainService<SupplyProfile> {

    public SupplyProfiles() {
        super(SupplyProfiles.class, SupplyProfile.class);
    }
    
    @Named("Alle profielen")
    public List<SupplyProfile> allSupplyProfiles() {
        return container.allInstances(SupplyProfile.class);
    }
    
    //NOTE:
    // No newProfile method here. It will be set on Actor instance by action

    
    // Region>helpers ////////////////////////////
    
    @Programmatic //newProfile can now be made by fixtures
    @Named("Nieuw profiel")
    public SupplyProfile newProfile(
            @Named("Profiel naam")
            final String profileName,
            final Actor profileowner,
            final String ownedBy
            ){
        final SupplyProfile newProfile = newTransientInstance(SupplyProfile.class);
        newProfile.setProfileName(profileName);
        newProfile.setProfileOwner(profileowner);
        newProfile.setOwnedBy(ownedBy);
        persist(newProfile);
        return newProfile;
    }
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
