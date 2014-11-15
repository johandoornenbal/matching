package info.matchingservice.dom.Actor;

import org.apache.isis.applib.annotation.Optional;

import info.matchingservice.dom.MatchingDomainObject;

public abstract class Role extends MatchingDomainObject<Role> {
    
    public Role() {
        super("role");
    }
    
    private String targetApplication;
    
    @Optional
    public String getTargetApplication() {
        return targetApplication;
    }
    
    public void setTargetApplication(final String app) {
        this.targetApplication = app;
    }

}
