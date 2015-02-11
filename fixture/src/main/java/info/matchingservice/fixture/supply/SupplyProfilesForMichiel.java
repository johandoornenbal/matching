package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfilesForMichiel extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
        
        createSupplyProfile(
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Ruyter").get(0).getCollectSupplies().first(),
                "michiel",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
