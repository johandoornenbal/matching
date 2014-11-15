package info.matchingservice.fixture.need;

import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

public class NeedForRembrandt extends NeedAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createNeed(
                "Opdracht van Rembrandt: Ik zoek figuranten voor de Nachtwacht",
                persons.allPersons().get(1),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Persons persons;
}
