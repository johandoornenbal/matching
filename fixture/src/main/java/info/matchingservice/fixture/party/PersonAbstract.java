package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Party;
import info.matchingservice.dom.Party.Persons;
import info.matchingservice.dom.Party.RoleType;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Party createPerson (
            String uniquePartyId,
            String firstName,
            String middleName,
            String lastName,
            RoleType role,
            String user,
            ExecutionContext executionContext
            ) {
        Party newPerson = persons.newPerson(uniquePartyId, firstName, middleName, lastName, role, user);
        return executionContext.add(this, newPerson);
    }
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;

}