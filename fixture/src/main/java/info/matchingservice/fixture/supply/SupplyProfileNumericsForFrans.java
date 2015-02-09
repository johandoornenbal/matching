package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class SupplyProfileNumericsForFrans extends SupplyProfileNumericAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        
//        createNumericElement(
//                "Prijs (credits)",
//                10,
//                100,
//                ProfileElementType.NUMERIC,
//                persons.findPersons("Hals").get(0).getSuppliesOfActor().first().getSupplyProfiles().first(),
//                "frans",
//                executionContext
//                );
    }
    
    @Inject
    private Persons persons;
    
    
}
