package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class SupplyProfileElementsForAntoni extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);;
   
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		persons.findPersons("Leeuw").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(),
        		"antoni",
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4321 ZX",
        		10,
        		persons.findPersons("Leeuw").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"antoni",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		persons.findPersons("Leeuw").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
        		"antoni", 
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Mijn grote passie is de wetenschap en microscopen in het bijzonder. "
                + "Ik bestudeer het liefst de hele dag micro organismen zoals bacterien. "
                + "Ook virussen en ziektekiemen onderzoek behoort tot mijn veld van interesse.", 
                ProfileElementType.PASSION, 
                persons.findPersons("Leeuw").get(0).getCollectSupplies().last().getCollectSupplyProfiles().first(), 
                "antoni", 
                executionContext);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
