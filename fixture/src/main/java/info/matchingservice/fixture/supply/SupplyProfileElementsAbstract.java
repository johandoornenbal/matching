package info.matchingservice.fixture.supply;

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

public abstract class SupplyProfileElementsAbstract extends FixtureScript {

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
    
    protected ProfileElementText createPassion(
            String description,
            Integer weight,
            String textValue,
            ProfileElementType profileElementCategory,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementText newText = profileElementTexts.newProfileElementText(description, weight, textValue, profileElementCategory, profileOwner, ownedBy);
        return executionContext.add(this,newText);
    }
    
    protected ProfileElementTag createBranche(
            String description,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.newProfileElementTag(description, weight, ProfileElementType.BRANCHE_TAGS, profileOwner, ownedBy);
        return executionContext.add(this,newElement);
    }
    
    protected ProfileElementTag createQualityTagsElement(
            String description,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
            ){
        ProfileElementTag newElement = profileElementTags.newProfileElementTag(description, weight, ProfileElementType.QUALITY_TAGS, profileOwner, ownedBy);
        return executionContext.add(this,newElement);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @Inject
    ProfileElementTexts profileElementTexts;
    
    @Inject
    ProfileElementTags profileElementTags;
}