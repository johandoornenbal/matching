package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "60", repositoryFor = ProfileElement.class)
@Named("Profiel elementen")
@Hidden
public class ProfileElements extends MatchingDomainService<ProfileElement> {

    public ProfileElements() {
        super(ProfileElements.class, ProfileElement.class);
    }
    
    @Named("Alle profiel elementen")
    public List<ProfileElement> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public ProfileElement newProfileElement(
            final String profileElementDescription,
            final Profile profileElementOwner,
            final String ownedBy
            ){
        final ProfileElement newProf = newTransientInstance(ProfileElement.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public ProfileElement newProfileElement(
            final String profileElementDescription,
            final Profile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final ProfileElement newProf = newTransientInstance(ProfileElement.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public ProfileElement newProfileElement(
            final String profileElementDescription,
            final Profile profileElementOwner,
            final String ownedBy,
            final ProfileElementType type,
            final ProfileElementNature nature
            ){
        final ProfileElement newProf = newTransientInstance(ProfileElement.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(type);
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
