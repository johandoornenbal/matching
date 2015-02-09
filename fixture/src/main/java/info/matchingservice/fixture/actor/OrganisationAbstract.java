package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Organisation;
import info.matchingservice.dom.Actor.OrganisationRole;
import info.matchingservice.dom.Actor.OrganisationRoleType;
import info.matchingservice.dom.Actor.OrganisationRoles;
import info.matchingservice.dom.Actor.Organisations;
import info.matchingservice.dom.Actor.Role;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class OrganisationAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Actor createOrganisation (
            String organisationName,
            String user,
            ExecutionContext executionContext
            ) {
        Actor newOrganisation = organisations.createOrganisation(organisationName, user);
        createRole(OrganisationRoleType.PRINCIPAL, (Organisation) newOrganisation, user, executionContext);               
        return executionContext.add(this, newOrganisation);
    }
    
    protected Role createRole (
            OrganisationRoleType role,
            Organisation roleOwner,
            String user,
            ExecutionContext executionContext   
            ) {
        OrganisationRole newRole = roles.createRole(role, roleOwner, user);
        return executionContext.add(this,newRole);
    }
    
    //region > injected services
    @javax.inject.Inject
    private Organisations organisations;
    
    //region > injected services
    @javax.inject.Inject
    OrganisationRoles roles;
}