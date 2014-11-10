package info.matchingservice.fixture.need;

import info.matchingservice.dom.Party.Persons;

import javax.inject.Inject;

public class NeedForFrans extends NeedAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createNeed(
                "Een behoefte van Frans Hals: Ik zoek hulpschilders",
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
        createNeed(
                "Nog behoefte van Frans Hals: Ik zoek schoonmakers",
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Persons persons;
}
