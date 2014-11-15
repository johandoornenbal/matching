package info.matchingservice.dom.Actor;

import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.PersonNeeds;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Testobjects.TestRelatedObject;
import info.matchingservice.dom.Testobjects.TestSecRelatedObject;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
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

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "PERSON_ID_UNQ", members = "uniqueActorId")
})
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Person "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Person "
                    + "WHERE lastName.matches(:lastName)"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Person "
                    + "WHERE lastName.indexOf(:lastName) >= 0")                    
})
@AutoComplete(repository=Persons.class,  action="autoComplete")
public class Person extends Actor {
    
    private String ownedBy;
    
    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    private String uniqueActorId;
    
    @Override
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getUniqueActorId() {
        return uniqueActorId;
    }
    
    public void setUniqueActorId(final String id) {
        this.uniqueActorId = id;
    }
    
    public String title() {
        if (getMiddleName()==null) {
            return this.getFirstName() + " " + this.getLastName();
        } else {
            return this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName();
        }
    }
    
    
    //Region> firstName /////////////////////////////////////////////////
    private String firstName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Voornaam")
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String fn) {
        this.firstName = fn;
    }
    
    //Region> middleName /////////////////////////////////////////////////
    private String middleName;
    
    @MemberOrder(sequence = "20")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Tussen")
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(final String mn) {
        this.middleName = mn;
    }
  
    //Region> lastName /////////////////////////////////////////////////
    private String lastName;
    
    @MemberOrder(sequence = "30")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Achternaam")
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String ln) {
        this.lastName = ln;
    }

    //Region> ROLES /////////////////////////////////////////////////
    
    // Role STUDENT 'Clean' code. Makes use of helpers in Helpers region
    
    @Named("Rol Student")
    @MemberOrder(sequence = "40")
    public Person addRoleStudent() {
        addRoleStudent(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleStudent() {
        return hideAddRoleStudent(this, currentUserName());
    }
    
    @Named("Geen student meer")
    @MemberOrder(sequence = "41")
    public Person deleteRoleStudent() {
        deleteRoleStudent(currentUserName());;
        return this;
    }
    
    public boolean hideDeleteRoleStudent() {
        return hideDeleteRoleStudent(this, currentUserName());
    }
    
    @Hidden
    public Boolean getIsStudent() {
        return getIsStudent(this);
    }

    // Role PROFESSIONAL 'Clean' code. Makes use of helpers in Helpers region
    
    @Named("Rol ZP-er")
    @MemberOrder(sequence = "50")
    public Person addRoleProfessional() {
        addRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleProfessional() {
        return hideAddRoleProfessional(this, currentUserName());
    }
    
    @Named("Geen ZP-er meer")
    @MemberOrder(sequence = "51")
    public Person deleteRoleProfessional() {
        deleteRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideDeleteRoleProfessional() {
        return hideDeleteRoleProfessional(this, currentUserName());
    }
    
    @Hidden
    public Boolean getIsProfessional() {
        return getIsProfessional(this);
    }
    
    // Role PRINCIPAL 'Clean' code. Makes use of helpers in Helpers region
    
    @Named("Rol Opdrachtgever")
    @MemberOrder(sequence = "60")
    public Person addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
    @Named("Geen opdrachtgever meer")
    @MemberOrder(sequence = "61")
    public Person deleteRolePrincipal() {
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
    public List<PersonRole> getAllMyRoles() {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    @MultiLine(numberOfLines=2)
    @Named("Rollen")
    public String getRoles() {
        TitleBuffer tb = new TitleBuffer();
        if (getIsStudent()) {
            tb.append(PersonRoleType.STUDENT.title());
        }
        if (getIsProfessional()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(PersonRoleType.PROFESSIONAL.title());
        }
        if (getIsPrincipal()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(PersonRoleType.PRINCIPAL.title());
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
    
    private SortedSet<PersonNeed> myNeeds = new TreeSet<PersonNeed>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "needOwner", dependentElement = "true")
    @Named("Mijn uitstaande opdrachten (tafels)")
    public SortedSet<PersonNeed> getMyNeeds() {
        return myNeeds;
    }
   
    public void setMyNeeds(final SortedSet<PersonNeed> need) {
        this.myNeeds = need;
    }
    
    public boolean hideMyNeeds() {
        return !getIsPrincipal();
    }
    
    @Named("Plaats nieuwe opdracht")
    public PersonNeed newNeed(
            @Named("Korte opdrachtomschrijving voor tafel")
            @MultiLine
            final String needDescription
            ) {
        return newNeed(needDescription, this, currentUserName());
    }
    
    public boolean hideNewNeed(final String needDescription) {
        return hideNewNeed(needDescription, this);
    }
    
    
    
    //helpers
    @Programmatic
    @Named("Plaats nieuwe opdracht")
    public PersonNeed newNeed(
            @Named("Korte opdrachtomschrijving") 
            @MultiLine 
            final String needDescription, 
            final Person needOwner, final String ownedBy){
        return needs.newNeed(needDescription, needOwner, ownedBy);
    }
    
    @Programmatic
    public boolean hideNewNeed(final String needDescription, final Person needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have not Principal Role
        if (!needOwner.getIsPrincipal()){
            return true;
        }
        return false;
    }
    
    //END Region> NEED /////////////////////////////////////////////////////////////

    //Region> testobjects /////////////////////////////////////////////////////////////
    
    private SortedSet<TestRelatedObject> testObject = new TreeSet<TestRelatedObject>();
    
    @Hidden
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    public SortedSet<TestRelatedObject> getTestObject() {
        return testObject;
    }
    
    public void setTestObject(final SortedSet<TestRelatedObject> test) {
        this.testObject = test;
    }
    
    private SortedSet<TestSecRelatedObject> testSecObject = new TreeSet<TestSecRelatedObject>();
    
    @Hidden
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    public SortedSet<TestSecRelatedObject> getTestSecObject() {
        return testSecObject;
    }
    
    public void setTestSecObject(final SortedSet<TestSecRelatedObject> test) {
        this.testSecObject = test;
    }
    
    //END Region> testobjects /////////////////////////////////////////////////////////////
    
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // HELPERS Role STUDENT
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsStudent(Person ownerPerson) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", PersonRoleType.STUDENT);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRoleStudent(String ownedBy) {
        roles.newRole(PersonRoleType.STUDENT, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRoleStudent(Person ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Student
        return getIsStudent(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRoleStudent(String ownedBy) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", PersonRoleType.STUDENT);
        PersonRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRoleStudent(Person ownerPerson, String ownedBy) {
        // if you are not the owner of person
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person has not role Student
        return !getIsStudent(ownerPerson);
    }
    
    // HELPERS Role PROFESSIONAL
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsProfessional(Person ownerPerson) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", PersonRoleType.PROFESSIONAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRoleProfessional(String ownedBy) {
        roles.newRole(PersonRoleType.PROFESSIONAL, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRoleProfessional(Person ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Professional
        return getIsProfessional(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRoleProfessional(String ownedBy) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", PersonRoleType.PROFESSIONAL);
        PersonRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRoleProfessional(Person ownerPerson, String ownedBy) {
        // if you are not the owner of person
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person has not role Professional
        return !getIsProfessional(ownerPerson);
    }    
    
    // HELPERS Role PRINCIPAL
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsPrincipal(Person ownerPerson) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", PersonRoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRolePrincipal(String ownedBy) {
        roles.newRole(PersonRoleType.PRINCIPAL, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRolePrincipal(Person ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Principal
        return getIsPrincipal(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRolePrincipal(String ownedBy) {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", PersonRoleType.PRINCIPAL);
        PersonRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRolePrincipal(Person ownerPerson, String ownedBy) {
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
            final Person person, 
            final String ownedBy) {
        return profiles.newProfile(profileName, person, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideMakeProfile(final String testfield, final Person person, final String ownedBy) {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have already profile
        QueryDefault<Profile> query = 
                QueryDefault.create(
                        Profile.class, 
                    "findProfileByOwner", 
                    "ownedBy", ownedBy);
        return container.firstMatch(query) != null?
                true        
                :false;
    }
    
    @Programmatic // now values can be set by fixtures
    public String validateMakeProfile(final String testfield, final Person person, final String ownedBy) {
        QueryDefault<Profile> query = 
                QueryDefault.create(
                        Profile.class, 
                    "findProfileByOwner", 
                    "ownedBy", ownedBy);
        return container.firstMatch(query) != null?
                "You already have a profile"        
                :null;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private PersonRoles roles;

    @Inject
    private Profiles profiles;
    
    @Inject
    private PersonNeeds needs;

    
}
