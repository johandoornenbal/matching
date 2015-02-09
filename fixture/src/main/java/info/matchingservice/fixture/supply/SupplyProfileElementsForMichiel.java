package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class SupplyProfileElementsForMichiel extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
        
//        createDropDownElement(
//                "Kwaliteit doortastend",
//                10,
//                dropDownForProfileElements.findDropDowns("doortastend").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "michiel",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "Kwaliteit oorlogszuchtig",
//                10,
//                dropDownForProfileElements.findDropDowns("oorlogszuchtig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "michiel",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "Kwaliteit leidinggevend",
//                10,
//                dropDownForProfileElements.findDropDowns("leidinggevend").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
//                "michiel",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"Mijn kwaliteiten",
        		10,
        		persons.findPersons("Ruyter").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
                "michiel",
        		executionContext
        		);
        
        createPassion(
                "Passie van Michiel", 
                10, 
                "Ik ben gek op zeilschepen, wapens in het algemeen en kanonnen in het bijzonder. Strategie ontwikkeling staat centraal in mijn leven.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Ruyter").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "michiel", 
                executionContext);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
