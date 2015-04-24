package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
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




    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createPhone(
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Number") String phoneNumber,
            final @ParameterLayout(named = "Person") Person person){


        communicationChannels.createPhone(person, type, phoneNumber);
        return person;
    }

    public List<CommunicationChannelType> choices0CreatePhone() {
        return CommunicationChannelType.matching(Phone.class);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createEmail(

            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named="Email")String adress,
            final @ParameterLayout(named="Person/SHould not be visible")Person person)
            {

        communicationChannels.createEmail(adress, type, person);
                return person;
    }


    public List<CommunicationChannelType> choices0CreateEmail(){
        return CommunicationChannelType.matching(Email.class);

    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createAddress(

            final @ParameterLayout(named = "Woning Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Woonplaats") String woonPlaats,
            final @ParameterLayout(named = "Addres") String address,
            final @ParameterLayout(named = "Postcode") String postalCode,
            final Person person){


        communicationChannels.createAddress(person, type, address, postalCode, woonPlaats);
        return person;
    }

    public List<CommunicationChannelType> choices0CreateAddress(){
        return CommunicationChannelType.matching(Address.class);

    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION, named = "Collect Emails")
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> allEmails(Person person){

        return communicationChannels.allCommunicationChannels(person, Email.class);


    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION, named =  "Collect Phones")
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> allPhones(Person person){

        return communicationChannels.allCommunicationChannels(person, Phone.class);


    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION, named = "Collect Adress")
    @CollectionLayout(render = RenderType.EAGERLY)
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
