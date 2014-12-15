package info.matchingservice.fixture.demand;

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Profile.Profile;
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
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Profile newDemandProfile = profiles.newDemandProfile(demandProfileDescription, weight, profileType, demandProfileOwner, ownedBy);
        
        return executionContext.add(this,newDemandProfile);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
}
