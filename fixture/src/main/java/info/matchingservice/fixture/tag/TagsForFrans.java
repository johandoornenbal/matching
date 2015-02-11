package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.supply.SuppliesForFrans;

import javax.inject.Inject;

public class TagsForFrans extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new DemandsForFrans(), executionContext);
        executeChild(new SuppliesForFrans(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "zeilschepen", 
                executionContext
                );
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "schilderen", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "oorlogszuchtig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "dapper", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "stoer", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "gespierd", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "nieuwsgierig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "onderzoekend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "invoelend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "meelevend", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(),
                "empatisch", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(), 
                "ijverig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().last(), 
                "out of the box", 
                executionContext
                );
        
        createBranchTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "schilderkunst", 
                executionContext
                );
        
        createBranchTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "schilderen", 
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
