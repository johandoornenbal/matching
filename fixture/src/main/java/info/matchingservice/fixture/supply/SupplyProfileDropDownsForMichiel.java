package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForMichiel;

import javax.inject.Inject;

public class SupplyProfileDropDownsForMichiel extends SupplyProfileDropDownAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
        
        createDropDownElement(
                "Kwaliteit doortastend",
                10,
                dropDownForProfileElements.findDropDowns("doortastend").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
                "michiel",
                executionContext
                );
        
        createDropDownElement(
                "Kwaliteit oorlogszuchtig",
                10,
                dropDownForProfileElements.findDropDowns("oorlogszuchtig").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
                "michiel",
                executionContext
                );
        
        createDropDownElement(
                "Kwaliteit leidinggevend",
                10,
                dropDownForProfileElements.findDropDowns("leidinggevend").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Ruyter").get(0).getMySupplies().last().getSupplyProfiles().first(),
                "michiel",
                executionContext
                );
    }
    
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
