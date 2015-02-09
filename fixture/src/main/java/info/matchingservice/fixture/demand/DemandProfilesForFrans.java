package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandProfilesForFrans extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        createDemandProfile(
                "Gezocht: nieuwsgierige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getDemandsOfActor().last(),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: oorlogszuchtige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getDemandsOfActor().last(),
                "frans",
                executionContext
                );
        
//        createDemandProfile(
//                "Gezocht: cursus",
//                10,
//                ProfileType.COURSE_PROFILE,
//                persons.findPersons("Hals").get(0).getDemandsOfActor().first(),
//                "frans",
//                executionContext
//                );
    }

    @Inject
    private Persons persons;
}
