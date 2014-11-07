package nl.socrates.dom.party;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Optional;

import nl.socrates.dom.JdoColumnLength;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@AutoComplete(repository=Organisations.class,  action="autoComplete")
public class Organisation extends Party {
    private String organisationName;
    
    @javax.jdo.annotations.Column(allowsNull = "false", length = JdoColumnLength.NAME)
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(final String organisationName) {
        this.organisationName = organisationName;
    }
    
    public void updating() {
        setName(getOrganisationName());
    }
    
    private String contactpersoon;
    
    @javax.jdo.annotations.Column(allowsNull = "true", length = JdoColumnLength.NAME)
    @Optional
    public String getContactpersoon() {
        return contactpersoon;
    }
    
    public void setContactpersoon(final String contactpersoon) {
        this.contactpersoon = contactpersoon;
    }
    
}
