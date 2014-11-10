package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Party.Person;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE ownedBy == :ownedBy")
})
public class Profile extends MatchingSecureMutableObject<Profile> {

    public Profile() {
        super("testField");
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
    
    private String testField;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getTestField() {
        return testField;
    }
    
    public void setTestField(final String test) {
        this.testField = test;
    }
    
    
    private Person profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Person owner) {
        this.profileOwner =owner;
    }

}
