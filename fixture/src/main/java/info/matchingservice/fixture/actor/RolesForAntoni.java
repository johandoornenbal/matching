package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class RolesForAntoni extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.STUDENT, 
                "antoni",
                executionContext
                );

    }

}
