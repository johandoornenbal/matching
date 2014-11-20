package info.matchingservice.dom.Assessment;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Need.Need;


@DomainService(menuOrder = "60", repositoryFor = Assessment.class)
@Named("Asessments")
public class Assessments extends MatchingDomainService<Assessment> {

    public Assessments() {
        super(Assessments.class, Assessment.class);
    }
    
    public List<Assessment> allAssessments(){
        return allInstances();
    }
    
    public Assessment newAssessment(
            final Need need
            ){
        final Assessment newAs = newTransientInstance(Assessment.class);
        newAs.setTarget(need);
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
