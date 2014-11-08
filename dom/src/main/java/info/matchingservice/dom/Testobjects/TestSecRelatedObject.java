package info.matchingservice.dom.Testobjects;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.VersionStrategy;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Party.Person;

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
