package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

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