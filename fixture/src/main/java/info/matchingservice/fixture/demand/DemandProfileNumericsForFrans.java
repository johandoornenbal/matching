package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandProfileNumericsForFrans extends DemandProfileNumericAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        createNumericElement(
                "Prijs (credits)",
                10,
                195,
                ProfileElementType.NUMERIC,
                persons.findPersons("Hals").get(0).getMyDemands().first().getDemandProfiles().first(),
                "frans",
                executionContext
                );
    }
    
    @Inject
    private Persons persons;
    
    
}
