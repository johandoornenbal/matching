package nl.yodo.dom.Party;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Disabled;

import nl.yodo.dom.YodoSecureMutableObject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "Party_ID_UNQ", members = "uniquePartyId")
})
public abstract class YodoParty extends YodoSecureMutableObject<YodoParty> {
    
    public YodoParty() {
        super("ownedBy");
    }
    
    private String uniquePartyId;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getUniquePartyId() {
        return uniquePartyId;
    }
    
    public void setUniquePartyId(final String id) {
        this.uniquePartyId = id;
    }
}
