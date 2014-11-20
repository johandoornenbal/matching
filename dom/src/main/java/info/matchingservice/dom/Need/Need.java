package info.matchingservice.dom.Need;

import java.util.SortedSet;
import java.util.TreeSet;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.Assessment;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public abstract class Need extends MatchingSecureMutableObject<Need> {

    public Need() {
        super("ownedBy, needDescription, weight");
    }

    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
  
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

    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
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
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

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
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder tafel")
    public Actor DeleteNeed(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Tafel verwijderd");
        return getNeedOwner();
    }
    
    public String validateDeleteNeed(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> Asessments
    
    private SortedSet<Assessment> needAssessments = new TreeSet<Assessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<Assessment> getNeedAssessments() {
        return needAssessments;
    }
    
    public void setNeedAssessments(final SortedSet<Assessment> assessment){
        this.needAssessments = assessment;
    }

    // Helpers
    
    
    public String toString() {
        return getNeedDescription() + " - " + getNeedOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
