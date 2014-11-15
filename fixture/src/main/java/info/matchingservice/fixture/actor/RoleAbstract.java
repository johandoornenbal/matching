package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRole;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.PersonRoles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class RoleAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected PersonRole createRole(
            PersonRoleType role,
            String user,
            ExecutionContext executionContext
            ) {
        PersonRole newRole = roles.newRole(role, user);
        return executionContext.add(this,newRole);
    }
    
    //region > injected services
    @javax.inject.Inject
    PersonRoles roles;
}
