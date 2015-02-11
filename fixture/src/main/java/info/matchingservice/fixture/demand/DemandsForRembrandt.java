package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandsForRembrandt extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        
        createDemand(
                "Hulp gezocht voor De Nachtwacht",
                "Samengevat: blabla",
                "Het hele verhaal: blablabla",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
