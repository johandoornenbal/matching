package info.matchingservice.fixture.demand;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementDropDowns;
import info.matchingservice.dom.Profile.ProfileElementLocation;
import info.matchingservice.dom.Profile.ProfileElementLocations;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementTags;
import info.matchingservice.dom.Profile.ProfileElementTexts;
import info.matchingservice.dom.Profile.ProfileElementTimePeriod;
import info.matchingservice.dom.Profile.ProfileElementTimePeriods;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Profile.RequiredProfileElementRole;
import info.matchingservice.dom.Profile.RequiredProfileElementRoles;

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
        ProfileElementTag newElement = profileElementTags.createProfileElementTag("BRANCHE_TAGS_ELEMENT", weight, ProfileElementType.BRANCHE_TAGS, profileOwner, ownedBy);
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
    
    protected ProfileElementLocation createLocation(
            String textValue,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
    		){
    	ProfileElementLocation newElement = profileElementLocations.createProfileElementLocation("LOCATION_ELEMENT", weight, textValue, ProfileElementType.LOCATION, profileOwner, ownedBy);
    	return executionContext.add(this,newElement);
    }
    
    protected ProfileElementTimePeriod createTimePeriod(
            LocalDate startDate,
            LocalDate endDate,
            Integer weight,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext 
    		){
    	ProfileElementTimePeriod newElement = profileElementTimePeriods.createProfileElementTimePeriod("TIME_PERIOD", weight, startDate, endDate, ProfileElementType.TIME_PERIOD, profileOwner, ownedBy);
    	return executionContext.add(this,newElement);
    }
    
    protected ProfileElementNumeric createAgeElement(
             final Integer numericValue,
             final Integer weight,
             final Profile profileOwner,
             final String ownedBy,
            ExecutionContext executionContext 
    		){
    	ProfileElementNumeric newElement = profileElementNumerics.createProfileElementNumeric("AGE_ELEMENT", weight, numericValue, ProfileElementType.AGE, profileOwner, ownedBy);
    	return executionContext.add(this,newElement);
    }

    protected RequiredProfileElementRole createRequiredRoleElement(
            final boolean student,
            final boolean professional,
            final boolean principal,
            final Integer weight,
            final Profile profileOwner,
            final String ownedBy,
            ExecutionContext executionContext
    ){
        RequiredProfileElementRole newElement = requiredProfileElementRoles.createRequiredProfileElementRole("REQUIRED_ROLE_ELEMENT", weight, ProfileElementType.ROLE_REQUIRED, profileOwner, student, professional, principal, ownedBy);
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
    
    @Inject
    ProfileElementLocations profileElementLocations;
    
    @Inject
    ProfileElementTimePeriods profileElementTimePeriods;
    
    @Inject
    ProfileElementNumerics profileElementNumerics;

    @Inject
    RequiredProfileElementRoles requiredProfileElementRoles;
}
