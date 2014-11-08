package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Party;
import info.matchingservice.dom.Party.Persons;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Party createPerson (
            String uniquePartyId,
            String firstName,
            String middleName,
            String lastName,
            String user,
            ExecutionContext executionContext
            ) {
        Party newPerson = persons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
        return executionContext.add(this, newPerson);
    }
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;

}