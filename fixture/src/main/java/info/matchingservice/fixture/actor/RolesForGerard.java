package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class RolesForGerard extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                PersonRoleType.STUDENT, 
                "gerard",
                executionContext
                );
        
        createRole(
                PersonRoleType.PROFESSIONAL, 
                "gerard",
                executionContext
                );
        
        createRole(
                PersonRoleType.PRINCIPAL, 
                "gerard",
                executionContext
                );

    }

}
