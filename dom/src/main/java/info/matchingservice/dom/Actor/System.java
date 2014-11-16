package info.matchingservice.dom.Actor;

import info.matchingservice.dom.Need.PersonNeeds;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;

//@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
//@javax.jdo.annotations.DatastoreIdentity(
//        strategy = IdGeneratorStrategy.NATIVE,
//        column = "id")
//@javax.jdo.annotations.Discriminator(
//        strategy = DiscriminatorStrategy.CLASS_NAME,
//        column = "discriminator")
//@javax.jdo.annotations.Version(
//        strategy = VersionStrategy.VERSION_NUMBER,
//        column = "version")
//@javax.jdo.annotations.Uniques({
//    @javax.jdo.annotations.Unique(
//            name = "SYS_ID_UNQ", members = "uniqueActorId")
//})
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
@AutoComplete(repository=Systems.class,  action="autoComplete")
public class System extends Actor {
    
//    private String ownedBy;
//    
//    @Override
//    @Hidden
//    @javax.jdo.annotations.Column(allowsNull = "false")
//    @Disabled
//    public String getOwnedBy() {
//        return ownedBy;
//    }
//
//    public void setOwnedBy(final String owner) {
//        this.ownedBy = owner;
//    }
//    
//    private String uniqueActorId;
//    
//    @Override
//    @Disabled
//    @javax.jdo.annotations.Column(allowsNull = "false")
//    public String getUniqueActorId() {
//        return uniqueActorId;
//    }
//    
//    public void setUniqueActorId(final String id) {
//        this.uniqueActorId = id;
//    }
//    
    public String title() {
        return systemName;
    }
    
    
    //Region> organisationName /////////////////////////////////////////////////
    private String systemName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Organisatie naam")
    public String getSystemName() {
        return systemName;
    }
    
    public void setSystemName(final String fn) {
        this.systemName = fn;
    }
    


    //Region> ROLES /////////////////////////////////////////////////
        
    // Role PRINCIPAL 'Clean' code. Makes use of helpers in Helpers region
    
    @Named("Rol Opdrachtgever")
    @MemberOrder(sequence = "60")
    public System addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
    @Named("Geen opdrachtgever meer")
    @MemberOrder(sequence = "61")
    public System deleteRolePrincipal() {
        deleteRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideDeleteRolePrincipal() {
        return hideDeleteRolePrincipal(this, currentUserName());
    }
    
    @Hidden
    public Boolean getIsPrincipal() {
        return getIsPrincipal(this);
    }
    
    // ALL My Roles
    
    @Hidden
    @MemberOrder(sequence = "100")
    @Render(Type.EAGERLY)
    public List<SystemRole> getAllMyRoles() {
        QueryDefault<SystemRole> query =
                QueryDefault.create(
                        SystemRole.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    @MultiLine(numberOfLines=2)
    @Named("Rollen")
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
    
    
    //END Region> ROLES /////////////////////////////////////////////////

    //Region> PROFILE /////////////////////////////////////////////////////////////
   
    private SortedSet<Profile> profile = new TreeSet<Profile>();
   
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileOwner", dependentElement = "true")
    @Named("Mijn profiel")
    public SortedSet<Profile> getProfile() {
        return profile;
    }
   
    public void setProfile(final SortedSet<Profile> profile) {
        this.profile = profile;
    }
   
    @Named("Maak een profiel")
    public Profile makeProfile(
            @Named("Naam van je profiel")
            final String profileName
            ) {
        return makeProfile(profileName, this, getOwnedBy());
    }
   
    public boolean hideMakeProfile(final String testfield) {
        return hideMakeProfile(testfield, this, getOwnedBy());
    }
   
    public String validateMakeProfile(final String testfield) {
        return validateMakeProfile(testfield, this, getOwnedBy());
    }
    
    //END Region> PROFILE /////////////////////////////////////////////////////////////
    
    //Region> NEED /////////////////////////////////////////////////////////////
    
//    private SortedSet<PersonNeed> myNeeds = new TreeSet<PersonNeed>();
//    
//    @Render(Type.EAGERLY)
//    @Persistent(mappedBy = "needOwner", dependentElement = "true")
//    @Named("Mijn uitstaande opdrachten (tafels)")
//    public SortedSet<PersonNeed> getMyNeeds() {
//        return myNeeds;
//    }
//   
//    public void setMyNeeds(final SortedSet<PersonNeed> need) {
//        this.myNeeds = need;
//    }
//    
//    public boolean hideMyNeeds() {
//        return !getIsPrincipal();
//    }
//    
//    @Named("Plaats nieuwe opdracht")
//    public PersonNeed newNeed(
//            @Named("Korte opdrachtomschrijving voor tafel")
//            @MultiLine
//            final String needDescription
//            ) {
//        return newNeed(needDescription, this, currentUserName());
//    }
//    
//    public boolean hideNewNeed(final String needDescription) {
//        return hideNewNeed(needDescription, this);
//    }
    
    
    
    //helpers
//    @Programmatic
//    @Named("Plaats nieuwe opdracht")
//    public PersonNeed newNeed(
//            @Named("Korte opdrachtomschrijving") 
//            @MultiLine 
//            final String needDescription, 
//            final Organisation needOwner, final String ownedBy){
//        return needs.newNeed(needDescription, needOwner, ownedBy);
//    }
//    
//    @Programmatic
//    public boolean hideNewNeed(final String needDescription, final Organisation needOwner){
//        // if you are not the owner
//        if (!needOwner.getOwnedBy().equals(currentUserName())){
//            return true;
//        }
//        // if you have not Principal Role
//        if (!needOwner.getIsPrincipal()){
//            return true;
//        }
//        return false;
//    }
    
    //END Region> NEED /////////////////////////////////////////////////////////////
    
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // HELPERS Role PRINCIPAL
    
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
    
    // HELPERS Profile
    
    @Programmatic // now values can be set by fixtures
    public Profile makeProfile(
            @Named("Naam van je profiel")
            final String profileName, 
            final System system, 
            final String ownedBy) {
        return profiles.newProfile(profileName, system, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideMakeProfile(final String testfield, final System system, final String ownedBy) {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have already profile
        QueryDefault<Profile> query = 
                QueryDefault.create(
                        Profile.class, 
                    "findProfileByOwner", 
                    "profileOwner", this);
        return container.firstMatch(query) != null?
                true        
                :false;
    }
    
    @Programmatic // now values can be set by fixtures
    public String validateMakeProfile(final String testfield, final System person, final String ownedBy) {
        QueryDefault<Profile> query = 
                QueryDefault.create(
                        Profile.class, 
                    "findProfileByOwner", 
                    "profileOwner", this);
        return container.firstMatch(query) != null?
                "You already have a profile"        
                :null;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private SystemRoles roles;

    @Inject
    private Profiles profiles;
    
    @Inject
    private PersonNeeds needs;

    
}
