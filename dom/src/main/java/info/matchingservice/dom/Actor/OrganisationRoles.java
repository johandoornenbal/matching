package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(menuOrder = "20", repositoryFor = OrganisationRole.class)
@Named("Organisatie Rollen")
@Hidden
public class OrganisationRoles extends MatchingDomainService<OrganisationRole> {

    public OrganisationRoles() {
        super(OrganisationRoles.class, OrganisationRole.class);
    }
    
    public OrganisationRole newRole(final OrganisationRoleType role){
        return newRole(role, currentUserName());
    }
    
    public String validateNewRole(final OrganisationRoleType role){
        return validateNewRole(role, currentUserName());
    }
    
    public List<OrganisationRole> allRoles() {
        return container.allInstances(OrganisationRole.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public OrganisationRole newRole(final OrganisationRoleType role, final String userName) {
        OrganisationRole newrole = newTransientInstance(OrganisationRole.class);
        newrole.setRole(role);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewRole(final OrganisationRoleType role, final String userName){
        QueryDefault<OrganisationRole> query = 
                QueryDefault.create(
                        OrganisationRole.class, 
                    "findSpecificRole", 
                    "ownedBy", userName,
                    "role", role);        
        return container.firstMatch(query) != null?
        "This role you already have"        
        :null;
    }
    
    //
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
