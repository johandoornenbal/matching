package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class DemandProfilesForFrans extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);
        
        createDemandProfile(
                "Gezocht profiel 1",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getMyDemands().first(),
                "Mijn getal 7",
                10,
                7,
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht profiel 2",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getMyDemands().first(),
                "Mijn getal 5",
                10,
                5,
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Figurant voor de Nachtwacht",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Rijn").get(0).getMyDemands().last(),
                "Getal 13",
                10,
                13,
                "rembrandt",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
