package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandsForMichiel extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        
        createDemand(
                "Een oorlog",
                10,
                DemandSupplyType.EVENT_DEMANDSUPPLY,
                persons.findPersons("Ruyter").get(0),
                "michiel",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
