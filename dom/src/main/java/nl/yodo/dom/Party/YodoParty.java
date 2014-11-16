package nl.yodo.dom.Party;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

import nl.yodo.dom.YodoSecureMutableObject;

//@javax.jdo.annotations.PersistenceCapable
//@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "YodoParty_ID_UNQ", members = "uniqueYodoPartyId")
})
public abstract class YodoParty extends YodoSecureMutableObject<YodoParty> {
    
    public YodoParty() {
        super("ownedBy");
    }
    
    private String ownedBy;
    
    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    private String uniqueYodoPartyId;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getUniqueYodoPartyId() {
        return uniqueYodoPartyId;
    }
    
    public void setUniqueYodoPartyId(final String id) {
        this.uniqueYodoPartyId = id;
    }
}
