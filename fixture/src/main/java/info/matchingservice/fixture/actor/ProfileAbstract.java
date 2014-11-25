package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Profile.SupplyProfile;
import info.matchingservice.dom.Profile.SupplyProfiles;
import info.matchingservice.dom.Profile.SuperProfile;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected SuperProfile createProfile(
            String testProfileName,
            Person profileOwner,
            String user,
            ExecutionContext executionContext
            ) {
        SupplyProfile newProfile = profiles.newProfile(testProfileName, profileOwner, user);
        return executionContext.add(this,newProfile);
    }
    
    //region > injected services
    @javax.inject.Inject
    SupplyProfiles profiles;
}
