package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;
import info.matchingservice.dom.Party.Person;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class NeedAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Need createNeed(
            String needDescription,
            Person needOwner,
            String user,
            ExecutionContext executionContext
            ) {
        Need newNeed = needs.newNeed(needDescription, needOwner, user);
        return executionContext.add(this,newNeed);
    }
    
    //region > injected services
    @javax.inject.Inject
    Needs needs;
}
