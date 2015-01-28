package info.matchingservice.dom.Assessment;

import java.util.UUID;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(editing=Editing.DISABLED)
public class Assessment extends MatchingSecureMutableObject<Assessment> {

    public Assessment() {
        super("uniqueItemId, ownedBy, assessmentDescription");
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
    
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    // Immutables //////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Should be overridden for use on specific Object Type
     */
    private Object target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(named="Assessment op")
    public Object getTarget() {
        return target;
    }
    
    public void setTarget(final Object object) {
        this.target = object;
    }
    
    private Actor targetOwnerActor;
    
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(named="Gericht aan")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Actor getTargetOwnerActor(){
        return targetOwnerActor;
    }
    
    public void setTargetOwnerActor(final Actor owner) {
        this.targetOwnerActor = owner;
    }
    
    private Actor ownerActor;
    
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(named="Afzender")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Actor getOwnerActor(){
        return ownerActor;
    }
    
    public void setOwnerActor(final Actor owner) {
        this.ownerActor = owner;
    }
    
    //description /////////////////////////////////////////////////////////////////////////////////////
    
    private String assessmentDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(named="Titel - korte omschrijving")
    public String getAssessmentDescription() {
        return assessmentDescription;
    }
    
    public void setAssessmentDescription(final String assessmentDescription){
        this.assessmentDescription = assessmentDescription;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @ActionLayout(named="Verwijder assessment")
    public void DeleteAssessment(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Assessment verwijderd");
    }
    
    public String validateDeleteAssessment(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //helpers ////////////////
    
    public String title() {
        return getTarget().toString() + " - " + getAssessmentDescription();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}

