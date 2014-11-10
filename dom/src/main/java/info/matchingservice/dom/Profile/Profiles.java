package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Party.Person;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "30", repositoryFor = Profile.class)
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    public List<Profile> allProfiles() {
        return container.allInstances(Profile.class);
    }
    
    //NOTE:
    // No newProfile method here. It will be set on Party instance by action

    
    // Region>helpers ////////////////////////////
    
    @Programmatic //newProfile can now be made by fixtures
    public Profile newProfile(
            final String testfield,
            final Person profileowner,
            final String ownedBy
            ){
        final Profile newProfile = newTransientInstance(Profile.class);
        newProfile.setTestField(testfield);
        newProfile.setProfileOwner(profileowner);
        newProfile.setOwnedBy(ownedBy);
        persist(newProfile);
        return newProfile;
    }
    
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
