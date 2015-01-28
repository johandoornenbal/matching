package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.DemandSupply.Supply;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class SupplyAssessment extends Assessment {
    
    private Supply target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(named="Doel")
    @Property(editing=Editing.DISABLED)
    public Supply getTarget() {
        return target;
    }
    
    public void setTarget(final Supply object) {
        this.target = object;
    }       

}
