package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = ProfileElementDropDown.class)
public class ProfileElementDropDowns extends MatchingDomainService<ProfileElementDropDown> {

    public ProfileElementDropDowns() {
        super(ProfileElementDropDowns.class, ProfileElementDropDown.class);
    }

    @Programmatic
    public ProfileElementDropDown newProfileElementDropDown(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown,
            final ProfileElementType profileElementCategory,
            final Profile profileOwner
            ){
        final ProfileElementDropDown newProfileElement = newTransientInstance(ProfileElementDropDown.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setDropDownValue(dropDown);
        newProfileElement.setDisplayValue(dropDown.getValue());
        newProfileElement.setProfileElementType(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setOwnedBy(currentUserName());
        persist(newProfileElement);
        return newProfileElement;
    }
    
    @Programmatic
    public ProfileElementDropDown newProfileElementDropDown(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown,
            final ProfileElementType profileElementCategory,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementDropDown newProfileElement = newTransientInstance(ProfileElementDropDown.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setDropDownValue(dropDown);
        newProfileElement.setDisplayValue(dropDown.getValue());
        newProfileElement.setProfileElementType(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setOwnedBy(ownedBy);
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
