package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = PersonRole.class)
@Hidden
@DomainServiceLayout(named="Persoon Rollen", menuOrder="20")
public class PersonRoles extends MatchingDomainService<PersonRole> {

    public PersonRoles() {
        super(PersonRoles.class, PersonRole.class);
    }
    
    public PersonRole newRole(final PersonRoleType role){
        return newRole(role, currentUserName());
    }
    
    public String validateNewRole(final PersonRoleType role){
        return validateNewRole(role, currentUserName());
    }
    
    public List<PersonRole> allRoles() {
        return container.allInstances(PersonRole.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public PersonRole newRole(final PersonRoleType role, final String userName) {
        PersonRole newrole = newTransientInstance(PersonRole.class);
        newrole.setRole(role);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewRole(final PersonRoleType role, final String userName){
        QueryDefault<PersonRole> query = 
                QueryDefault.create(
                        PersonRole.class, 
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
