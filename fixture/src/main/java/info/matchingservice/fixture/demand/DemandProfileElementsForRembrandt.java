package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfileElementsForRembrandt extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);

        
        createQualityTagsElement(
        		"kwaliteit geduldig en oorlogszuchtig",
        		10,
        		persons.findPersons("Rijn").get(0).getCollectDemands().last().getCollectDemandProfiles().last(),
        		"rembrandt",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
