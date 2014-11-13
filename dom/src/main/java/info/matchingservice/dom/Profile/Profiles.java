package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Party.Person;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "30", repositoryFor = Profile.class)
@Named("Profielen")
public class Profiles extends MatchingDomainService<Profile> {

    public Profiles() {
        super(Profiles.class, Profile.class);
    }
    
    @Named("Alle profielen")
    public List<Profile> allProfiles() {
        return container.allInstances(Profile.class);
    }
    
    //NOTE:
    // No newProfile method here. It will be set on Party instance by action

    
    // Region>helpers ////////////////////////////
    
    @Programmatic //newProfile can now be made by fixtures
    @Named("Nieuw profiel")
    public Profile newProfile(
            @Named("Profiel naam")
            final String profileName,
            final Person profileowner,
            final String ownedBy
            ){
        final Profile newProfile = newTransientInstance(Profile.class);
        newProfile.setProfileName(profileName);
        newProfile.setProfileOwner(profileowner);
        newProfile.setOwnedBy(ownedBy);
        persist(newProfile);
        return newProfile;
    }
    
    @Programmatic //newProfile can now be made by fixtures
    @Named("Nieuw profiel")
    public Profile newProfile(
            @Named("Profiel naam")
            final String profileName,
            @Named("Tekst voor matching")
            @MultiLine
            final String testFieldForMatching,
            @Named("Getal voor matching")
            final Integer testfigure,
            final Person profileowner,
            final String ownedBy
            ){
        final Profile newProfile = newTransientInstance(Profile.class);
        newProfile.setProfileName(profileName);
        newProfile.setTestFieldForMatching(testFieldForMatching);
        newProfile.setTestFigureForMatching(testfigure);
        newProfile.setProfileOwner(profileowner);
        newProfile.setOwnedBy(ownedBy);
        persist(newProfile);
        return newProfile;
    }
    
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
