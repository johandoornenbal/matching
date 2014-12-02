package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForGerard;

import javax.inject.Inject;

public class SuppliesForGerard extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForGerard(), executionContext);
        
        createSupply(
                "Aanbod 3",
                10,
                persons.findPersons("Dou").get(0),
                "gerard",
                executionContext
                );
        
        createSupply(
                "Aanbod 4",
                10,
                persons.findPersons("Dou").get(0),
                "gerard",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}