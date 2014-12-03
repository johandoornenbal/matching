package info.matchingservice.fixture.supply;

import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SupplyProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Profile createSupplyProfile(
            String supplyProfileDescription,
            Integer weight,
            ProfileType profileType,
            Supply supplyProfileOwner,
            String elementDescription,
            Integer elementWeight,
            Integer elementNumericValue,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Profile newSupplyProfile = profiles.newSupplyProfile(supplyProfileDescription, weight, profileType, supplyProfileOwner, ownedBy);
        
        createNumericElement(
                elementDescription, 
                elementWeight, 
                elementNumericValue,
                ProfileElementCategory.NUMERIC,
                newSupplyProfile,
                ownedBy,
                executionContext
                );
        
        return executionContext.add(this,newSupplyProfile);
    }
    
    protected ProfileElementNumeric createNumericElement(
            String description,
            Integer weight,
            Integer numericValue,
            ProfileElementCategory profileElementCategory,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementNumeric newNumeric = profileElementNumerics.newProfileElementNumeric(description, weight, numericValue, profileElementCategory, profileOwner, ownedBy);
        return executionContext.add(this,newNumeric);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementNumerics profileElementNumerics;
}
