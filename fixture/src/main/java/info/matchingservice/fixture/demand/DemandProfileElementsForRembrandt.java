package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.fixture.actor.PersonForRembrandt;

import javax.inject.Inject;

public class DemandProfileElementsForRembrandt extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForRembrandt(), executionContext);
        executeChild(new DemandsForRembrandt(), executionContext);
        
//        createDropDownElement(
//                "kwaliteit geduldig",
//                10,
//                dropDownForProfileElements.findDropDowns("geduldig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Rijn*").get(0).getMyDemands().last().getDemandProfiles().first(),
//                "rembrandt",
//                executionContext
//                );
//        
//        createDropDownElement(
//                "kwaliteit oorlogszuchtig",
//                10,
//                dropDownForProfileElements.findDropDowns("oorlogszuchtig").get(0),
//                ProfileElementType.QUALITY,
//                persons.findPersons("Rijn*").get(0).getMyDemands().last().getDemandProfiles().last(),
//                "rembrandt",
//                executionContext
//                );
        
        createQualityTagsElement(
        		"kwaliteit geduldig en oorlogszuchtig",
        		10,
        		persons.findPersons("Rijn*").get(0).getMyDemands().last().getDemandProfiles().last(),
        		"rembrandt",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    
}
