package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;

import javax.jdo.annotations.*;

/**
 * Created by jonathan on 27-3-15.
 */

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")

@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.CommunicationChannels.Phone "
                        + "WHERE person == :person")
})


public class Phone extends CommunicationChannel {


    public Phone() {
        super("phone, person");
    }


    @Column(allowsNull = "false")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    private String phoneNumber;



}
