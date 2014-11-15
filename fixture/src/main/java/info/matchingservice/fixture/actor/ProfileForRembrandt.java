package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

public class ProfileForRembrandt extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Rembrandt",
//                "Ervaren senior allround schilder",
//                7,
                persons.allPersons().get(1),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
