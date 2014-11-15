package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class RolesForRembrandt extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.PRINCIPAL, 
                "rembrandt",
                executionContext
                );

    }

}
