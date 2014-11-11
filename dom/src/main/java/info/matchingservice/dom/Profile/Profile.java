package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Party.Person;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;

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
@AutoComplete(repository=Profiles.class,  action="autoComplete")
public class Profile extends MatchingSecureMutableObject<Profile> {

    public Profile() {
        super("profileName");
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
    
    private String profileName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfileName() {
        return profileName;
    }
    
    public void setProfileName(final String test) {
        this.profileName = test;
    }

    private Integer testFigureForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getTestFigureForMatching(){
        return testFigureForMatching;
    }
    
    public void setTestFigureForMatching(final Integer testfigure) {
        this.testFigureForMatching = testfigure;
    }
    
    private String testFieldForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    public String getTestFieldForMatching(){
        return testFieldForMatching;
    }
    
    public void setTestFieldForMatching(final String testtext) {
        this.testFieldForMatching = testtext;
    }
    
    
    private Person profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Person owner) {
        this.profileOwner =owner;
    }
    
    public String toString() {
        return "Profile: " + this.profileName;
    }

}
