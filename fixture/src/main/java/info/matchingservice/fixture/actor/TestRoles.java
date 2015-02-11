package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class TestRoles extends RoleAbstract {

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
        
        createRole(
                PersonRoleType.STUDENT, 
                "gerard",
                executionContext
                );
        
        createRole(
                PersonRoleType.PROFESSIONAL, 
                "gerard",
                executionContext
                );
        
        createRole(
                PersonRoleType.PRINCIPAL, 
                "rembrandt",
                executionContext
                );
        
        createRole(
                PersonRoleType.PROFESSIONAL, 
                "rembrandt",
                executionContext
                );
        
        createRole(
                PersonRoleType.STUDENT, 
                "michiel",
                executionContext
                );
        
        createRole(
                PersonRoleType.PRINCIPAL, 
                "michiel",
                executionContext
                );
        
        createRole(
                PersonRoleType.PROFESSIONAL, 
                "antoni",
                executionContext
                );

    }

}
