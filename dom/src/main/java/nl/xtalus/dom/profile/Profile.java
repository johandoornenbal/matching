package nl.xtalus.dom.profile;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.annotation.Bookmarkable;

import nl.xtalus.dom.user.User;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@Bookmarkable
public class Profile extends AbstractDomainObject implements Comparable<Profile> {

    User owner;

    @Column(allowsNull="false")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    ProfileType type;
    
    @Column(allowsNull="false")
    public ProfileType getType() {
        return type;
    }
    
    public void setType(ProfileType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Profile o) {
        return ComparisonChain.start()
                .compare(this.getOwner(), o.getOwner())
                .compare(this.getType(), o.getType())
                .result();        
    }
    
}
