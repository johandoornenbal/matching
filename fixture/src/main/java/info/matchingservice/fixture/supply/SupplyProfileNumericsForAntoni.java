package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfileNumericsForAntoni extends SupplyProfileNumericAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        
//        createNumericElement(
//                "Prijs (credits)",
//                10,
//                200,
//                ProfileElementType.NUMERIC,
//                persons.findPersons("Leeuw").get(0).getSuppliesOfActor().first().getSupplyProfiles().first(),
//                "antoni",
//                executionContext
//                );
    }
    
    @Inject
    private Persons persons;
    
    
}
