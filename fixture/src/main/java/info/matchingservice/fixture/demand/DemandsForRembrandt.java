package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class DemandsForRembrandt extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        
        createDemand(
                "Vraagstuk 1",
                10,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
        
        createDemand(
                "Vraagstuk 2",
                10,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
