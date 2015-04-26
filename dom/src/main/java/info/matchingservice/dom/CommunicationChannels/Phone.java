package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.jdo.annotations.*;

import org.apache.isis.applib.annotation.ParameterLayout;

/**
 * Created by jonathan on 27-3-15.
 */

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
//@javax.jdo.annotations.DatastoreIdentity(
//        strategy = IdGeneratorStrategy.NATIVE,
//        column = "phone_id")
//@javax.jdo.annotations.Discriminator(
//        strategy = DiscriminatorStrategy.CLASS_NAME,
//        column = "discriminator")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.CommunicationChannels.Phone "
                        + "WHERE person == :person")
})


public class Phone extends CommunicationChannel {


    public Phone() {
        super("phone");
    }


    @Column(allowsNull = "false")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    private String phoneNumber;




    public Phone updatePhone(
            final @ParameterLayout(named = "Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Phone") String phoneNumber
    ){

        setType(type);
        setPhoneNumber(phoneNumber);
        return this;
    }

    public CommunicationChannelType default0UpdatePhone(){
        return getType();


    }

    public String default1UpdatePhone(){
        return getPhoneNumber();

    }

    public List<CommunicationChannelType> choices0UpdatePhone(){
        return CommunicationChannelType.matching(Phone.class);
    }


}
