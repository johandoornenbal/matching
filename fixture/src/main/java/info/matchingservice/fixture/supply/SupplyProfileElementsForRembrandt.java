package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class SupplyProfileElementsForRembrandt extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new SuppliesForRembrandt(), executionContext);
//        
//        createDropDownElement(
//                "Kwaliteit leidinggevend",
//                10,
//                dropDownForProfileElements.findDropDowns("leidinggevend").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Rijn").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "rembrandt",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "Kwaliteit nieuwsgierig",
//                10,
//                dropDownForProfileElements.findDropDowns("nieuwsgierig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Rijn").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "rembrandt",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Rijn").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
                "rembrandt",
        		executionContext
        		);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
