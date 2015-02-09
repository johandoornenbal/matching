package info.matchingservice.assessment;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.Assessment.Assessments;
import info.matchingservice.dom.DemandSupply.Demand;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class AssessmentAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Assessment createAssessment (
            Demand targetObject,
            Actor ownerActor,
            String description,
            String feedback,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Assessment newAssessment = assessments.createDemandAssessment(targetObject, ownerActor, description, feedback, ownedBy);
        
        return executionContext.add(this, newAssessment);
    }

    //region > injected services
    
    @javax.inject.Inject
    private Assessments assessments;
    

}