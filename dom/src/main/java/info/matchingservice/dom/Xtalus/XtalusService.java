package info.matchingservice.dom.Xtalus;

import info.matchingservice.dom.Actor.Person;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;

import javax.inject.Inject;

/**
 * Created by jonathan on 4-1-16.
 */
@DomainService
public class XtalusService {


    @Inject
    DomainObjectContainer container;

    public XtalusProfile createProfile(final Person person, final String backgroundImgUrl, final String story, final String profileImgUrl){

        XtalusProfile profile = container.newTransientInstance(XtalusProfile.class);
        profile.setPerson(person);
        profile.setBackgroundImage(backgroundImgUrl);
        profile.setStory(story);
        profile.setProfileImage(profileImgUrl);

        container.persistIfNotAlready(profile);
        return profile;

    }


}
