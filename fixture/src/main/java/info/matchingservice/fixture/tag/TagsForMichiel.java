package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;

import javax.inject.Inject;

public class TagsForMichiel extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	executeChild(new TestDemands(), executionContext);
    	executeChild(new TestSupplies(), executionContext);
        executeChild(new DemandProfileElementsForMichiel(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("wie maar wil", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(), 
                "dapper", 
                executionContext
                );
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("wie maar wil", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(), 
                "out of the box", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("wie maar wil", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(),
                "bloeddorstig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().last(), 
                "doortastend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().last(), 
                "leidinggevend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().last(), 
                "oorlogszuchtig", 
                executionContext
                );
    }

    
    @Inject
    Profiles profiles;
}
