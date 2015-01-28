package info.matchingservice.dom.Actor;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;

import info.matchingservice.dom.MatchingDomainObject;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public abstract class Role extends MatchingDomainObject<Role> {
    
    public Role() {
        super("role");
    }
    
    private String ownedBy;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(final String owner) {
        this.ownedBy=owner;
    }
    
    private String targetApplication;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(optional=Optionality.TRUE)
    public String getTargetApplication() {
        return targetApplication;
    }
    
    public void setTargetApplication(final String app) {
        this.targetApplication = app;
    }

}
