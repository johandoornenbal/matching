package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class DemandProfileElementsForRembrandt extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	executeChild(new TestDemands(), executionContext);

        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("commandant die wil poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createBrancheTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("commandant die wil poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("figurant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("model1", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createLocation(
        		"1234 AB",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("model1", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("model2", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createLocation(
        		"1234 AB",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("model2", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
    }
        
    @Inject 
    Profiles profiles;
    
    
}
