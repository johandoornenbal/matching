package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementNumerics;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SupplyProfileNumericAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
        
    protected ProfileElementNumeric createNumericElement(
            String description,
            Integer weight,
            Integer numericValue,
            ProfileElementType profileElementCategory,
            Profile profileOwner,
            String ownedBy,
            ExecutionContext executionContext
            ){
        ProfileElementNumeric newNumeric = profileElementNumerics.createProfileElementNumeric(description, weight, numericValue, profileElementCategory, profileOwner, ownedBy);
        return executionContext.add(this,newNumeric);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementNumerics profileElementNumerics;
}
