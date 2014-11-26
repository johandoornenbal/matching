package info.matchingservice.dom.Supply;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.SupplyAssessment;

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
public class Supply extends MatchingSecureMutableObject<Supply> {

    public Supply() {
        super("ownedBy, supplyDescription");
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
    
    private Actor supplyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Actor getSupplyOwner() {
        return supplyOwner;
    }
    
    public void setSupplyOwner(final Actor supplyOwner) {
        this.supplyOwner = supplyOwner;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String supplyDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    public String getSupplyDescription(){
        return supplyDescription;
    }
    
    public void setSupplyDescription(final String description) {
        this.supplyDescription = description;
    }

    //delete action /////////////////////////////////////////////////////////////////////////////////////

    public Actor DeleteSupply(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Supply deleted");
        return getSupplyOwner();
    }
    
    public String validateDeleteSupply(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> aanbod (supply profiles)
    
    private SortedSet<SupplyProfile> supplyProfiles = new TreeSet<SupplyProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "supplyProfileOwner", dependentElement = "true")
    public SortedSet<SupplyProfile> getSupplyProfiles() {
        return supplyProfiles;
    }
    
    public void setSupplyProfiles(final SortedSet<SupplyProfile> supplyProfile){
        this.supplyProfiles = supplyProfile;
    }
    
    public SupplyProfile newSupplyProfile(
            @Named("Omschrijving van supplyProfile")
            final  String supplyProfileDescription
            ) {
        return newSupplyProfile(supplyProfileDescription, this, currentUserName());
    }
    
    
    @Programmatic
    public SupplyProfile newSupplyProfile(
            final String supplyProfileDescription,
            final Supply supplyProfileOwner, 
            final String ownedBy) {
        return allSupplyProfiles.newSupplyProfile(supplyProfileDescription, supplyProfileOwner, ownedBy);
    }
    
    // Region> Assessments
    
    private SortedSet<SupplyAssessment> assessments = new TreeSet<SupplyAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<SupplyAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<SupplyAssessment> assessment) {
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
        return getSupplyDescription() + " - " + getSupplyOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    SupplyProfiles allSupplyProfiles;
    
}
