package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class SuppliesForFrans extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        
        createSupply(
                "Aanbod 5",
                10,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        createSupply(
                "Aanbod 6",
                10,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
