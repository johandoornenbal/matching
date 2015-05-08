package info.matchingservice.fixture.assessment;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.fixture.demand.TestDemands;

public class AssessmentsForRembrandtFixture extends AssessmentAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
      //preqs
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemands(), executionContext);

        Demand demandOfFrans;
        Demand demandOfMichiel;
        demandOfFrans=persons.findPersons("Hals").get(0).getCollectDemands().first();
        demandOfMichiel=persons.findPersons("Ruyter").get(0).getCollectDemands().first();
        
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
