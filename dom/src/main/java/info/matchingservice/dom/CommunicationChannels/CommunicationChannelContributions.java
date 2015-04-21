package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.annotation.*;

import javax.inject.Inject;

/**
 * Created by jonathan on 3-4-15.
 */
@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class CommunicationChannelContributions extends MatchingDomainService<CommunicationChannel>{


	public CommunicationChannelContributions() {
		super(CommunicationChannelContributions.class, CommunicationChannel.class);
	}



  //  @MemberOrder(name = "CommunicationChannels", sequence = "2")
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createPhone(
            final @ParameterLayout(named = "Person") Person person,
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Number") String phoneNumber) {

        //final CommunicationChannelType type = CommunicationChannelType.PHONE_NUMBER;
        communicationChannels.createPhone(person, type, phoneNumber);
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createEmail(

            final @ParameterLayout(named="Email")String adress,
            final @ParameterLayout(named="Person/SHould not be visible")Person person)
            {

        communicationChannels.createEmail(adress, person);
                return person;
    }





   // public List<Person> autoComplete0CreatePhone(final String search){
   // 	return persons.findPersons(search);
  //  }


    @Inject
    CommunicationChannels communicationChannels;
    
    @Inject
    Persons persons;

}
