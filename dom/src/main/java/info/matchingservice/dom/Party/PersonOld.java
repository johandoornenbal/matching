package info.matchingservice.dom.Party;

import info.matchingservice.dom.Testobjects.TestRelatedObject;
import info.matchingservice.dom.Testobjects.TestSecRelatedObject;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;

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
public class PersonOld extends Party {
    
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
    // At this moment a person can have 3 roles at most
    // Having a attrib role is obligated; second and third are optional
    // Roles are only displayed when having them
    // Roles can only be set/unset by actions
    // The first Role can also be set by methods like newStudent etc in Persons...
    
    private RoleType role;
    
    @MemberOrder(sequence = "40")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public RoleType getRole() {
        return role;
    }
    
    public void setRole(final RoleType role) {
        this.role = role;
    }
    
    public boolean hideRole() {
        return this.getRole() == null;
    }
    
    private RoleType secondRole;
    
    @MemberOrder(sequence = "50")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    public RoleType getSecondRole() {
        return secondRole;
    }
    
    public void setSecondRole(final RoleType role) {
        this.secondRole = role;
    }
    
    public boolean hideSecondRole() {
        return this.getSecondRole() == null;
    }
    
    public PersonOld addSecondRole(final RoleType role) {
        setSecondRole(role);
        return this;
    }
    
    public boolean hideAddSecondRole(final RoleType role){
        return this.getSecondRole() != null;
    }
    
    public PersonOld deleteSecondRole(){
        setSecondRole(null);
        return this;
    }
    
    public boolean hideDeleteSecondRole(){
        return this.getSecondRole() == null;
    }
    
    private RoleType thirdRole;
    
    @MemberOrder(sequence = "60")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    public RoleType getThirdRole() {
        return thirdRole;
    }
    
    public void setThirdRole(final RoleType role) {
        this.thirdRole = role;
    }
    
    public boolean hideThirdRole() {
        return this.getThirdRole() == null;
    }
    
    public PersonOld addThirdRole(final RoleType role) {
        setThirdRole(role);
        return this;
    }
    
    public boolean hideAddThirdRole(final RoleType role){
        return this.getSecondRole() == null || (this.getSecondRole() != null && this.getThirdRole() !=null);
    }
    
    public PersonOld deleteThirdRole(){
        setThirdRole(null);
        return this;
    }
    
    public boolean hideDeleteThirdRole(){
        return this.getThirdRole() == null;
    }
    
    public Boolean getIsStudent() {
        QueryDefault<Role> query =
                QueryDefault.create(
                        Role.class,
                        "findSpecificRole",
                        "ownedBy", this.getOwnedBy(),
                        "role", RoleType.STUDENT);
        return !container.allMatches(query).isEmpty();
    }
    
    
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
    
    
    //END Region> ROLES /////////////////////////////////////////////////


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
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
