package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

public class DemandProfileElementsForMichiel extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
                
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createPassionTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createLocation(
        		"4321 GD",
        		5,
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext       		
        		);
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createPassionTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createPassionTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("zeebenen", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("infanterist", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
    }
        

    @Inject
    Profiles profiles;
    
    
    
}
