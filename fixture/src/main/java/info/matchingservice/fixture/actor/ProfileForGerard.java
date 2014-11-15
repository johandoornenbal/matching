package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

public class ProfileForGerard extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Gerard Dou",
                "Ervaren senior schilder",
                3,
                persons.allPersons().get(2),
                "gerard",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
