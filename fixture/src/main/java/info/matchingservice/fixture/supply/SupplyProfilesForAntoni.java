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
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().last(),
                "antoni",
                executionContext
                );
        
        createSupplyProfile(
                "Wetenschap in de praktijk",
                10,
                ProfileType.COURSE_PROFILE,
                persons.findPersons("Leeuw*").get(0).getMySupplies().first(),
                "antoni",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
