package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForAntoni;

import javax.inject.Inject;

public class SupplyProfileElementsForAntoni extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForAntoni(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        
//        createDropDownElement(
//                "Kwaliteit 1",
//                10,
//                dropDownForProfileElements.findDropDowns("box").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Leeuw*").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "antoni",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Leeuw*").get(0).getMySupplies().last().getSupplyProfiles().first(),
        		"antoni",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
