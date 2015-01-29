package info.matchingservice.dom.Profile;

import java.util.UUID;

import info.matchingservice.dom.MatchingDomainService;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = ProfileElementText.class)
public class ProfileElementTexts extends MatchingDomainService<ProfileElementText> {

    public ProfileElementTexts() {
        super(ProfileElementTexts.class, ProfileElementText.class);
    }

    @Programmatic
    public ProfileElementText newProfileElementText(
            final String description,
            final Integer weight,
            final String textValue,
            final ProfileElementType profileElementCategory,
            final Profile profileOwner
            ){
        final ProfileElementText newProfileElement = newTransientInstance(ProfileElementText.class);
        final UUID uuid=UUID.randomUUID();
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setTextValue(textValue);
        newProfileElement.setDisplayValue(textValue);
        newProfileElement.setProfileElementType(profileElementCategory);
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
