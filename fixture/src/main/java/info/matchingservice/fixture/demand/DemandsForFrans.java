package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandsForFrans extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        
        createDemand(
                "Vraagstuk 1",
                10,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        createDemand(
                "Vraagstuk 2",
                10,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
