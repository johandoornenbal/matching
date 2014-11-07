package nl.yodo.dom.Party;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Disabled;

import nl.yodo.dom.YodoTrustedContact;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findYodoPersonalContactUniqueContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPersonalContact "
                    + "WHERE ownedBy == :ownedBy && contact == :contact")   ,
    @javax.jdo.annotations.Query(
            name = "findYodoPersonalContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPersonalContact "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "findYodoPersonalContactReferringToMe", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.yodo.dom.Party.YodoPersonalContact "
                    + "WHERE contact == :contact")                    
})
public class YodoPersonalContact extends YodoTrustedContact {
    
    private YodoPerson contactPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public YodoPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final YodoPerson contact) {
        this.contactPerson = contact;
    }
    
    public List<YodoPerson> autoCompleteContactPerson(String search) {
        return yodopersons.findYodoPersons(search);
    }
       
    @Inject
    YodoPersons yodopersons;

}
