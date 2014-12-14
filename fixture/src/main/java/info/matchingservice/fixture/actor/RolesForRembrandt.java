package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class RolesForRembrandt extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                PersonRoleType.PRINCIPAL, 
                "rembrandt",
                executionContext
                );
        
        createRole(
                PersonRoleType.PROFESSIONAL, 
                "rembrandt",
                executionContext
                );

    }

}
