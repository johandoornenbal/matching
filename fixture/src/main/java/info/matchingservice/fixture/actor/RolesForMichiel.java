package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class RolesForMichiel extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                PersonRoleType.STUDENT, 
                "michiel",
                executionContext
                );

    }

}
