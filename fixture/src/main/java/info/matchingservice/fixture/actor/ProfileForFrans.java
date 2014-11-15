package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;

public class ProfileForFrans extends ProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createProfile(
                "Profile of Frans Hals",
//                "Jonge man met ruime penseelvoorraad",
//                5,
                persons.allPersons().get(0),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Persons persons;

}
