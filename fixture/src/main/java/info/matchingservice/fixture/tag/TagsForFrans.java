package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.PersonForFrans;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.DemandsForFrans;
import info.matchingservice.fixture.supply.SuppliesForFrans;

import javax.inject.Inject;

public class TagsForFrans extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
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
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "ijverig", 
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("persoonlijk", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).getCollectProfileElements().first(), 
                "out of the box", 
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
