package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = SystemRole.class)
@Hidden
@DomainServiceLayout(named="Systeem Rollen", menuOrder="20")
public class SystemRoles extends MatchingDomainService<SystemRole> {

    public SystemRoles() {
        super(SystemRoles.class, SystemRole.class);
    }
    
    public SystemRole newRole(final SystemRoleType role){
        return newRole(role, currentUserName());
    }
    
    public String validateNewRole(final SystemRoleType role){
        return validateNewRole(role, currentUserName());
    }
    
    public List<SystemRole> allRoles() {
        return container.allInstances(SystemRole.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public SystemRole newRole(final SystemRoleType role, final String userName) {
        SystemRole newrole = newTransientInstance(SystemRole.class);
        newrole.setRole(role);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewRole(final SystemRoleType role, final String userName){
        QueryDefault<SystemRole> query = 
                QueryDefault.create(
                        SystemRole.class, 
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
