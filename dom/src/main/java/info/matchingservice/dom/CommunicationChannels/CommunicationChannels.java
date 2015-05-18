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
           // final String ownedBy){

        final Phone phone = newTransientInstance(Phone.class);
        phone.setPerson(person);
        phone.setType(type);
        phone.setPhoneNumber(phoneNumber);
        phone.setOwnedBy(getContainer().getUser().getName());

        persistIfNotAlready(phone);
        return phone;



    }


    @Programmatic
    public Email createEmail(
            final String emailAddress,
            final CommunicationChannelType type,
            final Person person){

        final Email email = newTransientInstance(Email.class);

        email.setEmail(emailAddress);
        email.setPerson(person);
        email.setType(type);

        email.setOwnedBy(getContainer().getUser().getName());

        persist(email);
        return email;

    }

    @Programmatic
    public Address createAddress(
            final Person person,
            final CommunicationChannelType type,
            final String address,
            final String postalCode,
            final String woonPlaats



    ){


    final Address a = newTransientInstance(Address.class);

        a.setPerson(person);
        a.setType(type);
        a.setAddress(address);
        a.setPostalCode(postalCode);
        a.setTown(woonPlaats);
        a.setOwnedBy(getContainer().getUser().getName());

    persist(a);
    return a;






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
