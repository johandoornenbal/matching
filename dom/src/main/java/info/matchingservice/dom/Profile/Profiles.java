package info.matchingservice.dom.Profile;

import java.util.List;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Party.Party;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@DomainService(menuOrder = "30", repositoryFor = Profile.class)
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    public List<Profile> allProfiles() {
        return container.allInstances(Profile.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public Profile newProfile(
            final String testfield,
            final Party profileowner
            ){
        return newProfile(testfield, profileowner, currentUserName());
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Profile newProfile(
            final String testfield,
            final Party profileowner,
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
