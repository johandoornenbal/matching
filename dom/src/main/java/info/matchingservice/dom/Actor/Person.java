package info.matchingservice.dom.Actor;

import info.matchingservice.dom.DemandSupply.DemandSupplyType;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
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
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
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
        
    //Region> DEMAND /////////////////////////////////////////////////////////////
    
    // method myDemands() is on Actor
    public boolean hideMyDemands() {
        return !getIsPrincipal();
    }
    
    // method newDemand() is on Actor
    public boolean hideNewDemand(final String needDescription, final Integer weight, final DemandSupplyType demandSupplyType) {
        return hideNewDemand(needDescription, this);
    }
    
    // Demand helpers
    @Programmatic
    public boolean hideNewDemand(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have not Principal Role
        if (!((Person) needOwner).getIsPrincipal()){
            return true;
        }
        
        return false;
    }   
    //END Region> DEMANDS /////////////////////////////////////////////////////////////
    

    ////PERSON CONTACTS EN REFERERS/////////////////////////////////////////////////////////////////////////////////
    
    private SortedSet<PersonalContact> personalContacts = new TreeSet<PersonalContact>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    @Named("Persoonlijke contacten")
    public SortedSet<PersonalContact> getPersonalContacts() {
        return personalContacts;
    }
    
    public void setPersonalContacts(final SortedSet<PersonalContact> personalContacts) {
        this.personalContacts = personalContacts;
    }
    
    @Render(Type.EAGERLY)
    @Named("Personen verwijzend naar mij")
    public List<Referral> getPersonsReferringToMe(){
        List<Referral> personsReferring = new ArrayList<Referral>();
        for(PersonalContact e: pcontacts.listAll()) {
            if (e.getContactPerson() == this){
                Referral referral = new Referral(e.getOwnerPerson(), e.getTrustLevel());
                personsReferring.add(referral);
            }
        }
        return personsReferring;
    }
        
    /////////////////////////////////////////////////////////////////////////////////////
    
    
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
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private PersonRoles roles;
    
    @Inject
    PersonalContacts pcontacts;

}
