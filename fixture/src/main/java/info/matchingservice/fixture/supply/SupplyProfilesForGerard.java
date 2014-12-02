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
                "Aangeboden: profiel 1",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Dou").get(0).getMySupplies().first(),
                "Mijn getal 10",
                10,
                10,
                "gerard",
                executionContext
                );
        
        createSupplyProfile(
                "Aangeboden: profiel 2",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Dou").get(0).getMySupplies().first(),
                "Mijn getal 2",
                10,
                2,
                "gerard",
                executionContext
                );
        
        createSupplyProfile(
                "Aangeboden: Schilder",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Dou").get(0).getMySupplies().last(),
                "Getal 5",
                10,
                5,
                "gerard",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
