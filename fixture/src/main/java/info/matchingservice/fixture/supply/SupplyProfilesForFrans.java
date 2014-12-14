package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class SupplyProfilesForFrans extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        
        createSupplyProfile(
                "Aangeboden: profiel 1",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getMySupplies().first(),
                "Mijn getal 7",
                10,
                7,
                "frans",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
