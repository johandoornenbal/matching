/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.joda.time.LocalDate;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;



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
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository=Persons.class, autoCompleteAction = "autoComplete")
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
    @PropertyLayout(named="Voornaam")
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
    @PropertyLayout(named="Tussen")
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
    @PropertyLayout(named="Achternaam")
    public String getLastName() {
        return lastName;
    }
        
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    //Region> dateOfBirth
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "60")
    @PropertyLayout(named = "Geboortedatum")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    private Blob picture;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "picture_name"),
            @javax.jdo.annotations.Column(name = "picture_mimetype"),
            @javax.jdo.annotations.Column(name = "picture_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    @Property(
            optional = Optionality.TRUE
    )
    public Blob getPicture() {
        return picture;
    }

    public void setPicture(final Blob picture) {
        this.picture = picture;
    }
    

    //Region> ROLES /////////////////////////////////////////////////
    
    // Role STUDENT 'Clean' code. Makes use of helpers in Helpers region
    
    @ActionLayout(named="Rol Student")
    @MemberOrder(sequence = "40")
    public Person addRoleStudent() {
        addRoleStudent(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleStudent() {
        return hideAddRoleStudent(this, currentUserName());
    }
    
    @ActionLayout(named="Geen student meer")
    @MemberOrder(sequence = "41")
    public Person deleteRoleStudent() {
        deleteRoleStudent(currentUserName());;
        return this;
    }
    
    public boolean hideDeleteRoleStudent() {
        return hideDeleteRoleStudent(this, currentUserName());
    }
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Boolean getIsStudent() {
        return getIsStudent(this);
    }

    // Role PROFESSIONAL 'Clean' code. Makes use of helpers in Helpers region
    
    @ActionLayout(named="Rol ZP-er")
    @MemberOrder(sequence = "50")
    public Person addRoleProfessional() {
        addRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleProfessional() {
        return hideAddRoleProfessional(this, currentUserName());
    }
    
    @ActionLayout(named="Geen ZP-er meer")
    @MemberOrder(sequence = "51")
    public Person deleteRoleProfessional() {
        deleteRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideDeleteRoleProfessional() {
        return hideDeleteRoleProfessional(this, currentUserName());
    }
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Boolean getIsProfessional() {
        return getIsProfessional(this);
    }
    
    // Role PRINCIPAL 'Clean' code. Makes use of helpers in Helpers region
    
    @ActionLayout(named="Rol Opdrachtgever")
    @MemberOrder(sequence = "60")
    public Person addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
    @ActionLayout(named="Geen opdrachtgever meer")
    @MemberOrder(sequence = "61")
    public Person deleteRolePrincipal() {
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
    public List<PersonRole> getAllMyRoles() {
        QueryDefault<PersonRole> query =
                QueryDefault.create(
                        PersonRole.class,
                        "findMyRoles",
                        "ownedBy", this.getOwnedBy());
        return container.allMatches(query);
    }
    
    @PropertyLayout(named="Rollen", multiLine=2)
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
    
    //Region> SUPPLIES /////////////////////////////////////////////////////////////   
    
    public Profile newPersonsSupply(){
        return newSupplyAndProfile("Persoonlijke profiel van " + this.title(), 10, DemandSupplyType.PERSON_DEMANDSUPPLY, this, "Mijn persoonlijke profiel", 10, ProfileType.PERSON_PROFILE, currentUserName());
    }
    
    //BUSINESS RULE
    // Je kunt alleen een Persoonprofiel aanmaken als je
    // - eigenaar bent
    // - rol Student of Professional hebt
    // - nog geen persoonssupply hebt
    public boolean hideNewPersonsSupply(){
        return hideNewPersonsSupply("", this);
    }
    
    public String validateNewPersonsSupply(){
        return validateNewPersonsSupply("", this);
    }
    
    public Supply newCourseSupply(
            @ParameterLayout(named="supplyDescription", multiLine=3)
            final String supplyDescription
            ){
        return newSupply(supplyDescription, 10, DemandSupplyType.COURSE_DEMANDSUPPLY, this, currentUserName());
    }
    
    public boolean hideNewCourseSupply(final String supplyDescription){
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have no ZP Role
        if (!((Person) this).getIsProfessional()){
            return true;
        } 
        
        return false;        
    }
    
    // Supply helpers
    //BUSINESS RULE
    // Je kunt alleen een Persoonprofiel aanmaken als je
    // - eigenaar bent
    // - rol Student of Professional hebt
    // - nog geen persoonssupply hebt
    @Programmatic
    public boolean hideNewPersonsSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have not Student or ZP Role
        if (!(((Person) needOwner).getIsStudent() || ((Person) needOwner).getIsProfessional())){
            return true;
        }        
        // if there is already a personal Supply
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByOwnedByAndType", 
                    "ownedBy", currentUserName(),
                    "supplyType", DemandSupplyType.PERSON_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    @Programmatic
    public String validateNewPersonsSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "Je bent niet de eigenaar";
        }
        // if you have not Student or ZP Role
        if (!(((Person) needOwner).getIsStudent() || ((Person) needOwner).getIsProfessional())){
            return "Je bent geen Student of Professional";
        }        
        // if there is already a personal Supply
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByOwnedByAndType", 
                    "ownedBy", currentUserName(),
                    "supplyType", DemandSupplyType.PERSON_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return "Je hebt al een persoonlijk profiel";
        }
        
        return null;
    }
    
    //BUSINESS RULE
    //Als je geen Student of Professional bent, toon dan geen supplies
    
    public boolean hideMySupplies(){
        
        if (!(getIsStudent() || getIsProfessional() )){
            return true;
        }
        
        return false;
    }
    
    //END Region> SUPPLIES /////////////////////////////////////////////////////////////    
        
    //Region> DEMAND /////////////////////////////////////////////////////////////
    
    // method myDemands() is on Actor
    //BUSINESS RULE
    // Je moet minimaal INNERCIRCLE zijn om de demands te zien
    public boolean hideMyDemands() { 
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    
    // method newDemand() is on Actor
    public boolean hideNewDemand(final String needDescription, final Integer weight, final DemandSupplyType demandSupplyType) {
        return hideNewDemand(needDescription, this);
    }
    
    //XTALUS
    //BUSINESS RULE
    // Je moet Opdrachtgever zijn om een tafel te starten
    @ActionLayout(named="Start nieuwe tafel")
    public Demand newPersonsDemand(
            @ParameterLayout(named="demandDescription")
            final String demandDescription,
            @ParameterLayout(named="demandSummary", multiLine=3)
            @Parameter(optional=Optionality.TRUE)
            final String demandSummary,
            @ParameterLayout(named="demandStory", multiLine=8)
            @Parameter(optional=Optionality.TRUE)
            final String demandStory,
            @ParameterLayout(named="demandAttachment")
            @Parameter(optional=Optionality.TRUE)
            final Blob demandAttachment
            ){
        return newDemand(demandDescription, demandSummary, demandStory, demandAttachment, 10, DemandSupplyType.PERSON_DEMANDSUPPLY, this, currentUserName());
    }
    
    public boolean hideNewPersonsDemand(
    		final String demandDescription,
    		final String demandSummary,
    		final String demandStory,
    		final Blob demandAttachment
    		){
        return hideNewDemand(demandDescription, this);
    }
    
    public String validateNewPersonsDemand(
    		final String demandDescription,
    		final String demandSummary,
    		final String demandStory,
    		final Blob demandAttachment
    		){
        return validateNewDemand(demandDescription, this);
    }
    
    //XTALUS
    //Business Rule
    //Er is slecht een demand van type Cursus
    @ActionLayout(named="Zoek een cursus")
    public Profile newCourseDemand(
            @ParameterLayout(named="demandProfileDescription")
            final String demandProfileDescription
            ){
        return newDemandAndProfile("Gezochte cursussen", 10, DemandSupplyType.COURSE_DEMANDSUPPLY, this, demandProfileDescription, 10, ProfileType.COURSE_PROFILE, currentUserName());
    }
    
    public boolean hideNewCourseDemand(
            final String demandProfileDescription
            ){
        // if there is already a personal Supply
        QueryDefault<Demand> query = 
                QueryDefault.create(
                        Demand.class, 
                    "findDemandByOwnedByAndType", 
                    "ownedBy", currentUserName(),
                    "demandType", DemandSupplyType.COURSE_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateNewCourseDemand(
            final String demandProfileDescription
            ){
        // if there is already a personal Supply
        QueryDefault<Demand> query = 
                QueryDefault.create(
                        Demand.class, 
                    "findDemandByOwnedByAndType", 
                    "ownedBy", currentUserName(),
                    "demandType", DemandSupplyType.COURSE_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return "Er is al een vraag van type CURSUS";
        }
        
        return null;
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
    
    @Programmatic
    public String validateNewDemand(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "Je bent geen eigenaar";
        }
        // if you have not Principal Role
        if (!((Person) needOwner).getIsPrincipal()){
            return "Je bent geen opdachtgever";
        }
        
        return null;
    }   
    //END Region> DEMANDS /////////////////////////////////////////////////////////////
    

    ////PERSON CONTACTS EN REFERERS/////////////////////////////////////////////////////////////////////////////////
    
    private SortedSet<PersonalContact> personalContacts = new TreeSet<PersonalContact>();
    
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    @CollectionLayout(named="Persoonlijke contacten", render=RenderType.EAGERLY)
    public SortedSet<PersonalContact> getPersonalContacts() {
        return personalContacts;
    }
    
    public void setPersonalContacts(final SortedSet<PersonalContact> personalContacts) {
        this.personalContacts = personalContacts;
    }
    
    public boolean hidePersonalContacts(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  
    
    @CollectionLayout(named="Personen verwijzend naar mij", render=RenderType.EAGERLY)
    @NotContributed(As.ACTION)
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
    
    public boolean hidePersonsReferringToMe(){
        return super.allowedTrustLevel(TrustLevel.INTIMATE);
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
    
    // EDIT ACTION
    public Person editPerson(
    		@ParameterLayout(named="firstName")
    		final String firstName,
    		@ParameterLayout(named="middleName")
    		@Parameter(optional=Optionality.TRUE)
    		final String middleName,
    		@ParameterLayout(named="lastName")
    		final String lastName,
    		@ParameterLayout(named="dateOfBirth")
    		final LocalDate dateOfBirth,
    		@ParameterLayout(named="picture")
    		final Blob picture
    		){
    	persons.EditPerson(this, firstName, middleName, lastName, dateOfBirth, picture);
    	return this;
    }
    
    public String default0EditPerson(){
    	return getFirstName();
    }
    
    public String default1EditPerson(){
    	return getMiddleName();
    }
    
    public String default2EditPerson(){
    	return getLastName();
    }
    
    public LocalDate default3EditPerson(){
    	return getDateOfBirth();
    }
    
    public Blob default4EditPerson(){
    	return getPicture();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private PersonRoles roles;
    
    @Inject
    PersonalContacts pcontacts;
    
    @Inject
    Persons persons;
}
