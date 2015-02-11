package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

public class TagsForMichiel extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);
        executeChild(new DemandProfileElementsForMichiel(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        //** demand **//
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),  
                "dapper", 
                executionContext
                );
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "out of the box", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "bloeddorstig", 
                executionContext
                );
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("huurling", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0),
                "oorlog", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "out of the box", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "strategisch", 
                executionContext
                );
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0),
                "spectakel", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("infanterist", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "doorzetter", 
                executionContext
                );
        
        
        
        
        //** supply **//
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "doortastend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "leidinggevend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "oorlogszuchtig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "sterk", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0),
                "gespierd", 
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0),
                "leger", 
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0),
                "politiek", 
                executionContext
                );
    }

    
    @Inject
    Profiles profiles;
}
