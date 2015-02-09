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
        
        
        createQualityTagsElement(
        		"kwaliteit oorlogszuchtig",
        		10,
        		persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().last(),
        		"frans",
        		executionContext
        		);
        
        
        createPassionTagsElement(
                "Passie steekwoorden", 
                10, 
                persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().last(), 
                "frans", 
                executionContext
                );
        
        
        createQualityTagsElement(
        		"kwaliteit nieuwsgierig",
        		10,
        		persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().first(),
        		"frans",
        		executionContext
        		);
        
        createBrancheTagsElement(
                "Branche steekwoorden", 
                10, 
                persons.findPersons("Hals").get(0).getCollectDemands().last().getCollectDemandProfiles().first(), 
                "frans", 
                executionContext
                );
        

    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}