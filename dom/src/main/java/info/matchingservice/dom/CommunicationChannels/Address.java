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

@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.CommunicationChannels.Address "
                        + "WHERE person == :person")
})
public class Address extends CommunicationChannel {
    public Address() {
        super("person, address");
    }


    private String address1;


    @Column(allowsNull = "false")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String adress1) {
        this.address1 = adress1;
    }


    private String address2;


    @Column(allowsNull = "true")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    private String address3;

    @Column(allowsNull = "true")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
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
