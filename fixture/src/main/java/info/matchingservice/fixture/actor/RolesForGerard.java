package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class RolesForGerard extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.STUDENT, 
                "gerard",
                executionContext
                );
        
        createRole(
                RoleType.PROFESSIONAL, 
                "gerard",
                executionContext
                );
        
        createRole(
                RoleType.PRINCIPAL, 
                "gerard",
                executionContext
                );

    }

}
