package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfilesForMichiel extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	executeChild(new TestDemands(), executionContext);
        
        createDemandProfile(
                "Gezocht: wie maar wil",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Ruyter").get(0).getCollectDemands().first(),
                "michiel",
                executionContext
                );
        
        createDemandProfile(
                "Commandant",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Ruyter").get(0).getCollectDemands().first(),
                "michiel",
                executionContext
                );
        
        createDemandProfile(
                "Voetvolk",
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
