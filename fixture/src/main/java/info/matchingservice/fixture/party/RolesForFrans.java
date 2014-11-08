package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

public class RolesForFrans extends RoleAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createRole(
                RoleType.STUDENT, 
                "frans",
                executionContext
                );
        
        createRole(
                RoleType.PROFESSIONAL, 
                "frans",
                executionContext
                );

    }

}
