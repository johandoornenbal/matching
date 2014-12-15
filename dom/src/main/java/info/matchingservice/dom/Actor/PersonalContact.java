package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingTrustedContact;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonalContactUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE ownedBy == :ownedBy && contact == :contact")   ,
    @javax.jdo.annotations.Query(
            name = "findPersonalContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findPersonalContactReferringToMe", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.PersonalContact "
                    + "WHERE contact == :contact")                    
})
public class PersonalContact extends MatchingTrustedContact {
    
    private Person contactPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    public Person getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final Person contact) {
        this.contactPerson = contact;
    }
    
    public List<Person> autoCompleteContactPerson(String search) {
        return persons.findPersonsContains(search);
    }
 
    private Person ownerPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    @Hidden(where = Where.ALL_TABLES)
    public Person getOwnerPerson() {
        return ownerPerson;
    }

    public void setOwnerPerson(final Person ownerPerson) {
        this.ownerPerson = ownerPerson;
    }
    
    public String title(){
        return "Contact gelegd met " + getContactPerson().title();
    }
    
    @Inject
    Persons persons;

}
