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
import info.matchingservice.dom.Assessment.DemandAssessment;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

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
import org.apache.isis.applib.value.Blob;
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
            name = "findDemandByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Demand "
                    + "WHERE ownedBy == :ownedBy && demandType == :demandType"),
    @javax.jdo.annotations.Query(
            name = "findDemandByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Demand "
                    + "WHERE ownedBy == :ownedBy && demandDescription.indexOf(:demandDescription) >= 0")                    
})
@DomainObject(editing=Editing.DISABLED)
public class Demand extends MatchingSecureMutableObject<Demand> {
	
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
    
    //** demandOwner **//
    private Actor demandOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Actor getDemandOwner() {
        return demandOwner;
    }
    
    public void setDemandOwner(final Actor needOwner) {
        this.demandOwner = needOwner;
    }
    //-- demandOwner --//
    
    //** demandDescription **//
    private String demandDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getDemandDescription(){
        return demandDescription;
    }
    
    public void setDemandDescription(final String description) {
        this.demandDescription = description;
    }
    //-- demandDescription --//
    
    //** demandSummary **//
    private String demandSummary;
    
    @javax.jdo.annotations.Column(allowsNull = "true", length=1024)
    @PropertyLayout(multiLine=4)
    @Property(maxLength=1024)
    public String getDemandSummary() {
		return demandSummary;
	}

	public void setDemandSummary(String demandSummary) {
		this.demandSummary = demandSummary;
	}
	//-- demandSummary --//

	//** demandStory **//
	private String demandStory;
    
	@javax.jdo.annotations.Column(allowsNull = "true", length=2048)
    @PropertyLayout(multiLine=8)
	@Property(maxLength=2048)
	public String getDemandStory() {
		return demandStory;
	}

	public void setDemandStory(String demandStory) {
		this.demandStory = demandStory;
	}
	//-- demandStory--//
	
