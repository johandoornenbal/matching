package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class SuppliesForFrans extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        
        createSupply(
                "Persoonlijke profiel van Frans Hals",
                10,
                DemandSupplyType.PERSONS_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
       
        createSupply(
                "Cursusaanbod van Frans Hals",
                10,
                DemandSupplyType.COURSE_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                ); 
    }

    @Inject
    private Persons persons;
}
