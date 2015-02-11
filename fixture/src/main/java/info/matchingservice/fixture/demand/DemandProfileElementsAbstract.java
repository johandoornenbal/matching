package info.matchingservice.fixture.demand;

import javax.inject.Inject;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementTags;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementTexts;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementDropDowns;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScript.ExecutionContext;

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
        ProfileElementDropDown newDropDown = profileElementDropDowns.createProfileElementDropDown(description, weight, dropdownValue, profileElementCategory, profileOwner, ownedBy);
        return executionContext.add(this,newDropDown);
    }
    
    protected ProfileElementTag createPassionTagsElement(
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.createProfileElementTag("PASSION_TAGS_ELEMENT", weight, ProfileElementType.PASSION_TAGS, profileOwner, ownedBy);
        return newElement;
    }
    
    protected ProfileElementTag createBrancheTagsElement(
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.createProfileElementTag("BRANCH_TAGS_ELEMENT", weight, ProfileElementType.BRANCHE_TAGS, profileOwner, ownedBy);
        return executionContext.add(this,newElement);
    }
    
    protected ProfileElementTag createQualityTagsElement(
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.createProfileElementTag("QUALITY_TAGS_ELEMENT", weight, ProfileElementType.QUALITY_TAGS, profileOwner, ownedBy);
        return executionContext.add(this,newElement);
    }
    
    protected ProfileElementText createLocation(
            String textValue,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
    		){
    	ProfileElementText newElement = profileElementTexts.createProfileElementText("LOCATION_ELEMENT", weight, textValue, ProfileElementType.LOCATION, profileOwner, ownedBy);
    	return executionContext.add(this,newElement);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @javax.inject.Inject
    ProfileElementTags profileElementTags;
    
    @Inject
    ProfileElementTexts profileElementTexts;
}
