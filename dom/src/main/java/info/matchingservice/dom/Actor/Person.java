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

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import java.util.SortedSet;
import java.util.TreeSet;



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
                    + "WHERE lastName.toLowerCase().indexOf(:lastName) >= 0"),
    @javax.jdo.annotations.Query(
            name = "matchPersonByNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Person "
                    + "WHERE lastName.toLowerCase().indexOf(:search) >= 0 || firstName.toLowerCase().indexOf(:search) >= 0"),
    @javax.jdo.annotations.Query(
            name = "findPersonByUniqueItemId", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Person "
                    + "WHERE uniqueItemId.matches(:uniqueItemId)")       
})
@DomainObject(editing=Editing.DISABLED, autoCompleteRepository=Persons.class, autoCompleteAction = "autoComplete")
public class Person extends Actor {


    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }
    
	//** API: PROPERTIES **//
    //Region> firstName /////////////////////////////////////////////////
    private String firstName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout()
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
    @PropertyLayout()
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
    @PropertyLayout()
    public String getLastName() {
        return lastName;
    }
        
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    //Region> dateOfBirth /////////////////////////////////////////////////
    private LocalDate dateOfBirth;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "60")
    @PropertyLayout()
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //region > PictureLink (property)
    private String pictureLink;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "70")
    @PropertyLayout()
    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(final String PictureLink) {
        this.pictureLink = PictureLink;
    }
    //endregion
    
    //Region> picture /////////////////////////////////////////////////
    private Blob picture;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "picture_name"),
            @javax.jdo.annotations.Column(name = "picture_mimetype"),
            @javax.jdo.annotations.Column(name = "picture_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    @Property(
    		optionality=Optionality.OPTIONAL
    )
    public Blob getPicture() {
        return picture;
    }

    public void setPicture(final Blob picture) {
        this.picture = picture;
    }
    
    //Region> roles /////////////////////////////////////////////////
    @PropertyLayout(multiLine=2)
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
	
	//-- API: PROPERTIES --//
    
    //** API: COLLECTIONS **//
    
    //** personalContacts **//
    private SortedSet<PersonalContact> collectPersonalContacts = new TreeSet<PersonalContact>();
    
    @Persistent(mappedBy = "ownerPerson", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<PersonalContact> getCollectPersonalContacts() {
        return collectPersonalContacts;
    }
    
    public void setCollectPersonalContacts(final SortedSet<PersonalContact> personalContacts) {
        this.collectPersonalContacts = personalContacts;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideCollectPersonalContacts(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- personalContacts --//
    
    //** personsReferringToActiveUser **//
//    @CollectionLayout(render=RenderType.EAGERLY)
//    public List<PersonalContact> getCollectPersonsReferringToActiveUser(){
//    	return pcontacts.allPersonalContactsReferringToBasicUser(currentUserName());
//    }
//    
//    // business rule:
//    // show only on person object of Active User
//    public boolean hideCollectPersonsReferringToActiveUser(){
//    	if (getOwnedBy().equals(currentUserName())){
//    		return false;
//    	} else {
//    		return true;
//    	}
//    }
    //-- personsReferringToActiveUser --//

    
    //** suppliesOfActor **//
    // Business rule:
    // - hide if not role student or professional
    public boolean hideCollectSupplies(){
        
        if (!(getIsStudent() || getIsProfessional() )){
            return true;
        }
        
        return false;
    }
    //-- suppliesOfActor --//
    
    //** demandsOfActor **//
    // Business rule:
    // Je moet minimaal INNERCIRCLE zijn om de demands te zien
    public boolean hideCollectDemands() { 
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- demandsOfActor --//
    
    //-- API: COLLECTIONS --//
    
    //** API: ACTIONS **//
    
    //** updatePerson **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Person updatePerson(
    		@ParameterLayout(named="firstName")
    		final String firstName,
    		@ParameterLayout(named="middleName")
    		@Parameter(optionality=Optionality.OPTIONAL)
    		final String middleName,
    		@ParameterLayout(named="lastName")
    		final String lastName,
    		@ParameterLayout(named="dateOfBirth")
    		final LocalDate dateOfBirth,
    		@ParameterLayout(named="picture")
    		@Parameter(optionality=Optionality.OPTIONAL)
    		final Blob picture,
            @ParameterLayout(named="pictureLink")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String pictureLink
    		){
    	persons.updatePerson(this, firstName, middleName, lastName, dateOfBirth, picture, pictureLink);
    	return this;
    }
    
    public String default0UpdatePerson(){
    	return getFirstName();
    }
    
    public String default1UpdatePerson(){
    	return getMiddleName();
    }
    
    public String default2UpdatePerson(){
    	return getLastName();
    }
    
    public LocalDate default3UpdatePerson(){
    	return getDateOfBirth();
    }
    
    public Blob default4UpdatePerson(){
    	return getPicture();
    }

    public String default5UpdatePerson(){
        return getPictureLink();
    }
    //-- updatePerson --//
    
    //** newPersonsSupplyAndProfile **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createPersonsSupplyAndProfile(){
        return createSupplyAndProfile("PERSON_SUPPLY_OF " + this.title(), 10, DemandSupplyType.PERSON_DEMANDSUPPLY, this, "PERSON_PROFILE_OF", 10, null, null, ProfileType.PERSON_PROFILE, currentUserName());
    }
    
    // Business rule: 
    // Je kunt alleen een Persoonprofiel aanmaken als je
    // - eigenaar bent
    // - rol Student of Professional hebt
    // - nog geen persoonssupply hebt
    public boolean hideCreatePersonsSupplyAndProfile(){
        return hideNewPersonsSupplyAndProfile("", this);
    }
    
    public String validateCreatePersonsSupplyAndProfile(){
        return validateNewPersonsSupplyAndProfile("", this);
    }
    //-- newPersonsSupplyAndProfile --//
    
    //** newPersonsDemand **//
    // Business rule: 
    // Je moet Opdrachtgever zijn om een tafel te starten
    @ActionLayout()
    public Demand createPersonsDemand(
            @ParameterLayout(named="demandDescription")
            final String demandDescription,
            @ParameterLayout(named="demandSummary", multiLine=3)
            @Parameter(optionality=Optionality.OPTIONAL)
            final String demandSummary,
            @ParameterLayout(named="demandStory", multiLine=8)
            @Parameter(optionality=Optionality.OPTIONAL)
            final String demandStory,
            @ParameterLayout(named="demandAttachment")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Blob demandAttachment,
            @ParameterLayout(named="demandOrSupplyProfileStartDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileStartDate,
            @ParameterLayout(named="demandOrSupplyProfileStartDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileEndDate
            ){
        return createDemand(demandDescription, demandSummary, demandStory, demandAttachment, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, 10, DemandSupplyType.PERSON_DEMANDSUPPLY, this, currentUserName());
    }
    
    public boolean hideCreatePersonsDemand(
    		final String demandDescription,
    		final String demandSummary,
    		final String demandStory,
    		final Blob demandAttachment,
    		final LocalDate demandOrSupplyProfileStartDate,
    		final LocalDate demandOrSupplyProfileEndDate
    		){
        return hideCreateDemand(demandDescription, this);
    }
    
    public String validateCreatePersonsDemand(
    		final String demandDescription,
    		final String demandSummary,
    		final String demandStory,
    		final Blob demandAttachment,
    		final LocalDate demandOrSupplyProfileStartDate,
    		final LocalDate demandOrSupplyProfileEndDate
    		){
        return validateNewDemand(demandDescription, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, this);
    }
    //-- newPersonsDemand --//
    
    //-- API: ACTIONS --//
    
    //** GENERIC OBJECT STUFF **//

    //-- GENERIC OBJECT STUFF --//
    
    //** HELPERS **//
    //** HELPERS: generic object helpers **//
    public String title() {
        if (getMiddleName()==null) {
            return this.getFirstName() + " " + this.getLastName();
        } else {
            return this.getFirstName() + " " + this.getMiddleName() + " " + this.getLastName();
        }
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    //-- HELPERS: generic object helpers --//
    
    //** HELPERS: programmatic actions **//
    @Programmatic
    public boolean hideNewPersonsSupplyAndProfile(final String needDescription, final Actor needOwner){
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
    public String validateNewPersonsSupplyAndProfile(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "NOT_THE_OWNER";
        }
        // if you have not Student or ZP Role
        if (!(((Person) needOwner).getIsStudent() || ((Person) needOwner).getIsProfessional())){
            return "NO_STUDENT_OR_PROFESSIONAL";
        }        
        // if there is already a personal Supply
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByOwnedByAndType", 
                    "ownedBy", currentUserName(),
                    "supplyType", DemandSupplyType.PERSON_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    
    @Programmatic
    public boolean hideCreateDemand(final String needDescription, final Actor needOwner){
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
    public String validateNewDemand
    	(
    		final String needDescription,
    		final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
    		final Actor needOwner
    	)
    {
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "NOT_THE_OWNER";
        }
        // if you have not Principal Role
        if (!((Person) needOwner).getIsPrincipal()){
            return "ONE_INSTANCE_AT_MOST";
        }
        
    	final LocalDate today = LocalDate.now();
    	if (demandOrSupplyProfileEndDate != null && demandOrSupplyProfileEndDate.isBefore(today))
    	{
    		return "ENDDATE_BEFORE_TODAY";
    	}
    	
    	if (
    			demandOrSupplyProfileEndDate != null 
    			
    			&& 
    			
    			demandOrSupplyProfileStartDate != null
    			
    			&&
    			
    			demandOrSupplyProfileEndDate.isBefore(demandOrSupplyProfileStartDate)
    			
    			)
    	{
    		return "ENDDATE_BEFORE_STARTDATE";
    	}
        
        return null;
    }
    
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
        roles.createRole(PersonRoleType.STUDENT, ownedBy);
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
        roles.createRole(PersonRoleType.PROFESSIONAL, ownedBy);
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
        roles.createRole(PersonRoleType.PRINCIPAL, ownedBy);
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

    
    //-- HELPERS: programmatic actions --//
    //-- HELPERS --//
    
    //** INJECTIONS **//
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private PersonRoles roles;
    
    @Inject
    PersonalContacts pcontacts;
    
    @Inject
    Persons persons;
    
    //-- INJECTIONS --//
    
    //** HIDDEN ACTIONS **//
    
    // Role STUDENT 'Clean' code. Makes use of helpers in Helpers region   
    @ActionLayout(hidden=Where.ANYWHERE)
    @MemberOrder(sequence = "40")
    public Person addRoleStudent() {
        addRoleStudent(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleStudent() {
        return hideAddRoleStudent(this, currentUserName());
    }
    
    @ActionLayout(hidden=Where.ANYWHERE)
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
    @ActionLayout(hidden=Where.ANYWHERE)
    @MemberOrder(sequence = "50")
    public Person addRoleProfessional() {
        addRoleProfessional(currentUserName());
        return this;
    }
    
    public boolean hideAddRoleProfessional() {
        return hideAddRoleProfessional(this, currentUserName());
    }
    
    @ActionLayout(hidden=Where.ANYWHERE)
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
    @ActionLayout(hidden=Where.ANYWHERE)
    @MemberOrder(sequence = "60")
    public Person addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }
    
    @ActionLayout(hidden=Where.ANYWHERE)
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
    
    //-- HIDDEN ACTIONS --//

}
