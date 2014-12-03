package info.matchingservice.fixture.demand;

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class DemandProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Profile createDemandProfile(
            String demandProfileDescription,
            Integer weight,
            ProfileType profileType,
            Demand demandProfileOwner,
            String elementDescription,
            Integer elementWeight,
            Integer elementNumericValue,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Profile newDemandProfile = profiles.newDemandProfile(demandProfileDescription, weight, profileType, demandProfileOwner, ownedBy);
        
        createNumericElement(
                elementDescription, 
                elementWeight, 
                elementNumericValue,
                ProfileElementCategory.NUMERIC,
                newDemandProfile,
                ownedBy,
                executionContext
                );
        
        return executionContext.add(this,newDemandProfile);
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
