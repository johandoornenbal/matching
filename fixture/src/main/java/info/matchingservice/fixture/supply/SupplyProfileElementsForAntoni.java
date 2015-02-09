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
   
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Leeuw").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
        		"antoni",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
