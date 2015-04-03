package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Created by jonathan on 3-4-15.
 */
@DomainService(nature=NatureOfService.VIEW)
public class CommunicationChannelContributions extends MatchingDomainService<CommunicationChannel>{


	public CommunicationChannelContributions() {
		super(CommunicationChannelContributions.class, CommunicationChannel.class);
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
