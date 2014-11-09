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
    
    // Role STUDENT
    
    public Person addRoleStudent() {
        addRoleStudent(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleStudent() {
        return hideAddRoleStudent(this, currentUserName());
    }
    
    //TODO: 'outfacture to helpers
    public Person deleteRoleStudent() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.STUDENT);
        Role roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
        return this;
    }
    
    public boolean hideDeleteRoleStudent() {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        return !getIsStudent();
    }
    
    @Hidden
    public Boolean getIsStudent() {
        return getIsStudent(this);
    }

    // Role PROFESSIONAL
    
    public Person addRoleProfessional() {
        roles.newRole(RoleType.PROFESSIONAL);
        return this;
    }
    
    public boolean hideAddRoleProfessional() {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        return getIsProfessional();
    }
    
    public Person deleteRoleProfessional() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.PROFESSIONAL);
        Role roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
        return this;
    }
    
    public boolean hideDeleteRoleProfessional() {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        return !getIsProfessional();
    }
    
    @Hidden
    public Boolean getIsProfessional() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.PROFESSIONAL);
        return !container.allMatches(query).isEmpty();
    }
    
    // Role PRINCIPAL
    
    public Person addRolePrincipal() {
        roles.newRole(RoleType.PRINCIPAL);
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        return getIsPrincipal();
    }
    
    public Person deleteRolePrincipal() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.PRINCIPAL);
        Role roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true);
        return this;
    }
    
    public boolean hideDeleteRolePrincipal() {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        return !getIsPrincipal();
    }
    
    @Hidden
    public Boolean getIsPrincipal() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
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
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
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
        return getIsStudent(ownerPerson); 
    }
    
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
