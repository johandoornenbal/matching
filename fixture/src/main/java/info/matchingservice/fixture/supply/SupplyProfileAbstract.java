package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.dom.Profile.ProfileElementNature;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Supply.Supply;
import info.matchingservice.dom.Supply.SupplyProfile;
import info.matchingservice.dom.Supply.SupplyProfiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SupplyProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected SupplyProfile createSupplyProfile(
            String supplyProfileDescription,
            ProfileNature profileNature,
            ProfileType profileType,
            Supply supplyProfileOwner,
            String elementDescription,
            Integer elementWeight,
            Integer elementNumericValue,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        SupplyProfile newSupplyProfile = profiles.newSupplyProfile(supplyProfileDescription, profileNature, profileType, supplyProfileOwner, ownedBy);
        
        createNumericElement(
                elementDescription, 
                elementWeight, 
                elementNumericValue,
                ProfileElementCategory.NUMERIC,
                newSupplyProfile,
                ProfileElementNature.MULTI_ELEMENT,
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
            ProfileElementNature nature,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementNumeric newNumeric = profileElementNumerics.newProfileElementNumeric(description, weight, numericValue, profileElementCategory, profileOwner, nature, ownedBy);
        return executionContext.add(this,newNumeric);
    }
    
    //region > injected services
    @javax.inject.Inject
    SupplyProfiles profiles;
    
    @javax.inject.Inject
    ProfileElementNumerics profileElementNumerics;
}
