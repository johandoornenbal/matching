package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public abstract class Need extends MatchingSecureMutableObject<Need> {

    public Need() {
        super("ownedBy, needDescription");
    }
    
    private String ownedBy;
    
    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    private String needDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Opdracht omschrijving op tafel")
    public String getNeedDescription(){
        return needDescription;
    }
    
    public void setNeedDescription(final String description) {
        this.needDescription = description;
    }
    
    private Actor needOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Opdrachtgever")
    public Actor getNeedOwner() {
        return needOwner;
    }
    
    public void setNeedOwner(final Actor needOwner) {
        this.needOwner = needOwner;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    

    // helpers
    
    
    public String toString() {
        return getNeedDescription() + " - " + getNeedOwner().title();
    }
    
}
