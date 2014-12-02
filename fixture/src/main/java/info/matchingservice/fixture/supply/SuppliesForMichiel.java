package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class SuppliesForMichiel extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        
        createSupply(
                "Aanbod 10",
                10,
                persons.findPersons("Ruyter").get(0),
                "frans",
                executionContext
                );
        
        createSupply(
                "Aanbod 11",
                10,
                persons.findPersons("Ruyter").get(0),
                "frans",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
