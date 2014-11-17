package info.matchingservice.fixture.need;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;


public class NeedForFrans extends NeedAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        Person frans = persons.allPersons().get(0);
        createNeed(
                frans,
                "Gezocht: compagnon schilders (M/V)",
                3,
                "Goed schilder maatje",
                6,
                "leeftijd",
                30,
                10,
                "frans",
                executionContext
                );
    }
    
    @Inject
    Persons persons;
}
