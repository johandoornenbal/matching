package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForGerard;

import javax.inject.Inject;

public class SuppliesForGerard extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForGerard(), executionContext);
        
        createSupply(
                "Persoonlijke profiel van Gerard Dou",
                10,
                DemandSupplyType.PERSONS_DEMANDSUPPLY,
                persons.findPersons("Dou").get(0),
                "gerard",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
