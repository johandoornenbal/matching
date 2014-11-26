package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Actor createPerson (
            String uniquePartyId,
            String firstName,
            String middleName,
            String lastName,
            String user,
            ExecutionContext executionContext
            ) {
        Actor newPerson = persons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
                       
        return executionContext.add(this, newPerson);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;


}