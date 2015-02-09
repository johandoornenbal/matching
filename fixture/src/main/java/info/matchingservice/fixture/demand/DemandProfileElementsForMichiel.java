package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class DemandProfileElementsForMichiel extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new DemandsForMichiel(), executionContext);
                
        createQualityTagsElement(
        		"kwaliteit out of the box en dapper",
        		10,
        		persons.findPersons("Ruyter").get(0).getDemandsOfActor().last().getDemandProfiles().first(),
        		"michiel",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
