package info.matchingservice.dom.Actor;

import info.matchingservice.dom.DemandSupply.DemandSupplyType;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findSystemUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.System "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchSystemBySystemName", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.System "
                    + "WHERE systemName.matches(:systemName)"),
    @javax.jdo.annotations.Query(
            name = "matchSystemBySystemNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.System "
                    + "WHERE systemName.indexOf(:systemName) >= 0")                    
})
@DomainObject(autoCompleteRepository=Systems.class, autoCompleteAction="autoComplete")
public class System extends Actor {
    
    public String title() {
        return systemName;
    }
    
    
    //Region> systemName /////////////////////////////////////////////////
    private String systemName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(named="Systeem naam")
    public String getSystemName() {
        return systemName;
    }
    
    public void setSystemName(final String systemName) {
        this.systemName = systemName;
    }

    //Region> ROLES /////////////////////////////////////////////////
        
    // Role PRINCIPAL 'Clean' code. Makes use of helpers in Helpers region
    
    @ActionLayout(named="Rol Opdrachtgever")
    @MemberOrder(sequence = "60")
    public System addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
    @ActionLayout(named="Geen opdrachtgever meer")
    @MemberOrder(sequence = "61")
    public System deleteRolePrincipal() {
        deleteRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideDeleteRolePrincipal() {
        return hideDeleteRolePrincipal(this, currentUserName());
    }
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Boolean getIsPrincipal() {
        return getIsPrincipal(this);
    }
    
    // ALL My Roles
    
    @CollectionLayout(hidden=Where.EVERYWHERE, render=RenderType.EAGERLY)
    @MemberOrder(sequence = "100")
    public List<SystemRole> getAllMyRoles() {
        QueryDefault<SystemRole> query =
                QueryDefault.create(
                        SystemRole.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    @PropertyLayout(named="Rollen", multiLine=2)
    public String getRoles() {
        TitleBuffer tb = new TitleBuffer();
        if (getIsPrincipal()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(SystemRoleType.PRINCIPAL.title());
        }
        return tb.toString();
    }
    
    // Role PRINCIPAL HELPERS
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsPrincipal(System ownerPerson) {
        QueryDefault<SystemRole> query =
                QueryDefault.create(
                        SystemRole.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", SystemRoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRolePrincipal(String ownedBy) {
        roles.newRole(SystemRoleType.PRINCIPAL, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRolePrincipal(System ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Principal
        return getIsPrincipal(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRolePrincipal(String ownedBy) {
        QueryDefault<SystemRole> query =
                QueryDefault.create(
                        SystemRole.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", SystemRoleType.PRINCIPAL);
        SystemRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRolePrincipal(System ownerPerson, String ownedBy) {
        // if you are not the owner of person
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person has not role Principal
        return !getIsPrincipal(ownerPerson);
    }  
    


    
    //END Region> ROLES /////////////////////////////////////////////////

    //Region> DEMAND /////////////////////////////////////////////////////////////
    
    // method myDemands() is on Actor
    public boolean hideMyDemands() {
        return !getIsPrincipal();
    }
    
    // method NewNeed() is on Actor
    public boolean hideNewDemand(final String demandDescription, final Integer weight, final DemandSupplyType demandSupplyType) {
        return hideNewDemand(demandDescription, this);
    }
        
    //Need helpers
    
    @Programmatic
    public boolean hideNewDemand(final String demandDescription, final Actor demandOwner){
        // if you are not the owner
        if (!demandOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have not Principal Role
        if (!((System) demandOwner).getIsPrincipal()){
            return true;
        }
        return false;
    }
    
    //END Region> DEMAND /////////////////////////////////////////////////////////////
    
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
        
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private SystemRoles roles;
 
}
