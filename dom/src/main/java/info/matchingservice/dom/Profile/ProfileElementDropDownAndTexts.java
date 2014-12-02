package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = ProfileElementDropDownAndText.class)
public class ProfileElementDropDownAndTexts extends MatchingDomainService<ProfileElementDropDownAndText> {

    public ProfileElementDropDownAndTexts() {
        super(ProfileElementDropDownAndTexts.class, ProfileElementDropDownAndText.class);
    }

    @Programmatic
    public ProfileElementDropDownAndText newProfileElementDropDownAndText(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown,
            final String text,
            final ProfileElementCategory profileElementCategory,
            final Profile profileOwner
            ){
        final ProfileElementDropDownAndText newProfileElement = newTransientInstance(ProfileElementDropDownAndText.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setOptionalDropDownValue(dropDown);
        newProfileElement.setText(text);
        newProfileElement.setProfileElementCategory(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
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
