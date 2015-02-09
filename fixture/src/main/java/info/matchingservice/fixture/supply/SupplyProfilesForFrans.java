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
                "Mijn persoonlijke profiel",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getSuppliesOfActor().last(),
                "frans",
                executionContext
                );
        
//        createSupplyProfile(
//                "Prince2 cursus",
//                10,
//                ProfileType.COURSE_PROFILE,
//                persons.findPersons("Hals").get(0).getSuppliesOfActor().first(),
//                "frans",
//                executionContext
//                );
    }

    @Inject
    private Persons persons;
}
