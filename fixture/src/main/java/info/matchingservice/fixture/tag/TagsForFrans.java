package info.matchingservice.fixture.tag;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.DemandProfileElementsForFrans;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.demand.TestDemands;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

public class TagsForFrans extends TagsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new TagCategoriesFixture(), executionContext);
        
        //** demand 1 **//
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "zeilschepen",
                "frans",
                executionContext
                );
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "schilderen",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "oorlogszuchtig",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "dapper", 
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "stoer",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "gespierd",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "nieuwsgierig",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "onderzoekend", 
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "invoelend",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "meelevend",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "empatisch", 
                "frans",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "schilder",
                "frans",
                executionContext
                );
        
        //**demand 2 **//
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "aktie",
                "frans",
                executionContext
                );
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "buitensport",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "energiek",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "ondernemend",
                "frans",
                executionContext
                );
        
        //**demand 3 **//
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "onderzoek",
                "frans",
                executionContext
                );
        
        createPassionTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("PASSION").get(0), 
                "wetenschap",
                "frans",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("BRANCH").get(0), 
                "wetenschap",
                "frans",
                executionContext
                ); 
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "nauwkeurig",
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "nieuwsgierig",
                "frans",
                executionContext
                );
        
        //** supply **//
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "ijverig", 
                "frans",
                executionContext
                );
        
        createQualityTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("QUALITY").get(0), 
                "out of the box",
                "frans",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "schilderkunst", 
                "frans",
                executionContext
                );
        
        createBrancheTagHolder(
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0).findProfileElementByOwnerProfileAndDescription("BRANCHE").get(0), 
                "schilderen", 
                "frans",
                executionContext
                );
    }
    
    
    @Inject
    Profiles profiles;
}
