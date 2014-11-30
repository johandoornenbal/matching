package info.matchingservice.dom.Assessment;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.Demand.Demand;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Named;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class DemandAssessment extends Assessment {
    
    private Demand target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("doel")
    public Demand getTarget() {
        return target;
    }
    
    public void setTarget(final Demand object) {
        this.target = object;
    }       

}
