package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfileElementsForRembrandt extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
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
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		persons.findPersons("Rijn").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
                "rembrandt",
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4351 FK",
        		10,
        		persons.findPersons("Rijn").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"rembrandt",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		persons.findPersons("Rijn").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"rembrandt", 
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Enorme doeken inspireren me bijzonder. Daar moet ik gewoon op schilderen. Lichtinval, kleurgebruik, het vastleggen van "
                + "fluwelen stoffen: niet kan me meer boeien dan dat. En van tijd tot tijd een cognacje met een havanna.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Rijn").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "rembrandt", 
                executionContext);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
