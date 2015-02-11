package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfilesForAntoni extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        
        createSupplyProfile(
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Leeuw").get(0).getCollectSupplies().last(),
                "antoni",
                executionContext
                );
        
//        createSupplyProfile(
//                "Wetenschap in de praktijk",
//                10,
//                ProfileType.COURSE_PROFILE,
//                persons.findPersons("Leeuw").get(0).getSuppliesOfActor().first(),
//                "antoni",
//                executionContext
//                );
    }

    @Inject
    private Persons persons;
}
