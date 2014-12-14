package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class SupplyProfileDropDownsForFrans extends SupplyProfileDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        
        createDropDownElement(
                "Kwaliteit ijverig",
                10,
                dropDownForProfileElements.findDropDowns("ijverig").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Hals").get(0).getMySupplies().first().getSupplyProfiles().first(),
                "frans",
                executionContext
                );
        
        createDropDownElement(
                "Kwaliteit out of the box",
                10,
                dropDownForProfileElements.findDropDowns("box").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Hals").get(0).getMySupplies().first().getSupplyProfiles().first(),
                "frans",
                executionContext
                );
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
