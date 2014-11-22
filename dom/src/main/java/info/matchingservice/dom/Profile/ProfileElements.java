package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "60", repositoryFor = ProfileElement.class)
@Named("Alle profiel elementen")
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
    
    @Programmatic
    public P_DropDownElement newDropdownElement(
            final Quality keyword,
            final Profile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature           
            ){
        final P_DropDownElement newElement = newTransientInstance(P_DropDownElement.class);
        newElement.setKeyword(keyword);
        newElement.setProfileElementNature(nature);
        newElement.setProfileElementType(ProfileElementType.MATCHABLE_DROPDOWN);
        newElement.setProfileElementDescription(keyword.toString());
        newElement.setProfileElementOwner(profileElementOwner);
        newElement.setOwnedBy(ownedBy);
        persist(newElement);
        return newElement;
    }
    
    //TODO: deze werkt niet
    @Programmatic
    public P_DropDownElement newDropdownElementForFixture(){
        final P_DropDownElement newElement = newTransientInstance(P_DropDownElement.class);
        newElement.setKeyword(qualities.allQualities().get(0));
        newElement.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT);
        newElement.setProfileElementType(ProfileElementType.MATCHABLE_DROPDOWN);
        newElement.setProfileElementDescription(qualities.allQualities().get(0).toString());
        newElement.setProfileElementOwner(profiles.allProfiles().get(0));
        newElement.setOwnedBy("frans");
        persist(newElement);
        return newElement;
    }
    
    @Inject
    Qualities qualities;
    
    @Inject
    Profiles profiles;
    
}
