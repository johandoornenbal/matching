package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

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
