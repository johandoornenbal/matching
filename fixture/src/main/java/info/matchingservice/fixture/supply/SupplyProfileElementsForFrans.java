package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class SupplyProfileElementsForFrans extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        
//        createDropDownElement(
//                "Kwaliteit ijverig",
//                10,
//                dropDownForProfileElements.findDropDowns("ijverig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Hals").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "frans",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "Kwaliteit out of the box",
//                10,
//                dropDownForProfileElements.findDropDowns("box").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Hals").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "frans",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
        		"frans",
        		executionContext
        		);
        
        createPassion(
                "Passie van Frans", 
                10, 
                "Ik ben gek op schilderen en schilder het liefst vrouwen in de bloei van hun leven. Kleurgebruik en lichtinval ben ik helemaal gek van.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Hals").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "frans", 
                executionContext);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
