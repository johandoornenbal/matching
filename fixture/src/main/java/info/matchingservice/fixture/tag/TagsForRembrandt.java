package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForRembrandt;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

public class TagsForRembrandt extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);
        executeChild(new DemandProfileElementsForRembrandt(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        //** demand 1 **//

        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "leger", 
                executionContext
                );
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "schilderkunst", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "sterk", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "stoer", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("poseren", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "gespierd", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("figurant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "nieuwsgierig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("figurant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "stil", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("model1", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "slank", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("model2", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "dik", 
                executionContext
                );
        
        
        //** supply **//
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "gevoelig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "precies", 
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "schilderkunst", 
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "schilderen", 
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
