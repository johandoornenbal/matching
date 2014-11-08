package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

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
