package info.matchingservice.dom.Xtalus;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
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



    @Programmatic
    public Education createEducation(final Person person, final String institute, final String honoursProgramm, final String title){


        Education e = container.newTransientInstance(Education.class);

        e.setHonoursProgramm(honoursProgramm);
        e.setInstitute(institute);
        e.setTitle(title);
        e.setPerson(person);

        container.persistIfNotAlready(e);
        return e;

    }

    public Profile createInterestProfile(final info.matchingservice.dom.Actor.Person person, final String description, LocalDate from, final LocalDate till, final int timeAvailable, int weight){
        Supply personSupply = api.getPersonalSupply(person);
        Profile p = profiles.createSupplyProfile(description, 10, from, till, ProfileType.INTEREST_PROFILE, "", personSupply, person.getOwnedBy());
        p.createTimeAvailableElement(weight, timeAvailable);
        return p;

    }



}
