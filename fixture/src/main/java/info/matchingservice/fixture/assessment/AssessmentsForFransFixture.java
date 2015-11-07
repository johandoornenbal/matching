package info.matchingservice.fixture.assessment;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.fixture.demand.TestDemands;

public class AssessmentsForFransFixture extends AssessmentAbstract {


    @Override
    protected void execute(ExecutionContext executionContext) {
        
      //preqs
        executionContext.executeChild(this, new TestDemands());
        executionContext.executeChild(this, new TestDemands());

        Person ASSESSMENT_OWNER_ACTOR = persons.findPersons("Hals").get(0);
        Demand demandOfRembrandt;
        Demand demandOfMichiel;
        demandOfRembrandt=persons.findPersons("Rijn").get(0).getDemands().first();
        demandOfMichiel=persons.findPersons("Ruyter").get(0).getDemands().first();
        
        createAssessment(
                demandOfRembrandt,
                ASSESSMENT_OWNER_ACTOR,
                "Verklaar je nader",
                "Hoi van Rijn. Verklaar je nader... Wat ben je nu precies van plan? Gr. v Frans",
                "frans",
                executionContext);
        
        createAssessment(
                demandOfMichiel,
                ASSESSMENT_OWNER_ACTOR,
                "Alweer?",
                "Waarom alweer een oorlog? Ga eens met pensioen man! Groeten van Frans.",
                "frans",
                executionContext);
    }

    @Inject
    private Persons persons;
}
