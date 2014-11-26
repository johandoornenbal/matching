package info.matchingservice.dom.Profile;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Dropdown.ProfileElementDropDown;

@DomainService(menuOrder = "60", repositoryFor = ProfileElement.class)
public class ProfileElements extends MatchingDomainService<ProfileElement> {

    public ProfileElements() {
        super(ProfileElements.class, ProfileElement.class);
    }

    @Programmatic
    public ProfileElement newProfileElement(
            final String description,
            final Integer weight,
            final ProfileElementDropDown dropDown,
            final ProfileElementCategory profileElementCategory,
            final ProfileElementRepresentation profileElementRepresentation,
            final Profile profileOwner,
            final ProfileElementNature nature,
            final ProfileElementType type
            ){
        final ProfileElement newProfileElement = newTransientInstance(ProfileElement.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setDropDownValue(dropDown);
        newProfileElement.setProfileElementCategory(profileElementCategory);
        newProfileElement.setProfileElementRepresentation(profileElementRepresentation);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setProfileElementNature(nature);
        newProfileElement.setProfileElementType(type);
        newProfileElement.setOwnedBy(currentUserName());
        persist(newProfileElement);
        return newProfileElement;
    }
    
    // Region>helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
