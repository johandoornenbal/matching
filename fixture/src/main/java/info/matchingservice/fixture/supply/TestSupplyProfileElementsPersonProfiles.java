package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class TestSupplyProfileElementsPersonProfiles extends SupplyProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        
        //** frans **//
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben gek op schilderen en schilder het liefst vrouwen in de bloei van hun leven. Kleurgebruik en lichtinval ben ik helemaal gek van.", 
                ProfileElementType.PASSION, 
                profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"1234AB",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans", 
        		executionContext
        		);
        
        //** gerard **//
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"gerard").get(0),
                "gerard",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"gerard").get(0),
        		"gerard", 
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4987 VW",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"gerard").get(0),
        		"gerard",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben natuurlijk bezeten van schilderen. Het witte doek bekladden: ik doe niets liever dan dat. "
                + "Kleurgebruik en lichtinval ben ik helemaal gek van. De geur van olieverf en het gevoel van een verse"
                + "penseel in de hand doet me opstijgen. Mijn passie voor het portret in de gouden eeuw gaat me"
                + " tot grote hoogten brengen.", 
                ProfileElementType.PASSION, 
                profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"gerard").get(0),
                "gerard", 
                executionContext);
        
//        createUseTimePeriod(
//        		"USE_TIME_PERIOD",
//        		true,
//        		10,
//        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"gerard").get(0),
//        		"gerard", 
//                executionContext);
        
        //** rembrandt **//
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
                "rembrandt",
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4351 FK",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
        		"rembrandt", 
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Enorme doeken inspireren me bijzonder. Daar moet ik gewoon op schilderen. Lichtinval, kleurgebruik, het vastleggen van "
                + "fluwelen stoffen: niet kan me meer boeien dan dat. En van tijd tot tijd een cognacje met een havanna.", 
                ProfileElementType.PASSION, 
                profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"rembrandt").get(0),
                "rembrandt", 
                executionContext);
        
        //** michiel **//
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0),
                "michiel",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0),
                "michiel",
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Ik ben gek op zeilschepen, wapens in het algemeen en kanonnen in het bijzonder. "
                + "Strategie ontwikkeling staat centraal in mijn leven. De kunst van de oorlog "
                + "kruitdampen en strijdtonelen vullen mijn gedachten voortdurend.", 
                ProfileElementType.PASSION, 
                profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0),
                "michiel", 
                executionContext);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"5674 FK",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        //** antoni **//
        
        createQualityTagsElement(
        		"QUALITY_TAGS_ELEMENT",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0),
        		"antoni",
        		executionContext
        		);
        
        createLocation(
        		"LOCATION_ELEMENT",
        		"4321 ZX",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0),
        		"antoni",
        		executionContext
        		);
        
        createBranche(
        		"BRANCHE_ELEMENT", 
        		10, 
        		profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0),
        		"antoni", 
        		executionContext
        		);
        
        createPassion(
                "PASSION_ELEMENT", 
                10, 
                "Mijn grote passie is de wetenschap en microscopen in het bijzonder. "
                + "Ik bestudeer het liefst de hele dag micro organismen zoals bacterien. "
                + "Ook virussen en ziektekiemen onderzoek behoort tot mijn veld van interesse.", 
                ProfileElementType.PASSION, 
                profiles.searchNameOfProfilesOfTypeByOwner("PERSON_PROFILE_OF", DemandOrSupply.SUPPLY, ProfileType.PERSON_PROFILE,"antoni").get(0),
                "antoni", 
                executionContext);
    }
    
    
    @Inject Profiles profiles;
    
    
}
