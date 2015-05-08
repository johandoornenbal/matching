package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.jdo.annotations.*;

import org.apache.isis.applib.annotation.*;

import info.matchingservice.dom.Actor.Person;

/**
 * Created by jonathan on 21-4-15.
 */



@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
//@javax.jdo.annotations.DatastoreIdentity(
//        strategy = IdGeneratorStrategy.NATIVE,
//        column = "id")
//@javax.jdo.annotations.Discriminator(
//        strategy = DiscriminatorStrategy.CLASS_NAME,
//        column = "discriminator")
//@javax.jdo.annotations.Version(
//        strategy = VersionStrategy.VERSION_NUMBER,
//        column = "version")
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


    private String woonPlaats;

    @Column(allowsNull = "false")
    public String getWoonPlaats() {
        return woonPlaats;
    }

    public void setWoonPlaats(final String woonPlaats) {
        this.woonPlaats = woonPlaats;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Address updateAddress(

            final @ParameterLayout(named = "Woning Type") CommunicationChannelType type,
            final @ParameterLayout(named = "Woonplaats")
            @Parameter(regexPattern = "^([a-zA-Z]{3,})")String woonPlaats,
            final @ParameterLayout(named = "Addres")
            @Parameter(regexPattern = "^([a-zA-Z.]{1,})(( [a-zA-Z.]{1,})*) ([0-9]{1,})([a-zA-Z]*)$") String address,


            final @ParameterLayout(named = "Postcode")
            @Parameter(regexPattern = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")String postalCode)

            {

                setType(type);
                setWoonPlaats(woonPlaats);
                setPostalCode(postalCode);
                setAddress(address);

        return this;
    }

    public CommunicationChannelType default0UpdateAddress(){
        return getType();
    }
    public String default1UpdateAddress(){
        return getWoonPlaats();
    }
    public String default2UpdateAddress(){
        return getAddress();
    }
    public String default3UpdateAddress(){
        return getPostalCode();
    }

    public List<CommunicationChannelType> choices0UpdateAddress(){
        return CommunicationChannelType.matching(Address.class);

    }



    private  String ownedBy;

    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden= Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }


}
