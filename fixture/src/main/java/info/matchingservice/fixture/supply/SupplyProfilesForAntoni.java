package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
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
                "Aangeboden: profiel 1",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().first(),
                "Mijn getal 9",
                10,
                9,
                "antoni",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
