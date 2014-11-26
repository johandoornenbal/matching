package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.Supply.Supply;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Disabled;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class SupplyAssessment extends Assessment {
    
    private Supply target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Supply getTarget() {
        return target;
    }
    
    public void setTarget(final Supply object) {
        this.target = object;
    }       

}
