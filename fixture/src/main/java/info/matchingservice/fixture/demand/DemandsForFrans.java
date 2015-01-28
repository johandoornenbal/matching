package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandsForFrans extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        
        createDemand(
                "Mensen gezocht voor project",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        createDemand(
                "Cursus gezocht",
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
