package info.matchingservice.dom.Assessment;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Demand.Demand;
import info.matchingservice.dom.Profile.Profile;


@DomainService(menuOrder = "60", repositoryFor = Assessment.class)
@Named("Assessments")
public class Assessments extends MatchingDomainService<Assessment> {

    public Assessments() {
        super(Assessments.class, Assessment.class);
    }
    
    public List<Assessment> allAssessments(){
        return allInstances();
    }
    
    @Named("Geef feedback")
    @NotInServiceMenu
    public Assessment newAssessment(
            final Demand targetObject,
            @Named("Titel - korte omschrijving")
            final String description,
            @Named("Feedback")
            @MultiLine
            final String feedback
            ){
        final DemandFeedback newAs = newTransientInstance(DemandFeedback.class);
        newAs.setTarget(targetObject);
        newAs.setOwnerActor(persons.findPersonUnique(currentUserName()));
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    @Named("Geef feedback")
    @NotInServiceMenu
    public Assessment newAssessment(
            final Profile targetObject,
            @Named("Titel - korte omschrijving")
            final String description,
            @Named("Feedback")
            @MultiLine
            final String feedback
            ){
        final ProfileFeedback newAs = newTransientInstance(ProfileFeedback.class);
        newAs.setTarget(targetObject);
        newAs.setOwnerActor(persons.findPersonUnique(currentUserName()));
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
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
