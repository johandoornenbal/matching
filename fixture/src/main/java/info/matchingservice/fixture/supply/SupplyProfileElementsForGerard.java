package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForGerard;

import javax.inject.Inject;

public class SupplyProfileElementsForGerard extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForGerard(), executionContext);
        executeChild(new SuppliesForGerard(), executionContext);
        
//        createDropDownElement(
//                "Kwaliteit empatisch",
//                10,
//                dropDownForProfileElements.findDropDowns("empatisch").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "gerard",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "Kwaliteit nieuwsgierig",
//                10,
//                dropDownForProfileElements.findDropDowns("nieuwsgierig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Dou").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "gerard",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Dou").get(0).getSuppliesOfActor().last().getSupplyProfiles().first(),
                "gerard",
        		executionContext
        		);
        
        createBranche(
        		"Branche voor Gerard", 
        		10, 
        		persons.findPersons("Dou").get(0).getSuppliesOfActor().last().getSupplyProfiles().first(), 
        		"gerard", 
        		executionContext
        		);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
