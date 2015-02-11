package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;
import javax.jdo.listener.CreateLifecycleListener;

public class DemandProfileElementsForMichiel extends DemandProfileElementsAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
                
        createQualityTagsElement(
        		"kwaliteit out of the box en dapper",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("wie maar wil", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createPassionTagsElement(
        		"gevraagd de volgende passies",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("wie maar wil", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createQualityTagsElement(
        		"iemand met overwicht",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("Commandant", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
        
        createQualityTagsElement(
        		"hardloper",
        		10,
        		profiles.searchNameOfProfilesOfTypeByOwner("Voetvolk", DemandOrSupply.DEMAND, ProfileType.PERSON_PROFILE,"michiel").get(0),
        		"michiel",
        		executionContext
        		);
    }
        

    @Inject
    private DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    private Persons persons;
    
    @Inject
    Profiles profiles;
    
    
    
}
