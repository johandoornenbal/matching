package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Party;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class ProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Profile createProfile(
            String testString,
            Party profileOwner,
            String user,
            ExecutionContext executionContext
            ) {
        Profile newProfile = profiles.newProfile(testString, profileOwner, user);
        return executionContext.add(this,newProfile);
    }
    
    //region > injected services
    @javax.inject.Inject
    Profiles profiles;
}
