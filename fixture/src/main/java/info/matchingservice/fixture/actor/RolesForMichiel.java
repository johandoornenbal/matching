package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class RolesForMichiel extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.STUDENT, 
                "michiel",
                executionContext
                );

    }

}
