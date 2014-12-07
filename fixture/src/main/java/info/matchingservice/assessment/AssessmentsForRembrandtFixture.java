package info.matchingservice.assessment;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.demand.DemandsForMichiel;

import javax.inject.Inject;

public class AssessmentsForRembrandtFixture extends AssessmentAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
      //preqs
        executeChild(new DemandsForFrans(), executionContext);
        executeChild(new DemandsForMichiel(), executionContext);

        Demand demandOfFrans;
        Demand demandOfMichiel;
        demandOfFrans=persons.findPersons("Hals").get(0).getMyDemands().first();
        demandOfMichiel=persons.findPersons("Ruyter").get(0).getMyDemands().first();
        
        createAssessment(
                demandOfFrans,
                persons.findPersons("Rijn").get(0),
                "Goed bezig",
                "Je draait zeker lekker Frans. Groeten van Rembrandt.",
                "rembrandt",
                executionContext);
        
        createAssessment(
                demandOfMichiel,
                persons.findPersons("Rijn").get(0),
                "Moet dat nou?",
                "Waarom nou weer een oorlog? Ga eens met pensioen man! Groeten van Rembrandt.",
                "rembrandt",
                executionContext);
    }

    @Inject
    private Persons persons;
}
