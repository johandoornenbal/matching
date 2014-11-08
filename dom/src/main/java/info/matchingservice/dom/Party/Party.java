package info.matchingservice.dom.Party;

import info.matchingservice.dom.MatchingSecureMutableObject;

import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Disabled;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "Party_ID_UNQ", members = "uniquePartyId")
})
public abstract class Party extends MatchingSecureMutableObject<Party> {
    
    public Party() {
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