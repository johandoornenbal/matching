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
    
    private YodoPerson contactYodoPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public YodoPerson getContactYodoPerson() {
        return contactYodoPerson;
    }

    public void setContactYodoPerson(final YodoPerson contact) {
        this.contactYodoPerson = contact;
    }
    
    public List<YodoPerson> autoCompleteContactYodoPerson(String search) {
        return yodopersons.findYodoPersons(search);
    }
       
    @Inject
    YodoPersons yodopersons;

}
