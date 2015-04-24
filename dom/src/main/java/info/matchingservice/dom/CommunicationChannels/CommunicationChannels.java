package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jonathan on 3-4-15.
 */
@DomainService(repositoryFor = CommunicationChannel.class, nature = NatureOfService.DOMAIN)
public class CommunicationChannels extends MatchingDomainService<CommunicationChannel>{

    public CommunicationChannels(){

        super(CommunicationChannels.class, CommunicationChannel.class);

    }

    @Programmatic
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

        persist(phone);
        return phone;



    }


    @Programmatic
    public Email createEmail(
            final String emailAddres,
            final CommunicationChannelType type,
            final Person person){

        final Email email = newTransientInstance(Email.class);

        email.setEmail(emailAddres);
        email.setPerson(person);


        persist(email);
        return email;

    }

    @Programmatic
    public Address createAddress(
            final Person person,
            final CommunicationChannelType type,
            final String address1,
            final String postalCode



    ){


    final Address address = newTransientInstance(Address.class);

    address.setPerson(person);
    address.setType(type);
    address.setAddress(address1);
    address.setPostalCode(postalCode);


    persist(address);
    return address;






    }

    /** gets the specified communicationchannel of the person,
     *
     * @param person the person
     * @param type the type of communicationchannel (subclass of comm channel)
     * @return
     */
    @Programmatic
    public List<CommunicationChannel> allCommunicationChannels(final Person person, Class type){
        QueryDefault<CommunicationChannel> query =
                QueryDefault.create(
                        type,
                        "findByPerson",
                        "person", person);
        return allMatches(query);

    }


    @Programmatic
    public List<CommunicationChannel> allCommunicationChannels(Class type){
        return getContainer().allInstances(type);

    }






}
