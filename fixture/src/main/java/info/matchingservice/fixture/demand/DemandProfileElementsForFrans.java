package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForFrans;

import javax.inject.Inject;

public class DemandProfileElementsForFrans extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        createDropDownElement(
                "kwaliteit oorlogszuchtig",
                10,
                dropDownForProfileElements.findDropDowns("oorlogszuchtig").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Hals*").get(0).getMyDemands().last().getDemandProfiles().last(),
                "frans",
                executionContext
                );
        
        createDropDownElement(
                "kwaliteit nieuwsgierig",
                10,
                dropDownForProfileElements.findDropDowns("nieuwsgierig").get(0),
                ProfileElementType.QUALITY,
                persons.findPersons("Hals*").get(0).getMyDemands().last().getDemandProfiles().first(),
                "frans",
                executionContext
                );
        
        createPassionTagsElement(
                "Passie steekwoorden", 
                10, 
                persons.findPersons("Hals*").get(0).getMyDemands().last().getDemandProfiles().last(), 
                "frans", 
                executionContext
                );
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
