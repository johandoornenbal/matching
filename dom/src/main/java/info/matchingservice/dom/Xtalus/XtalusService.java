package info.matchingservice.dom.Xtalus;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainService;
import org.isisaddons.services.postalcode.Location;
import org.joda.time.LocalDate;

import javax.inject.Inject;

/**
 * Created by jonathan on 4-1-16.
 */
@DomainService
public class XtalusService {


    @Inject
    DomainObjectContainer container;

    @Inject
    Api api;

    @Inject
    Profiles profiles;

    public XtalusProfile createProfile(final Person person, final String backgroundImgUrl, final String story, final String profileImgUrl){

        XtalusProfile profile = container.newTransientInstance(XtalusProfile.class);
        profile.setPerson(person);
        profile.setBackgroundImage(backgroundImgUrl);
        profile.setStory(story);
        profile.setProfileImage(profileImgUrl);

        container.persistIfNotAlready(profile);
        return profile;

    }

    public Profile createInterestProfile(final Person person, final String description, LocalDate from, final LocalDate till, final int timeAvailable, int weight){
        Supply personSupply = api.getPersonalSupply(person);
        Profile p = profiles.createSupplyProfile(description, 10, from, till, ProfileType.INTEREST_PROFILE, "", personSupply, person.getOwnedBy());
        p.createTimeAvailableElement(weight, timeAvailable);
        return p;

    }


}
