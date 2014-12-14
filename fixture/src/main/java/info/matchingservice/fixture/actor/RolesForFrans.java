package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class RolesForFrans extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                PersonRoleType.PROFESSIONAL, 
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
