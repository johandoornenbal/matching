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

package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.SupplyAssessment;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findSupplyByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE ownedBy == :ownedBy && supplyType == :supplyType"),
    @javax.jdo.annotations.Query(
            name = "findSupplyByActorOwnerAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE supplyOwner == :supplyOwner && supplyType == :supplyType"),
    @javax.jdo.annotations.Query(
            name = "findSupplyByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE ownedBy == :ownedBy && supplyDescription.indexOf(:supplyDescription) >= 0"),
    @javax.jdo.annotations.Query(
            name = "findSupplyByUniqueItemId", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE uniqueItemId.matches(:uniqueItemId)")  
})
@DomainObject(editing=Editing.DISABLED)
public class Supply extends MatchingSecureMutableObject<Supply> {

	//** API: PROPERTIES **//
	
	//** uniqueItemId **//
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//
    
    //** supplyOwner **//
    private Actor supplyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Actor getSupplyOwner() {
        return supplyOwner;
    }
    
    public void setSupplyOwner(final Actor supplyOwner) {
        this.supplyOwner = supplyOwner;
    }
    //-- supplyOwner --//
    
    //** supplyDescription **//
    private String supplyDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(multiLine=4)
    public String getSupplyDescription(){
        return supplyDescription;
    }
    
    public void setSupplyDescription(final String description) {
        this.supplyDescription = description;
    }
    //-- supplyDescription --//
    
	//** demandOrSupplyProfileStartDate **//
	private LocalDate demandOrSupplyProfileStartDate;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getDemandOrSupplyProfileStartDate() {
		return demandOrSupplyProfileStartDate;
	}
	
	public void setDemandOrSupplyProfileStartDate(final LocalDate demandOrSupplyProfileStartDate){
		this.demandOrSupplyProfileStartDate = demandOrSupplyProfileStartDate;
	}	
	//-- demandOrSupplyProfileStartDate --//
	
	//** demandOrSupplyProfileEndDate **//	
	private LocalDate demandOrSupplyProfileEndDate;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getDemandOrSupplyProfileEndDate() {
		return demandOrSupplyProfileEndDate;
	}
	
	public void setDemandOrSupplyProfileEndDate(final LocalDate demandOrSupplyProfileEndDate){
		this.demandOrSupplyProfileEndDate = demandOrSupplyProfileEndDate;
	}	
	//-- demandOrSupplyProfileStartDate --//
    
	//-- API: PROPERTIES --//
	//** API: COLLECTIONS **//
    
