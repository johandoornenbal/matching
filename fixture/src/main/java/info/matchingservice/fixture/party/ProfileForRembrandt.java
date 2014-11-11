package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Persons;

import javax.inject.Inject;

public class ProfileForRembrandt extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Rembrandt",
                "Ervaren senior allround schilder",
                7,
                persons.allPersons().get(1),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
