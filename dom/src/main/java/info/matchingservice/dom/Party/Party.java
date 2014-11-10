package info.matchingservice.dom.Party;

import info.matchingservice.dom.MatchingSecureMutableObject;

import org.apache.isis.applib.annotation.Disabled;

public abstract class Party extends MatchingSecureMutableObject<Party> {
    
    public Party() {
        super("uniquePartyId");
    }
    
    private String uniquePartyId;
    
    @Disabled
    public String getUniquePartyId() {
        return uniquePartyId;
    }
    
    public void setUniquePartyId(final String id) {
        this.uniquePartyId = id;
    }
}