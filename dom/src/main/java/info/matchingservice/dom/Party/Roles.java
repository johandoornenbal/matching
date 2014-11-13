package info.matchingservice.dom.Party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(menuOrder = "20", repositoryFor = Role.class)
@Named("Rollen")
@Hidden
public class Roles extends MatchingDomainService<Role> {

    public Roles() {
        super(Roles.class, Role.class);
    }
    
    public Role newRole(final RoleType role){
        return newRole(role, currentUserName());
    }
    
    public String validateNewRole(final RoleType role){
        return validateNewRole(role, currentUserName());
    }
    
    public List<Role> allRoles() {
        return container.allInstances(Role.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Role newRole(final RoleType role, final String userName) {
        Role newrole = newTransientInstance(Role.class);
        newrole.setRole(role);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewRole(final RoleType role, final String userName){
        QueryDefault<Role> query = 
                QueryDefault.create(
                        Role.class, 
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
