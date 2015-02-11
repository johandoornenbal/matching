package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.supply.SuppliesForAntoni;
import info.matchingservice.fixture.supply.SuppliesForFrans;
import info.matchingservice.fixture.supply.SupplyProfileElementsForAntoni;

import javax.inject.Inject;

public class TagsForAntoni extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new TestPersons(), executionContext);
        executeChild(new SuppliesForAntoni(), executionContext);
        executeChild(new SupplyProfileElementsForAntoni(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).getCollectProfileElements().last(), 
                "onderzoekend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).getCollectProfileElements().last(), 
                "nieuwsgierig", 
                executionContext
                );
        
        createBranchTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).getCollectProfileElements().first(), 
                "wetenschap", 
                executionContext
                );
        
        createBranchTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0).getCollectProfileElements().first(), 
                "onderzoek", 
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
