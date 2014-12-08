package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandProfileDropDownsForFrans extends DemandProfileDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        createDropDownElement(
                "Out of the box",
                10,
                dropDownForProfileElements.findDropDowns("box").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Hals*").get(0).getMyDemands().first().getDemandProfiles().first(),
                "frans",
                executionContext
                );
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