    //** supplyProfiles **//
    private SortedSet<Profile> collectSupplyProfiles = new TreeSet<Profile>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "supplyProfileOwner", dependentElement = "true")
    public SortedSet<Profile> getCollectSupplyProfiles() {
        return collectSupplyProfiles;
    }
    
    public void setCollectSupplyProfiles(final SortedSet<Profile> supplyProfile){
        this.collectSupplyProfiles = supplyProfile;
    }
    //-- supplyProfiles --//
    
    //** supplyAssessments **//
    private SortedSet<SupplyAssessment> collectSupplyAssessments = new TreeSet<SupplyAssessment>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<SupplyAssessment> getCollectSupplyAssessments() {
        return collectSupplyAssessments;
    }
   
    public void setCollectSupplyAssessments(final SortedSet<SupplyAssessment> assessment) {
        this.collectSupplyAssessments = assessment;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideCollectSupplyAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- supplyAssessments --//
    
	//-- API: COLLECTIONS --//
	//** API: ACTIONS **//
    
    //** updateSupply **//
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Supply updateSupply(
            @ParameterLayout(named="supplyDescription", multiLine=4)
            final String supplyDescription,
            @ParameterLayout(named="demandOrSupplyProfileStartDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileStartDate,
            @ParameterLayout(named="demandOrSupplyProfileEndDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileEndDate
            ){
        this.setSupplyDescription(supplyDescription);
        this.setDemandOrSupplyProfileStartDate(demandOrSupplyProfileStartDate);
        this.setDemandOrSupplyProfileEndDate(demandOrSupplyProfileEndDate);
        return this;
    }
    
    public String default0UpdateSupply(){
        return this.getSupplyDescription();
    }
    
    public LocalDate default1UpdateSupply(){
        return this.getDemandOrSupplyProfileStartDate();
    }
    
    public LocalDate default2UpdateSupply(){
        return this.getDemandOrSupplyProfileEndDate();
    }
    
    public String validateUpdateSupply(
            final String supplyDescription,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate
            )
     {
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
    
    //-- updateSupply --//
    
    //** deleteSupply **//
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Actor deleteSupply(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Supply deleted");
        return getSupplyOwner();
    }
    
    public String validateDeleteSupply(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    //-- deleteSupply --//
    
    //** createPersonSupplyProfile **//
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createPersonSupplyProfile(){
        return createSupplyProfile("PERSON_PROFILE_OF " + this.getSupplyOwner().title(), 10, null, null, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
    
    // Business rule:
    // je kunt slechts een 'persoonlijk profiel' hebben (supplyType PERSONS_DEMANDSUPPLY)
    // alleen tonen op supply van type PERSONS
    // je kunt alleen een persoonlijk profiel aanmaken als je student of ZP-er bent.
    
     public boolean hideCreatePersonSupplyProfile(){
               QueryDefault<Profile> query = 
               QueryDefault.create(
                       Profile.class, 
                   "allSupplyProfilesOfTypeByOwner", 
                   "ownedBy", currentUserName(),
                   "profileType", ProfileType.PERSON_PROFILE);
         if (container.firstMatch(query) != null) {
           return true;
         }
         
         if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
             return true;
         }
         
         if (!(((Person) getSupplyOwner()).getIsStudent() || ((Person) getSupplyOwner()).getIsProfessional())){
             return true;
         }
         
         return false;
     }
     
     public String validateCreatePersonSupplyProfile(){
             QueryDefault<Profile> query = 
             QueryDefault.create(
                     Profile.class, 
                 "allSupplyProfilesOfTypeByOwner", 
                 "ownedBy", currentUserName(),
                 "profileType", ProfileType.PERSON_PROFILE);
           if (container.firstMatch(query) != null) {
               return "ONE_INSTANCE_AT_MOST";
           }
           
           if (!(((Person) getSupplyOwner()).getIsStudent() || ((Person) getSupplyOwner()).getIsProfessional())){
               return "NO_STUDENT_OR_PROFESSIONAL";
           }
           
           if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
               return "ONLY_ON_PERSON_SUPPLY";
           }
           
           return null;
           
           
     }
     //-- createPersonSupplyProfile --//
    
    
	//-- API: ACTIONS --//
	//** GENERIC OBJECT STUFF **//
	//** constructor **//
    public Supply() {
        super("supplyDescription, weight, ownedBy, uniqueItemId");
    }
	//** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
	//-- GENERIC OBJECT STUFF --//
	//** HELPERS **//
    //** HELPERS: generic object helpers **//
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getSupplyDescription() + " - " + getSupplyOwner().title();
    }
	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getSupplyOwner();
    }
    
    @Programmatic
    public Profile createSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final Supply supplyProfileOwner, 
            final String ownedBy) {
        return allSupplyProfiles.createSupplyProfile(supplyProfileDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, profileType, supplyProfileOwner, ownedBy);
    }
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles allSupplyProfiles;
	//-- INJECTIONS --//
	//** HIDDEN: PROPERTIES **//
    
    //** supplyType **//
    private DemandSupplyType supplyType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public DemandSupplyType getSupplyType(){
        return supplyType;
    }
    
    public void setSupplyType(final DemandSupplyType supplyType){
        this.supplyType = supplyType;
    }
    //-- supplyType --//
    
    //** weight **//
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    //-- weight --//
    
	//-- HIDDEN: PROPERTIES --//
	//** HIDDEN: ACTIONS **//
    @ActionLayout(hidden=Where.EVERYWHERE)
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Supply updateWeight(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        this.setWeight(weight);
        return this;
    }
    
    public Integer default0UpdateWeight(){
        return this.getWeight();
    }
	//-- HIDDEN: ACTIONS --//

    
//    @ActionLayout(hidden=Where.EVERYWHERE)
//    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
//    public Profile createSupplyProfile(
//            final String supplyProfileDescription,
//            final Integer weight
//            ) {
//        return createSupplyProfile(supplyProfileDescription, weight, null, null, ProfileType.PERSON_PROFILE, this, currentUserName());
//    }
   

    
//    @ActionLayout(named="Nieuwe cursus", hidden=Where.EVERYWHERE)
//    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
//    public Profile createCourseSupplyProfile(
//            @ParameterLayout(named="profileName")
//            final String supplyProfileDescription
//            ) {
//        return createSupplyProfile(supplyProfileDescription, 10, null, null, ProfileType.COURSE_PROFILE, this, currentUserName());
//    }
//    
//    // BUSINESS RULE voor hide en validate van de aktie 'nieuw cursus profiel'
//    // alleen tonen op supply van type cursus
//    // je kunt alleen een cursus profiel aanmaken als je ZP-er bent.
//    
//    public boolean hideCreateCourseSupplyProfile(
//            final String supplyProfileDescription
//            ) {
//        if (this.getSupplyType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
//            return true;
//        }
//        
//        if (!((Person) getSupplyOwner()).getIsProfessional()){
//            return true;
//        }
//        
//        return false;
//    }
//    
//    public String validateCreateCourseSupplyProfile(
//            final String supplyProfileDescription
//            ) {
//        if (this.getSupplyType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
//            return "Kan alleen op type Cursus";
//        }
//        
//        if (!((Person) getSupplyOwner()).getIsProfessional()){
//            return "Je moet ZP-er zijn";
//        }
//        
//        return null;
//    }

}
