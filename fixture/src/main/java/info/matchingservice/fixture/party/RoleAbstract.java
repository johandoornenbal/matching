package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.Role;
import info.matchingservice.dom.Party.RoleType;
import info.matchingservice.dom.Party.Roles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class RoleAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Role createRole(
            RoleType role,
            String user,
            ExecutionContext executionContext
            ) {
        Role newRole = roles.newRole(role, user);
        return executionContext.add(this,newRole);
    }
    
    //region > injected services
    @javax.inject.Inject
    Roles roles;
}
