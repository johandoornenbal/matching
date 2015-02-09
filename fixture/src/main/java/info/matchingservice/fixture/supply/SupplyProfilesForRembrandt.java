package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class SupplyProfilesForRembrandt extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new SuppliesForRembrandt(), executionContext);
        
        createSupplyProfile(
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getSuppliesOfActor().first(),
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
