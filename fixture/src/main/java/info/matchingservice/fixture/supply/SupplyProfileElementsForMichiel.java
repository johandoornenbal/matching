package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfileElementsForMichiel extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	executeChild(new TestSupplies(), executionContext);
        
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
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		persons.findPersons("Ruyter").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
                "michiel",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben gek op zeilschepen, wapens in het algemeen en kanonnen in het bijzonder. "
                + "Strategie ontwikkeling staat centraal in mijn leven. De kunst van de oorlog "
                + "kruitdampen en strijdtonelen vullen mijn gedachten voortdurend.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Ruyter").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "michiel", 
                executionContext);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"5674 FK",
        		10,
        		persons.findPersons("Ruyter").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"michiel",
        		executionContext
        		);
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
