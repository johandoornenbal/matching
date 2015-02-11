package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfileElementsForFrans extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        
        
        createQualityTagsElement(
        		"kwaliteit oorlogszuchtig",
        		10,
        		persons.findPersons("Hals").get(0).getCollectDemands().first().getCollectDemandProfiles().last(),
        		"frans",
        		executionContext
        		);
        
        
        createPassionTagsElement(
                "Passie steekwoorden", 
                10, 
                persons.findPersons("Hals").get(0).getCollectDemands().first().getCollectDemandProfiles().last(), 
                "frans", 
                executionContext
                );
        
        
        createQualityTagsElement(
        		"kwaliteit onderzoekend",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createQualityTagsElement(
        		"empathische kwaliteiten",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
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
    private Persons persons;
    
    @Inject Profiles profiles;
    
    
}
