package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.MatchingDomainService;

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


        communicationChannels.createPhone(person, type, phoneNumber);
        return person;
    }

    public List<CommunicationChannelType> choices1CreatePhone() {
        return CommunicationChannelType.matching(Phone.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createEmail(

            final @ParameterLayout(named="Email")String adress,
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named="Person/SHould not be visible")Person person)
            {

        communicationChannels.createEmail(adress, type, person);
                return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createAddress(

            final Person person,
            final @ParameterLayout(named = "Woning type") CommunicationChannelType type,
            final @ParameterLayout(named = "Addres") String address1,
            final @ParameterLayout(named = "Postcode") String postalCode

    ){


        communicationChannels.createAddress(person, type, address1, postalCode);
        return person;


}
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION, named = "Email Gegevens")
    public List<CommunicationChannel> allEmails(Person person){

        return communicationChannels.allCommunicationChannels(person, Email.class);


    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION, named =  "Telefoon Gegevens")
    public List<CommunicationChannel> allPhones(Person person){

        return communicationChannels.allCommunicationChannels(person, Phone.class);


    }



    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION, named = "Address Gegevens")
    public List<CommunicationChannel> allAddress(Person person){

        return communicationChannels.allCommunicationChannels(person, Address.class);


    }

   // public List<Person> autoComplete0CreatePhone(final String search){
   // 	return persons.findPersons(search);
  //  }


    @Inject
    CommunicationChannels communicationChannels;
    
    @Inject
    Persons persons;

}
