package info.matchingservice.dom.CommunicationChannels;

import java.util.List;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;

/**
 * Created by jonathan on 21-4-15.
 */



@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByPerson", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.CommunicationChannels.Address "
                        + "WHERE person == :person")
})
public class Address extends CommunicationChannel {
    public Address() {

        //super("address");
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


    private String town;

    @Column(allowsNull = "false")
    public String getTown() {
        return town;
    }

    public void setTown(final String town) {
        this.town = town;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(contributed = Contributed.AS_ACTION)
    public Address updateAddress(

            final @ParameterLayout(named = "type") CommunicationChannelType type,
            final @ParameterLayout(named = "town")
            @Parameter(regexPattern = "^([a-zA-Z]{3,})")String town,
            final @ParameterLayout(named = "address")
            String address,
            final @ParameterLayout(named = "postalCode")
            @Parameter(regexPattern = "^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")String postalCode)

            {

                setType(type);
                setTown( town);
                setPostalCode(postalCode);
                setAddress(address);

        return this;
    }

    public CommunicationChannelType default0UpdateAddress(){
        return getType();
    }
    public String default1UpdateAddress(){
        return getTown();
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
