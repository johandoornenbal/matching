package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Demand.Demand;
import info.matchingservice.dom.Demand.DemandProfile;
import info.matchingservice.dom.Demand.DemandProfiles;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementCategory;
import info.matchingservice.dom.Profile.ProfileElementNature;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileNature;
import info.matchingservice.dom.Profile.ProfileType;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class DemandProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected DemandProfile createDemandProfile(
            String demandProfileDescription,
            Integer weight,
            ProfileNature profileNature,
            ProfileType profileType,
            Demand demandProfileOwner,
            String elementDescription,
            Integer elementWeight,
            Integer elementNumericValue,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        DemandProfile newDemandProfile = profiles.newDemandProfile(demandProfileDescription, weight, profileNature, profileType, demandProfileOwner, ownedBy);
        
        createNumericElement(
                elementDescription, 
                elementWeight, 
                elementNumericValue,
                ProfileElementCategory.NUMERIC,
                newDemandProfile,
                ProfileElementNature.MULTI_ELEMENT,
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
            ProfileElementNature nature,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementNumeric newNumeric = profileElementNumerics.newProfileElementNumeric(description, weight, numericValue, profileElementCategory, profileOwner, nature, ownedBy);
        return executionContext.add(this,newNumeric);
    }
    
    //region > injected services
    @javax.inject.Inject
    DemandProfiles profiles;
    
    @javax.inject.Inject
    ProfileElementNumerics profileElementNumerics;
}
