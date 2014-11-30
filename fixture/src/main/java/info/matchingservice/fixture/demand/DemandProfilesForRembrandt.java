package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandProfilesForRembrandt extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        createDemandProfile(
                "Gezocht profiel 1",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getMyDemands().first(),
                "Mijn getal 10",
                10,
                10,
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht profiel 2",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getMyDemands().first(),
                "Mijn getal 3",
                10,
                3,
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Hulpschilder",
                10,
                ProfileNature.MULTI_PROFILE,
                ProfileType.DEMAND_PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getMyDemands().last(),
                "Getal 5",
                10,
                5,
                "frans",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
