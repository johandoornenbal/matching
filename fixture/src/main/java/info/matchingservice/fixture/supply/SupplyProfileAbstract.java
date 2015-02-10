package info.matchingservice.fixture.supply;

import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
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
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Profile newSupplyProfile = profiles.createSupplyProfile(supplyProfileDescription, weight, null, null, profileType, supplyProfileOwner, ownedBy);    
        return executionContext.add(this,newSupplyProfile);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileElementNumerics profileElementNumerics;
}
