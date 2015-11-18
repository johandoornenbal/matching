package info.matchingservice.fixture.demand;

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

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
        Profile newDemandProfile = profiles.createDemandProfile(demandProfileDescription, weight, null, null, profileType, null, demandProfileOwner, ownedBy);
        
        return executionContext.add(this,newDemandProfile);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
    
    @javax.inject.Inject
    ProfileTypeMatchingRules profileTypeMatchingRules;
}
