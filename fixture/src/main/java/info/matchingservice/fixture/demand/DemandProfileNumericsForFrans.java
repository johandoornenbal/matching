package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfileNumericsForFrans extends DemandProfileNumericAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        
        createNumericElement(
                "Prijs (credits)",
                10,
                195,
                ProfileElementType.NUMERIC,
                persons.findPersons("Hals").get(0).getCollectDemands().first().getCollectDemandProfiles().first(),
                "frans",
                executionContext
                );
    }
    
    @Inject
    private Persons persons;
    
    
}
