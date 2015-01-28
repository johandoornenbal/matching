package info.matchingservice.dom.Assessment;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;


@DomainService(repositoryFor = Assessment.class)
@Hidden
public class Assessments extends MatchingDomainService<Assessment> {

    public Assessments() {
        super(Assessments.class, Assessment.class);
    }
    
    public List<Assessment> allAssessments(){
        return allInstances();
    }
    
    @NotInServiceMenu
    public Assessment newDemandAssessment(
            @ParameterLayout(named="target")
            final Demand target,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
        final DemandFeedback newAs = newTransientInstance(DemandFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTarget(target);
        newAs.setTargetOwnerActor(target.getDemandOwner());
        newAs.setOwnerActor(persons.findPersonUnique(currentUserName()));
        newAs.setAssessmentDescription(assessmentDescription);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    //BUSINESS RULE
    //Geen feedback op eigen demand
    public boolean hideNewDemandAssessment(
            final Demand target,
            final String assessmentDescription,
            final String feedback            
            ){
        if (target.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    
    @Programmatic
    public Assessment newDemandAssessment(
            final Demand targetObject,
            final Actor ownerActor,
            final String description,
            final String feedback,
            final String ownedBy
            ){
        final DemandFeedback newAs = newTransientInstance(DemandFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTarget(targetObject);
        newAs.setTargetOwnerActor(targetObject.getDemandOwner());
        newAs.setOwnerActor(ownerActor);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        persist(newAs);
        return newAs;
    }
    
    @NotInServiceMenu
    public Assessment newSupplyAssessment(
            @ParameterLayout(named="target")
            final Supply target,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
        final SupplyFeedback newAs = newTransientInstance(SupplyFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTarget(target);
        newAs.setTargetOwnerActor(target.getSupplyOwner());
        newAs.setOwnerActor(persons.findPersonUnique(currentUserName()));
        newAs.setAssessmentDescription(assessmentDescription);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    //BUSINESS RULE
    //Geen feedback op eigen supply
    public boolean hideNewSupplyAssessment(
            final Supply targetObject,
            final String description,
            final String feedback            
            ){
        if (targetObject.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    
    @Programmatic
    public Assessment newSupplyAssessment(
            final Supply targetObject,
            final Actor ownerActor,
            final String description,
            final String feedback,
            final String ownedBy
            ){
        final SupplyFeedback newAs = newTransientInstance(SupplyFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTarget(targetObject);
        newAs.setTargetOwnerActor(targetObject.getSupplyOwner());
        newAs.setOwnerActor(ownerActor);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(ownedBy);
        persist(newAs);
        return newAs;
    }    
    
    @NotInServiceMenu
    public Assessment newProfileAssessment(
            @ParameterLayout(named="target")
            final Profile target,
            @ParameterLayout(named="assessmentDescription")
            final String assessmentDescription,
            @ParameterLayout(named="feedback", multiLine=3)
            final String feedback
            ){
        final ProfileFeedback newAs = newTransientInstance(ProfileFeedback.class);
        final UUID uuid=UUID.randomUUID();
        newAs.setUniqueItemId(uuid);
        newAs.setTarget(target);
        if (target.getDemandOrSupply().equals(DemandOrSupply.SUPPLY)){
            newAs.setTargetOwnerActor(target.getSupplyProfileOwner().getSupplyOwner());
        } else {
            newAs.setTargetOwnerActor(target.getDemandProfileOwner().getDemandOwner());
        }
        newAs.setOwnerActor(persons.findPersonUnique(currentUserName()));
        newAs.setAssessmentDescription(assessmentDescription);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    //BUSINESS RULE
    //Geen feedback op eigen profile
    public boolean hideNewProfileAssessment(
            final Profile targetObject,
            final String description,
            final String feedback            
            ){
        if (targetObject.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        return false;
    }
    

    
    
    //Region> Helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private Persons persons;

}
