package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingSecureMutableObject;

import org.apache.isis.applib.annotation.Disabled;


public abstract class Actor extends MatchingSecureMutableObject<Actor> {
    
    
    public Actor() {
        super("uniqueActorId");
    }
        
    public String title() {
        return getUniqueActorId();
    }
    
    private String uniqueActorId;
    
    @Disabled
    public String getUniqueActorId() {
        return uniqueActorId;
    }
    
    public void setUniqueActorId(final String id) {
        this.uniqueActorId = id;
    }
}