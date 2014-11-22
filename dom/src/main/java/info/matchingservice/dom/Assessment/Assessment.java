package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Person;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Immutable
public class Assessment extends MatchingSecureMutableObject<Assessment> {

    public Assessment() {
        super("ownedBy, assessmentDescription");
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

    // Immutables //////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Should be overridden for use on specific Object Type
     */
    private Object target;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("doel")
    public Object getTarget() {
        return target;
    }
    
    public void setTarget(final Object object) {
        this.target = object;
    }
    
    private Person ownerPerson;
    
    @Disabled
    @Named("Afzender")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getOwnerPerson(){
        return ownerPerson;
    }
    
    public void setOwnerPerson(final Person owner) {
        this.ownerPerson = owner;
    }
    
    //description /////////////////////////////////////////////////////////////////////////////////////
    
    private String assessmentDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Titel - korte omschrijving")
    public String getAssessmentDescription() {
        return assessmentDescription;
    }
    
    public void setAssessmentDescription(final String assessmentDescription){
        this.assessmentDescription = assessmentDescription;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder assessment")
    public void DeleteAssessment(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
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
