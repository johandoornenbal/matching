package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementTags;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementDropDowns;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class DemandProfileElementsAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
        
    protected ProfileElementDropDown createDropDownElement(
            String description,
            Integer weight,
            DropDownForProfileElement dropdownValue,
            ProfileElementType profileElementCategory,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementDropDown newDropDown = profileElementDropDowns.newProfileElementDropDown(description, weight, dropdownValue, profileElementCategory, profileOwner, ownedBy);
        return executionContext.add(this,newDropDown);
    }
    
    protected ProfileElementTag createPassionTagsElement(
            String description,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.newProfileElementTag(description, weight, ProfileElementType.PASSION_TAGS, profileOwner, ownedBy);
        return newElement;
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @javax.inject.Inject
    ProfileElementTags profileElementTags;
}
