package info.matchingservice.dom.Actor;

import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;

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
            name = "findMyOrganisations", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchOrganisationByOrganisationName", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE organisationName.matches(:organisationName)"),
    @javax.jdo.annotations.Query(
            name = "matchOrganisationByOrganisationNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE organisationName.indexOf(:organisationName) >= 0")                    
})

@DomainObject(autoCompleteRepository=Organisations.class, autoCompleteAction="autoComplete")
public class Organisation extends Actor {
    
    @Override
    public String title() {
        return getOrganisationName();
    }
    
    public String toString() {
        return organisationName;
    }
    
    
    //Region> organisationName /////////////////////////////////////////////////
    private String organisationName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(named="Organisatie naam")
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(final String fn) {
        this.organisationName = fn;
    }
    

    //Region> ROLES /////////////////////////////////////////////////
        
    // Role PRINCIPAL 'Clean' code. Makes use of helpers in Helpers region
    
    @MemberOrder(sequence = "60")
    @ActionLayout(named="Rol Opdrachtgever")
    public Organisation addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }

    @ActionLayout(named="Geen opdrachtgever meer")
    @MemberOrder(sequence = "61")
    public Organisation deleteRolePrincipal() {
        deleteRolePrincipal(this);
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
    
    @MemberOrder(sequence = "100")
    @CollectionLayout(hidden=Where.EVERYWHERE, render=RenderType.EAGERLY)
    public List<OrganisationRole> getAllMyRoles() {
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findMyRoles",
                        "roleOwner", this);
        return container.allMatches(query);
    }
    
    @PropertyLayout(named="Rollen", multiLine=2)
    public String getRoles() {
        TitleBuffer tb = new TitleBuffer();
        if (getIsPrincipal()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(OrganisationRoleType.PRINCIPAL.title());
        }
        return tb.toString();
    }
    
    // Role PRINCIPAL helpers
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsPrincipal(Organisation ownerOrganisation) {
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findSpecificRole",
                        "roleOwner", ownerOrganisation,
                        "role", OrganisationRoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRolePrincipal(String ownedBy) {
        roles.newRole(OrganisationRoleType.PRINCIPAL, this, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRolePrincipal(Organisation ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Principal
        return getIsPrincipal(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRolePrincipal(Organisation roleOwner) {
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findSpecificRole",
                        "roleOwner", roleOwner,
                        "role", OrganisationRoleType.PRINCIPAL);
        OrganisationRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true, this);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRolePrincipal(Organisation ownerPerson, String ownedBy) {
        // if you are not the owner of person
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person has not role Principal
        return !getIsPrincipal(ownerPerson);
    }  
            
    //END Region> ROLES /////////////////////////////////////////////////
    
    //Region> SUPPLIES /////////////////////////////////////////////////////////////   
    
    public Profile newOrganisationSupply(){
        return newSupplyAndProfile("Organisatie profiel van " + this.title(), 10, DemandSupplyType.ORGANISATION_DEMANDSUPPLY, this, "Organisatie profiel", 10, ProfileType.ORGANISATION_PROFILE, currentUserName());
    }
    
    //BUSINESS RULE
    // Je kunt alleen een Organisatieprofiel aanmaken als je
    // - eigenaar bent
    // - nog geen persoonssupply hebt
    public boolean hideNewOrganisationSupply(){
        return hideNewOrganisationSupply("", this);
    }
    
    public String validateNewOrganisationSupply(){
        return validateNewOrganisationSupply("", this);
    }
    
    // Supply helpers
    //BUSINESS RULE
    // Je kunt alleen een Persoonprofiel aanmaken als je
    // - eigenaar bent
    // - rol Student of Professional hebt
    // - nog geen persoonssupply hebt
    @Programmatic
    public boolean hideNewOrganisationSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        // if there is already a organsation Supply
        // TODO: deze werkt nog niet omdat een ownedBy op verschillende organisaties kan slaan van de owner
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByActorOwnerAndType", 
                    "supplyOwner", needOwner,
                    "supplyType", DemandSupplyType.ORGANISATION_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    @Programmatic
    public String validateNewOrganisationSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "Je bent niet de eigenaar";
        }
     
        // if there is already a personal Supply
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByActorOwnerAndType", 
                    "supplyOwner", needOwner,
                    "supplyType", DemandSupplyType.ORGANISATION_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return "Je hebt al een organisatie profiel";
        }
        
        return null;
    }
   
    //Region> DEMAND /////////////////////////////////////////////////////////////
    
    // method myDemands() is on Actor
    public boolean hideMyDemands() {
        return !getIsPrincipal();
    }
    
    // method NewDemand() is on Actor
    public boolean hideNewDemand(final String needDescription, final Integer weight, final DemandSupplyType demandSupplyType) {
        return hideNewDemand(needDescription, this);
    }
        
    //Need helpers
    
    @Programmatic
    public boolean hideNewDemand(final String demandDescription, final Actor demandOwner){
        // if you are not the owner
        if (!demandOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have not Principal Role
        if (!((Organisation) demandOwner).getIsPrincipal()){
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
    private OrganisationRoles roles;
    
}
