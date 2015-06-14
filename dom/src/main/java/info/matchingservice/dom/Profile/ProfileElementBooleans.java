package info.matchingservice.dom.Profile;

import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jonathan on 22-4-15.
 */

@DomainService(repositoryFor = ProfileElementBoolean.class)
public class ProfileElementBooleans extends MatchingDomainService<ProfileElementBoolean> {


    public ProfileElementBooleans() {
        super(ProfileElementBooleans.class, ProfileElementBoolean.class);
    }

    @Programmatic
    public ProfileElementBoolean createProfileElementBoolean(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner
    ){
        return createProfileElementBoolean(description, weight, profileElementType, profileOwner, currentUserName());
    }

    // without currentUser (for fixtures)
    @Programmatic
    public ProfileElementBoolean createProfileElementBoolean(
            final String description,
            final Integer weight,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final String ownedBy
    ){
        final ProfileElementBoolean newProfileElement = newTransientInstance(ProfileElementBoolean.class);
        final UUID uuid=UUID.randomUUID();


        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setWeight(weight);
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setIsActive(true);
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
