package info.matchingservice.dom.Assessment;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
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
    
    public Assessment newAssessment(
            final Need targetObject
            ){
        final NeedAssessment newAs = newTransientInstance(NeedAssessment.class);
        newAs.setTarget(targetObject);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    public Assessment newAssessment(
            final Profile targetObject
            ){
        final ProfileAssessment newAs = newTransientInstance(ProfileAssessment.class);
        newAs.setTarget(targetObject);
        newAs.setOwnedBy(currentUserName());
        persist(newAs);
        return newAs;
    }
    
    public VacancyProfileAssessment newAssessment(
            final VacancyProfile targetObject
            ){
        final VacancyProfileAssessment newAs = newTransientInstance(VacancyProfileAssessment.class);
        newAs.setTarget(targetObject);
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
