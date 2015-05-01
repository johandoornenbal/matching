package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Parameter;
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



    // regex: of mobiel nummer, of vast telefoon nummer
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createPhone(
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Number")

            @Parameter(regexPattern ="(^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$)"
                    + "|(^(((\\\\+31|0|0031)6){1}[1-9]{1}[0-9]{7})$)") String phoneNumber,
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
            final @ParameterLayout(named="Email")
            @Parameter(regexPattern ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")String adress,
            final @ParameterLayout(named="Person/SHould not be visible")Person person)
            {

        communicationChannels.createEmail(adress, type, person);
                return person;
    }


    public List<CommunicationChannelType> choices0CreateEmail(){
        return CommunicationChannelType.matching(Email.class);

    }

    // Address, 3 of meer letters gevolgd door cijfer, met evt een letter
    // postcode 4 cijfers, evt spatie. gevolgd door 2 Hoofdletters
    // Woonplaats, 3 of meer letters
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createAddress(

            final @ParameterLayout(named = "Woning Type")
            CommunicationChannelType type,
            final @ParameterLayout(named = "Woonplaats")
            @Parameter(regexPattern = "^([a-zA-Z]{3,})")String woonPlaats,
            final @ParameterLayout(named = "Addres")
            @Parameter(regexPattern = "^([a-zA-Z]{3,}) ([0-9]{1,})([a-zA-Z]*)$") String address,
            final @ParameterLayout(named = "Postcode")
            @Parameter(regexPattern = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")String postalCode,
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
    public List<CommunicationChannel> collectEmails(Person person){

        return communicationChannels.allCommunicationChannels(person, Email.class);


    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION, named =  "Collect Phones")
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> collectPhones(Person person){

        return communicationChannels.allCommunicationChannels(person, Phone.class);


    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION, named = "Collect Adress")
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> collectAddresses(Person person){

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
