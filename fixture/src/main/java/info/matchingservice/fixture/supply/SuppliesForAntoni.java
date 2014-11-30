package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForAntoni;

import javax.inject.Inject;

public class SuppliesForAntoni extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForAntoni(), executionContext);
        
        createSupply(
                "Aanbod 1",
                persons.findPersons("Leeuw*").get(0),
                "antoni",
                executionContext
                );
        
        createSupply(
                "Vraagstuk 2",
                persons.findPersons("Leeuw*").get(0),
                "antoni",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
