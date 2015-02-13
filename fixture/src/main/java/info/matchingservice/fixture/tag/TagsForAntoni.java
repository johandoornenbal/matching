package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

public class TagsForAntoni extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),  
                "onderzoekend", 
                "antoni",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),  
                "nieuwsgierig",
                "antoni",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),  
                "nauwkeurig", 
                "antoni",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0),  
                "wetenschap",
                "antoni",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "onderzoek", 
                "antoni",
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
