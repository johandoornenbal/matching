package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class RolesForFrans extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                PersonRoleType.STUDENT, 
                "frans",
                executionContext
                );
        
        createRole(
                PersonRoleType.PRINCIPAL, 
                "frans",
                executionContext
                );

    }

}
