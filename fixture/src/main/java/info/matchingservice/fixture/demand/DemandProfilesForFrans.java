package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfilesForFrans extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        
        createDemandProfile(
                "Gezocht: nieuwsgierige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getCollectDemands().first(),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: oorlogszuchtige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getCollectDemands().first(),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: meelevende persooonlijkheden",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getCollectDemands().first(),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: onderzoekend persoon",
                10,
                ProfileType.PERSON_PROFILE,
                persons.findPersons("Hals").get(0).getCollectDemands().last(),
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
