package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForAntoni;

import javax.inject.Inject;

public class SupplyProfilesForAntoni extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForAntoni(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        
        createSupplyProfile(
                "Aangeboden profiel 1",
                ProfileNature.MULTI_PROFILE,
                ProfileType.SUPPLY_PERSON_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().first(),
                "Mijn getal 6",
                10,
                6,
                "antoni",
                executionContext
                );
        
        createSupplyProfile(
                "Aangeboden profiel 2",
                ProfileNature.MULTI_PROFILE,
                ProfileType.SUPPLY_PERSON_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().first(),
                "Mijn getal 4",
                10,
                4,
                "antoni",
                executionContext
                );
        
        createSupplyProfile(
                "wetenschapper",
                ProfileNature.MULTI_PROFILE,
                ProfileType.SUPPLY_PERSON_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().last(),
                "Getal 13",
                10,
                13,
                "antoni",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
