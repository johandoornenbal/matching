package info.matchingservice.dom.Assessment;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.Need.DemandProfile;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class VacancyProfileAssessment extends Assessment {
    
    private DemandProfile target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("doel")
    public DemandProfile getTarget() {
        return target;
    }
    
    public void setTarget(final DemandProfile object) {
        this.target = object;
    }       

}
