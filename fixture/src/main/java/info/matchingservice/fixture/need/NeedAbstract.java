package info.matchingservice.fixture.need;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.PersonNeeds;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class NeedAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected PersonNeed createNeed(
            String needDescription,
            Person needOwner,
            String user,
            ExecutionContext executionContext
            ) {
        PersonNeed newNeed = needs.newNeed(needDescription, needOwner, user);
        return executionContext.add(this,newNeed);
    }
    
    //region > injected services
    @javax.inject.Inject
    PersonNeeds needs;
}
