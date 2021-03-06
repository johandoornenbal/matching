package info.matchingservice.fixture.demand;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.Dropdowns.DropDownHbo;
import info.matchingservice.fixture.Dropdowns.DropDownMbo;
import info.matchingservice.fixture.Dropdowns.DropDownWo;
import info.matchingservice.fixture.actor.TestPersons;

public class DemandProfileElementsForFrans extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new DropDownHbo(), executionContext);
        executeChild(new DropDownMbo(), executionContext);
        executeChild(new DropDownWo(), executionContext);
        
        //** op eerste demand **//
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtige", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        
        createPassionTagsElement(
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtige", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );

        createRequiredRoleElement(
                true,
                true,
                false,
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("oorlogszuchtige", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        createQualityTagsElement(
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("nieuwsgierig", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createBrancheTagsElement(
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        createTimePeriod(
                new LocalDate(2015, 3, 1),
                new LocalDate(2015, 3, 20),
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        createAgeElement(
                40,
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        createLocation(
                "8926PJ",
                10,
                profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );

        createDropDownElement(
                "EDUCATION_LEVEL",
                10,
                dropDownForProfileElements.findDropDowns("hbo", ProfileElementType.EDUCATION_LEVEL).get(0),
                ProfileElementType.EDUCATION_LEVEL,
                profiles.searchNameOfProfilesOfTypeByOwner("meelevend", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE, "frans").get(0),
                "frans",
                executionContext
        );
        
        //** op tweede demand **//
        
        createPassionTagsElement( 
                10, 
                profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext
                );
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createLocation(
        		"8926PJ",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("iemand die in is voor aktie", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext
                );
        
        //** op derde demand **//
        createPassionTagsElement( 
                10, 
                profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext
                );
        
        createQualityTagsElement(
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
        		"frans",
        		executionContext
        		);
        
        createBrancheTagsElement( 
                10, 
                profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext
                );
        
        createLocation(
        		"8926PJ",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("onderzoekend persoon", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"frans").get(0),
                "frans", 
                executionContext
                );
        

    }
        
    
    @Inject
    Profiles profiles;

    @Inject
    DropDownForProfileElements dropDownForProfileElements;
    
}
