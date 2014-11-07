package nl.yodo.dom.Party;

import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class YodoOrganisation extends YodoParty {

    private String organisationName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(final String name) {
        this.organisationName = name;
    }
    
}
