package info.matchingservice.dom.Actor;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import info.matchingservice.dom.MatchingSecureMutableObject;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

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
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "PERSON_ID_UNQ", members = "uniqueActorId")
})
public abstract class Actor extends MatchingSecureMutableObject<Actor> {
    
    
    public Actor() {
        super("uniqueActorId");
    }
        
    public String title() {
        return getUniqueActorId();
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
    
    private String uniqueActorId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getUniqueActorId() {
        return uniqueActorId;
    }
    
    public void setUniqueActorId(final String id) {
        this.uniqueActorId = id;
    }
}