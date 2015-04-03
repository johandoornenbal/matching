package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingService;
import org.apache.isis.applib.annotation.*;

import javax.inject.Inject;

/**
 * Created by jonathan on 3-4-15.
 */
public abstract class CommunicationChannelContributions extends MatchingService<CommunicationChannelContributions>{


    protected CommunicationChannelContributions(Class<? extends MatchingService<CommunicationChannelContributions>> serviceType) {
        super(serviceType);
    }


    // geen idee waarom hij hem niet koppeld aan de creatPhone in communicationChannels
    @MemberOrder(name = "CommunicationChannels", sequence = "2")
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createPhone(
            final @ParameterLayout(named = "Person") Person person,
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Address") String phoneNumber) {

        //final CommunicationChannelType type = CommunicationChannelType.PHONE_NUMBER;
        communicationChannels.createPhone(person, type, phoneNumber);
        return person;
    }


    @Inject
    CommunicationChannels communicationChannels;

}
