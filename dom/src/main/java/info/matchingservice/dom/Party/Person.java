package info.matchingservice.dom.Party;

import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Testobjects.TestRelatedObject;
import info.matchingservice.dom.Testobjects.TestSecRelatedObject;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Party.Person "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastName", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Party.Person "
                    + "WHERE lastName.matches(:lastName)"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByLastNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Party.Person "
                    + "WHERE lastName.indexOf(:lastName) >= 0")                    
})
@AutoComplete(repository=Persons.class,  action="autoComplete")
public class Person extends Party {
    
    public String title() {
        return this.getLastName() + ", " + this.getFirstName() + " " + this.getMiddleName();
    }
    
    
    //Region> firstName /////////////////////////////////////////////////
    private String firstName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
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
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String ln) {
        this.lastName = ln;
    }

    //Region> ROLES /////////////////////////////////////////////////
    
    // Role STUDENT 'Clean' code. Makes use of helpers in Helpers region
    
    public Person addRoleStudent() {
        addRoleStudent(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleStudent() {
        return hideAddRoleStudent(this, currentUserName());
    }
    
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
    
    public Person addRoleProfessional() {
        addRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleProfessional() {
        return hideAddRoleProfessional(this, currentUserName());
    }
    
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
    
    public Person addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
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
    public List<Role> getAllMyRoles() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    @MultiLine(numberOfLines=2)
    public String getRoles() {
        TitleBuffer tb = new TitleBuffer();
        if (getIsStudent()) {
            tb.append(RoleType.STUDENT.title());
        }
        if (getIsProfessional()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(RoleType.PROFESSIONAL.title());
        }
        if (getIsPrincipal()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(RoleType.PRINCIPAL.title());
        }
        return tb.toString();
    }
    
    
    //END Region> ROLES /////////////////////////////////////////////////

    //Region> PROFILE /////////////////////////////////////////////////////////////
   
    private SortedSet<Profile> profile = new TreeSet<Profile>();
   
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileOwner", dependentElement = "true")
    public SortedSet<Profile> getProfile() {
        return profile;
    }
   
    public void setProfile(final SortedSet<Profile> profile) {
        this.profile = profile;
    }
   
    public Person makeProfile(final String testfield) {
        makeProfile(testfield, this, getOwnedBy());
        return this;
    }
   
    public boolean hideMakeProfile(final String testfield) {
        return hideMakeProfile(testfield, this, getOwnedBy());
    }
   
    public String validateMakeProfile(final String testfield) {
        return validateMakeProfile(testfield, this, getOwnedBy());
    }

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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", RoleType.STUDENT);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRoleStudent(String ownedBy) {
        roles.newRole(RoleType.STUDENT, ownedBy);
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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", RoleType.STUDENT);
        Role roleToDelete = container.firstMatch(query);
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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", RoleType.PROFESSIONAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRoleProfessional(String ownedBy) {
        roles.newRole(RoleType.PROFESSIONAL, ownedBy);
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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", RoleType.PROFESSIONAL);
        Role roleToDelete = container.firstMatch(query);
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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownerPerson.getOwnedBy(),
                        "role", RoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRolePrincipal(String ownedBy) {
        roles.newRole(RoleType.PRINCIPAL, ownedBy);
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
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", ownedBy,
                        "role", RoleType.PRINCIPAL);
        Role roleToDelete = container.firstMatch(query);
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
    public void makeProfile(final String testfield, final Person person, final String ownedBy) {
        profiles.newProfile(testfield, person, ownedBy);
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
    private Roles roles;

    @Inject
    private Profiles profiles;
}
