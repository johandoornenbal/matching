package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.PersonForMichiel;
import info.matchingservice.fixture.demand.DemandProfileElementsForMichiel;
import info.matchingservice.fixture.demand.DemandsForMichiel;
import info.matchingservice.fixture.supply.SuppliesForMichiel;

import javax.inject.Inject;

public class TagsForMichiel extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForMichiel(), executionContext);
        executeChild(new DemandsForMichiel(), executionContext);
        executeChild(new SuppliesForMichiel(), executionContext);
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
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(), 
                "doortastend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(), 
                "leidinggevend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0).getCollectProfileElements().first(), 
                "oorlogszuchtig", 
                executionContext
                );
    }

    
    @Inject
    Profiles profiles;
}
