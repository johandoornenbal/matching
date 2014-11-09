package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Persons;

import javax.inject.Inject;

public class ProfileForRembrandt extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Rembrandt",
                persons.allPersons().get(1),
                "rembrant",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
