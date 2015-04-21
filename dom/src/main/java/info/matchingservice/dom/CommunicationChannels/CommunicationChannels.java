package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.annotation.*;

import java.util.List;

/**
 * Created by jonathan on 3-4-15.
 */
@DomainService(repositoryFor = CommunicationChannel.class, nature = NatureOfService.DOMAIN)
public class CommunicationChannels extends MatchingDomainService<CommunicationChannel>{

    public CommunicationChannels(){

        super(CommunicationChannels.class, CommunicationChannel.class);

    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public List<CommunicationChannel> allCommunicationChannels(){

        return allInstances();
    }

    // nu nog niet hidden omdat ik contribution niet kan koppelen.



    @Programmatic
    public Phone createPhone(
            final Person person,
            final CommunicationChannelType type,
            final String phoneNumber){

        final Phone phone = newTransientInstance(Phone.class);
        phone.setPerson(person);
        phone.setType(type);
        phone.setPhoneNumber(phoneNumber);

        persistIfNotAlready(phone);
        return phone;



    }


    @Programmatic
    public Email createEmail(
            final String adress,

            final Person person){

        final Email email = newTransientInstance(Email.class);

        email.setEmail(adress);
        email.setPerson(person);


        persist(email);
        return email;

    }


}
