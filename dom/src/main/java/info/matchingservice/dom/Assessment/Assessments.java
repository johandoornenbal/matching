package info.matchingservice.dom.Assessment;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.VacancyProfile;
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
    public Assessment newAssessment(
            final Need targetObject,
            @Named("Titel - korte omschrijving")
            final String description,
            @Named("Feedback")
            @MultiLine
            final String feedback
            ){
        final NeedFeedback newAs = newTransientInstance(NeedFeedback.class);
        newAs.setTarget(targetObject);
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    @Named("Geef feedback")
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
        newAs.setAssessmentDescription(description);
        newAs.setFeedback(feedback);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    @Named("Geef feedback")
    public VacancyProfileFeedback newAssessment(
            final VacancyProfile targetObject,
            @Named("Titel - korte omschrijving")
            final String description,
            @Named("Feedback")
            @MultiLine
            final String feedback
            ){
        final VacancyProfileFeedback newAs = newTransientInstance(VacancyProfileFeedback.class);
        newAs.setTarget(targetObject);
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

}