	//** demandAttachment **//
	private Blob demandAttachment;
	
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "demandAttachment_name"),
            @javax.jdo.annotations.Column(name = "demandAttachment_mimetype"),
            @javax.jdo.annotations.Column(name = "demandAttachment_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    @Property(
    		optionality=Optionality.OPTIONAL
    )
	public Blob getDemandAttachment(){
		return demandAttachment;
	}
	
	public void setDemandAttachment(Blob demandAttachment) {
		this.demandAttachment = demandAttachment;
	}
	//-- demandAttachment --//
	
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
	
	//** demandProfiles **//
    private SortedSet<Profile> collectDemandProfiles = new TreeSet<Profile>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandProfileOwner", dependentElement = "true")
    public SortedSet<Profile> getCollectDemandProfiles() {
        return collectDemandProfiles;
    }
    
    public void setCollectDemandProfiles(final SortedSet<Profile> vac){
        this.collectDemandProfiles = vac;
    }
    //-- demandProfiles --//
    
    //** demandAssessments **//
    private SortedSet<DemandAssessment> collectDemandAssessments = new TreeSet<DemandAssessment>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<DemandAssessment> getCollectDemandAssessments() {
        return collectDemandAssessments;
    }
   
    public void setCollectDemandAssessments(final SortedSet<DemandAssessment> demandAssessment) {
        this.collectDemandAssessments = demandAssessment;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideCollectDemandAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  
    //-- demandAssessments --//
    
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
	
	//** updateDemand **//
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Demand updateDemand(
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
            @ParameterLayout(named="demandOrSupplyProfileEndDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileEndDate
            ){
        this.setDemandDescription(demandDescription);
        this.setDemandSummary(demandSummary);
        this.setDemandStory(demandStory);
        this.setDemandAttachment(demandAttachment);
        this.setDemandOrSupplyProfileStartDate(demandOrSupplyProfileStartDate);
        this.setDemandOrSupplyProfileEndDate(demandOrSupplyProfileEndDate);
        return this;
    }
    
    public String default0UpdateDemand(){
        return this.getDemandDescription();
    }
    
    public String default1UpdateDemand(){
        return this.getDemandSummary();
    }
    
    public String default2UpdateDemand(){
        return this.getDemandStory();
    }
    
    public Blob default3UpdateDemand(){
        return this.getDemandAttachment();
    }
    
    public LocalDate default4UpdateDemand(){
        return this.getDemandOrSupplyProfileStartDate();
    }
    
    public LocalDate default5UpdateDemand(){
        return this.getDemandOrSupplyProfileEndDate();
    }
    
    public String validateUpdateDemand(
    		final String demandDescription,
            final String demandSummary,
            final String demandStory,
            final Blob demandAttachment,
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
    //-- updateDemand --//
    
    //** deleteDemand **//
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Actor deleteDemand(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        container.informUser("Demand deleted");
        return getDemandOwner();
    }
    
    public String validateDeleteDemand(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    //-- DeleteDemand --//
    
    //** createPersonDemandProfile **//
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createPersonDemandProfile(
            @ParameterLayout(named="profileName")
            final  String demandProfileDescription
            ){
        return createDemandProfile(
        		demandProfileDescription, 
        		10, 
        		this.getDemandOrSupplyProfileStartDate(), 
        		this.getDemandOrSupplyProfileEndDate(), 
        		ProfileType.PERSON_PROFILE, 
        		this,
        		profileTypeMatchingRules.findProfileTypeMatchingRule("regel1"),
        		currentUserName()
        		);
    }
    
    // Business rule: 
    // alleen tonen op demand van type persoon
    
    public boolean hideCreatePersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return true;
        }
        
        return false;
    }
    
    public String validateCreatePersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return "ONLY_ON_PERSON_DEMAND";
        }
        
        return null;
    }
    //-- createPersonDemandProfile --//
    
	//-- API: ACTIONS --//
	//** GENERIC OBJECT STUFF **//
	
	//** constructor **//
    public Demand() {
        super("demandDescription, weight, ownedBy, uniqueItemId");
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
        return getDemandDescription() + " - " + getDemandOwner().title();
    }

	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getDemandOwner();
    }
    
    @Programmatic
    public Profile createDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final Demand demandProfileOwner,
            final ProfileTypeMatchingRule profileTypeMatchingRule,
            final String ownedBy) {
        return profiles.createDemandProfile(demandProfileDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, profileType, demandProfileOwner, profileTypeMatchingRule, ownedBy);
    }
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
	@javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles profiles;
    
    @Inject
    ProfileTypeMatchingRules profileTypeMatchingRules;
    
    

	//-- INJECTIONS --//
    //** HIDDEN: PROPERTIES **//
    
    //** demandType **//
    private DemandSupplyType demandType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public DemandSupplyType getDemandType(){
        return demandType;
    }
    
    public void setDemandType(final DemandSupplyType demandType){
        this.demandType = demandType;
    }
    //-- demandType --//
    
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
    public Demand updateWeight(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        this.setWeight(weight);
        return this;
    }
    
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Integer default0UpdateWeight(){
        return this.getWeight();
    }
    
  	//-- HIDDEN: ACTIONS --//
    
    
    //XTALUS 
    //Nieuwe cursus gezocht
//    @ActionLayout(named="Nieuwe cursus zoeken", hidden=Where.ANYWHERE)
//    public Profile newCourseDemandProfile(
//            @ParameterLayout(named="profileName", multiLine=4)
//            final  String demandProfileDescription
//            ){
//        return createDemandProfile(demandProfileDescription, 10, null, null, ProfileType.COURSE_PROFILE, this, currentUserName());
//    }
//    
//    // BUSINESS RULE voor hide en validate van de aktie 'nieuw cursus gezocht'
//    // alleen tonen op demand van type cursus
//    
//    public boolean hideNewCourseDemandProfile(
//            final  String demandProfileDescription
//            ){
//        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
//            return true;
//        }
//        
//        return false;
//    }
//    
//    public String validateNewCourseDemandProfile(
//            final  String demandProfileDescription
//            ){
//        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
//            return "Alleen op type CURSUS";
//        }
//        
//        return null;
//    }    

    //XTALUS 
    //Nieuwe persoon gezocht

    
//    @ActionLayout(hidden=Where.EVERYWHERE)
//    public Profile newDemandProfile(
//            final  String demandProfileDescription,
//            final Integer weight 
//            ) {
//        return createDemandProfile(demandProfileDescription, weight, null, null, ProfileType.PERSON_PROFILE, this, currentUserName());
//    }

}
