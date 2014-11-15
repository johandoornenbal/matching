package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class RolesForFrans extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.STUDENT, 
                "frans",
                executionContext
                );
        
        createRole(
                RoleType.PRINCIPAL, 
                "frans",
                executionContext
                );

    }

}
