package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForGerard;

import javax.inject.Inject;

public class SupplyProfilesForGerard extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForGerard(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        
        createSupplyProfile(
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Dou").get(0).getSuppliesOfActor().first(),
                "gerard",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
