package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SupplyProfileElementsForFrans extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
        		"frans",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben gek op schilderen en schilder het liefst vrouwen in de bloei van hun leven. Kleurgebruik en lichtinval ben ik helemaal gek van.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "frans", 
                executionContext);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"1234AB",
        		10,
        		persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"frans",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"frans", 
        		executionContext
        		);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
