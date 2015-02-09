package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class DemandProfilesForRembrandt extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);
        
        createDemandProfile(
                "Gezocht: soldaat die wil poseren",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getCollectDemands().first(),
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: Figurant voor de Nachtwacht",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getCollectDemands().last(),
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
