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
                "Persoonlijk profiel van Antoni",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Leeuw").get(0),
                "antoni",
                executionContext
                );
        
//        createSupply(
//                "Cursusaanbod van Antoni",
//                10,
//                DemandSupplyType.COURSE_DEMANDSUPPLY,
//                persons.findPersons("Leeuw").get(0),
//                "antoni",
//                executionContext
//                );
    }
    

    @Inject
    private Persons persons;
}
