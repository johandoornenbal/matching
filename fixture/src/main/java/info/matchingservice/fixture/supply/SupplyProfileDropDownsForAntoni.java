package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.fixture.actor.PersonForAntoni;

import javax.inject.Inject;

public class SupplyProfileDropDownsForAntoni extends SupplyProfileDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForAntoni(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        
        createDropDownElement(
                "Kwaliteit 1",
                10,
                dropDownForProfileElements.findDropDowns("box").get(0),
                ProfileElementCategory.QUALITY,
                persons.findPersons("Leeuw*").get(0).getMySupplies().first().getSupplyProfiles().first(),
                "antoni",
                executionContext
                );
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
