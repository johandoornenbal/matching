package nl.yodo.dom.Party;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class YodoOrganisation extends YodoParty {

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
    
    private String organisationName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(final String name) {
        this.organisationName = name;
    }
    
}
