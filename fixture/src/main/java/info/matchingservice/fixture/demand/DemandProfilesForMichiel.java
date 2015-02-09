package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class DemandProfilesForMichiel extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new DemandsForMichiel(), executionContext);
        
        createDemandProfile(
                "Gezocht: wie maar wil",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Ruyter").get(0).getCollectDemands().first(),
                "michiel",
                executionContext
                );

    }

    @Inject
    private Persons persons;
}
