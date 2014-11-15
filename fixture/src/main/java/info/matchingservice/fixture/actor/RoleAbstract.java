package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.SystemRole;
import info.matchingservice.dom.Actor.RoleType;
import info.matchingservice.dom.Actor.SystemRoles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class RoleAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected SystemRole createRole(
            RoleType role,
            String user,
            ExecutionContext executionContext
            ) {
        SystemRole newRole = roles.newRole(role, user);
        return executionContext.add(this,newRole);
    }
    
    //region > injected services
    @javax.inject.Inject
    SystemRoles roles;
}
