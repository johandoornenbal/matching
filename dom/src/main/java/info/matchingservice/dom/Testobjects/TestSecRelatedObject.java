package info.matchingservice.dom.Testobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Person;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
public class TestSecRelatedObject extends MatchingSecureMutableObject<TestSecRelatedObject>{

    public TestSecRelatedObject() {
        super("testVeld");
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
    
    
    private String testVeld;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getTestVeld() {
        return testVeld;
    }
    
    public void setTestVeld(final String string) {
        this.testVeld=string;
    }
    
    private Person ownerPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getOwnerPerson() {
        return ownerPerson;
    }
    
    public void setOwnerPerson(final Person owner) {
        this.ownerPerson=owner;
    }

}
