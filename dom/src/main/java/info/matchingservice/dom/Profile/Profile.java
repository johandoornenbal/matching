package info.matchingservice.dom.Profile;

import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Party.Party;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "Profile_ID_UNQ", members = "uniqueProfileId")
})
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE ownedBy == :ownedBy")
})
public class Profile extends MatchingSecureMutableObject<Profile> {

    public Profile() {
        super("ownedBy");
    }
    
    private String testField;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getTestField() {
        return testField;
    }
    
    public void setTestField(final String test) {
        this.testField = test;
    }
    
    
    private Party profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Party getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Party owner) {
        this.profileOwner =owner;
    }

}
