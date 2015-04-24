package info.matchingservice.dom.CommunicationChannels;

import javax.jdo.annotations.*;

/**
 * Created by jonathan on 21-4-15.
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
                        + "FROM info.matchingservice.dom.CommunicationChannels.Address "
                        + "WHERE person == :person")
})
public class Address extends CommunicationChannel {
    public Address() {
        super("address");
    }


    private String address;


    @Column(allowsNull = "false")
    public String getAddress() {
        return address;
    }

    public void setAddress(String adress1) {
        this.address = adress1;
    }





    private String postalCode;

    @Column(allowsNull = "false")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }



}
