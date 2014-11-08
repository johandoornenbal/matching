package info.matchingservice.dom.Party;

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
        return newrole;
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
