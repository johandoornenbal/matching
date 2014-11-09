package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Persons;

import javax.inject.Inject;

public class ProfileForFrans extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Frans Hals",
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
