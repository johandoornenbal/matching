package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class SuppliesForRembrandt extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        
        createSupply(
                "Aanbod 7",
                10,
                DemandSupplyType.PERSONS_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
        
        createSupply(
                "Aanbod 8",
                10,
                DemandSupplyType.GENERIC_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
        
        createSupply(
                "Aanbod 9",
                10,
                DemandSupplyType.GENERIC_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
