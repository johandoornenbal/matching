package info.matchingservice.dom.Party;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(menuOrder = "20", repositoryFor = Role.class)
@Named("Rollen")
public class Roles extends MatchingDomainService<Role> {

    public Roles() {
        super(Roles.class, Role.class);
    }
    
    public Role newRole(final RoleType role){
        Role newrole = newTransientInstance(Role.class);
        newrole.setRole(role);
        newrole.setOwnedBy(currentUserName());
        persist(newrole);
        return newrole;
    }
    
    public List<Role> allRoles() {
        return container.allInstances(Role.class);
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
