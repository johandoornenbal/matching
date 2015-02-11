package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfileElementsForGerard extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
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
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
                "gerard",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"gerard", 
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4987 VW",
        		10,
        		persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"gerard",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben natuurlijk bezeten van schilderen. Het witte doek bekladden: ik doe niets liever dan dat. "
                + "Kleurgebruik en lichtinval ben ik helemaal gek van. De geur van olieverf en het gevoel van een verse"
                + "penseel in de hand doet me opstijgen. Mijn passie voor het portret in de gouden eeuw gaat me"
                + " tot grote hoogten brengen.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Dou").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "gerard", 
                executionContext);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
