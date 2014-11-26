package info.matchingservice.dom.Demand;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.DemandAssessment;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
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
import org.apache.isis.applib.annotation.Programmatic;
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
public class Demand extends MatchingSecureMutableObject<Demand> {

    public Demand() {
        super("ownedBy, demandDescription, weight");
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
    
    private Actor demandOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Actor getDemandOwner() {
        return demandOwner;
    }
    
    public void setDemandOwner(final Actor needOwner) {
        this.demandOwner = needOwner;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String demandDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Opdracht omschrijving op tafel")
    public String getDemandDescription(){
        return demandDescription;
    }
    
    public void setDemandDescription(final String description) {
        this.demandDescription = description;
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
    
    public Actor DeleteDemand(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Demand deleted");
        return getDemandOwner();
    }
    
    public String validateDeleteDemand(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> Vacancies
    
    private SortedSet<DemandProfile> demandProfiles = new TreeSet<DemandProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "demandProfileOwner", dependentElement = "true")
    public SortedSet<DemandProfile> getDemandProfiles() {
        return demandProfiles;
    }
    
    public void setDemandProfiles(final SortedSet<DemandProfile> vac){
        this.demandProfiles = vac;
    }
    
    public DemandProfile newDemandProfile(
            final  String demandProfileDescription,
            final Integer weight 
            ) {
        return newDemandProfile(demandProfileDescription, weight, this, currentUserName());
    }
    
    
    @Programmatic
    public DemandProfile newDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final Demand demandProfileOwner, 
            final String ownedBy) {
        return allDemandProfiles.newDemand(demandProfileDescription, weight, demandProfileOwner, ownedBy);
    }
    
    // Region> Assessments
    
    private SortedSet<DemandAssessment> assessments = new TreeSet<DemandAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<DemandAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<DemandAssessment> assessment) {
        this.assessments = assessment;
    }
    
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  

    // Helpers
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getDemandDescription() + " - " + getDemandOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    DemandProfiles allDemandProfiles;
    
}
