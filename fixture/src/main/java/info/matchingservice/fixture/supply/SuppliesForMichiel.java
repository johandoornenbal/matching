package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class SuppliesForMichiel extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        
        createSupply(
                "Persoonlijk profiel van Michiel",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Ruyter").get(0),
                "michiel",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
