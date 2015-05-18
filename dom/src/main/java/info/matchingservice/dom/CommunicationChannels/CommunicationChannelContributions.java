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

            @Parameter(regexPattern ="(^(((0)[1-9]{2}[0-9][-| ]?[1-9]( ?[0-9]){5})|((\\+31|0|0031)[-| ]?[1-9][0-9][1-9]( ?[0-9]){6}))$)"
                    + "|(^(((\\\\+31|0|0031)6){1}[-| ]?[1-9]{1}( ?[0-9]){7})$)") String phoneNumber,
            final @ParameterLayout(named = "Person") Person person,
            final String ownedBy){

        communicationChannels.createPhone(person, type, phoneNumber);
        return person;
    }

    public List<CommunicationChannelType> choices0CreatePhone() {
        return CommunicationChannelType.matching(Phone.class);
    }

    //Adding businessRule: only one Email of type EMAIL_MAIN
    //This one will be synced with the one used for security

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createEmail(

            final @ParameterLayout(named = "type") CommunicationChannelType type,
            final @ParameterLayout(named="address")
            @Parameter(regexPattern ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")String address,
            final @ParameterLayout(named="person")Person person)
            {

        communicationChannels.createEmail(address, type, person);
                return person;
    }


    public List<CommunicationChannelType> choices0CreateEmail(){
        return CommunicationChannelType.matching(Email.class);

    }

    public String validateCreateEmail(CommunicationChannelType type, String address, Person person){

        if (
                type.equals(CommunicationChannelType.EMAIL_MAIN)
                &&
                communicationChannels.findCommunicationChannelByPersonAndType(person, type).size() > 0
                ) {

            return "ONE_INSTANCE_AT_MOST";

        }
        return null;
    }

    // Address, 3 of meer letters gevolgd door cijfer, met evt een letter
    // postcode 4 cijfers, evt spatie. gevolgd door 2 Hoofdletters

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Person createAddress(

            final @ParameterLayout(named = "type")
            CommunicationChannelType addresType,
            final @ParameterLayout(named = "town")
            String town,
            final @ParameterLayout(named = "address")
            @Parameter(regexPattern = "^([a-zA-Z.]{1,})(( [a-zA-Z.]{1,})*) ([0-9]{1,})([a-zA-Z]*)$") String address,
            final @ParameterLayout(named = "postalCode")
            @Parameter(regexPattern = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")String postalCode,
            final Person person){




        communicationChannels.createAddress(person, addresType, address, postalCode, town);
        return person;
    }

    public List<CommunicationChannelType> choices0CreateAddress(){
        return CommunicationChannelType.matching(Address.class);

    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> collectEmails(Person person){

        return communicationChannels.allCommunicationChannels(person, Email.class);


    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<CommunicationChannel> collectPhones(Person person){

        return communicationChannels.allCommunicationChannels(person, Phone.class);


    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
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
