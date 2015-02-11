package info.matchingservice.assessment;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.fixture.demand.TestDemands;

import javax.inject.Inject;

public class AssessmentsForFransFixture extends AssessmentAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
      //preqs
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemands(), executionContext);

        Demand demandOfRembrandt;
        Demand demandOfMichiel;
        demandOfRembrandt=persons.findPersons("Rijn").get(0).getCollectDemands().first();
        demandOfMichiel=persons.findPersons("Ruyter").get(0).getCollectDemands().first();
        
        createAssessment(
                demandOfRembrandt,
                persons.findPersons("Hals").get(0),
                "Verklaar je nader",
                "Hoi van Rijn. Verklaar je nader... Wat ben je nu precies van plan? Gr. v Frans",
                "frans",
                executionContext);
        
        createAssessment(
                demandOfMichiel,
                persons.findPersons("Hals").get(0),
                "Alweer?",
                "Waarom alweer een oorlog? Ga eens met pensioen man! Groeten van Frans.",
                "frans",
                executionContext);
    }

    @Inject
    private Persons persons;
}
