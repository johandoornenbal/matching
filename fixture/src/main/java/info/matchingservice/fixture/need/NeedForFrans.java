package info.matchingservice.fixture.need;

import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

public class NeedForFrans extends NeedAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createNeed(
                "Gezocht: hulpschilders (M/V)",
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
        createNeed(
                "Nog een opdracht van Frans: Ik zoek schoonmakers",
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Persons persons;
}
