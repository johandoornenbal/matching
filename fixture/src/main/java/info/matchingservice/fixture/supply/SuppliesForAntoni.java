package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForAntoni;

import javax.inject.Inject;

public class SuppliesForAntoni extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForAntoni(), executionContext);
        
        createSupply(
                "Aanbod 1",
                10,
                DemandSupplyType.GENERIC_DEMANDSUPPLY,
                persons.findPersons("Leeuw*").get(0),
                "antoni",
                executionContext
                );
        
        createSupply(
                "Aanbod 2",
                10,
                DemandSupplyType.GENERIC_DEMANDSUPPLY,
                persons.findPersons("Leeuw*").get(0),
                "antoni",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
