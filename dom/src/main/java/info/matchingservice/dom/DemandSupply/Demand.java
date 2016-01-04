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

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.DemandFeedback;
import info.matchingservice.dom.HasImageUrl;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.*;
import java.util.SortedSet;
import java.util.TreeSet;


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
                    + "WHERE ownedBy == :ownedBy && description.indexOf(:description) >= 0"),
    @javax.jdo.annotations.Query(
            name = "findDemandByDescriptionOnly", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Demand "
                    + "WHERE description.toLowerCase().indexOf(:description) >= 0")
})
@DomainObject(editing=Editing.DISABLED)
public class Demand extends MatchingSecureMutableObject<Demand> implements HasImageUrl {

    public Demand() {
        super("ownedBy, description, timeStamp");
    }




    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }

    //region > date (property)
    private LocalDateTime timeStamp;

    @Column(allowsNull = "false")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    //endregion

	//** API: PROPERTIES **//


    //** owner **//
    private Actor owner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Actor getOwner() {
        return owner;
    }
    
    public void setOwner(final Actor needOwner) {
        this.owner = needOwner;
    }
    //-- owner --//
    
    //** description **//
    private String description;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getDescription(){
        return description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    //-- description --//
    
    //** summary **//
    private String summary;
    
    @javax.jdo.annotations.Column(allowsNull = "true", length=1024)
    @PropertyLayout(multiLine=4)
    @Property(maxLength=1024)
    public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	//-- summary --//

	//** story **//
	private String story;
    
	@javax.jdo.annotations.Column(allowsNull = "true", length=2048)
    @PropertyLayout(multiLine=8)
	@Property(maxLength=2048)
	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}
	//-- story--//
	
	//** attachment **//
	private Blob attachment;
	
    @javax.jdo.annotations.Persistent(defaultFetchGroup="false", columns = {
            @javax.jdo.annotations.Column(name = "attachment_name"),
            @javax.jdo.annotations.Column(name = "attachment_mimetype"),
            @javax.jdo.annotations.Column(name = "attachment_bytes", jdbcType = "BLOB", sqlType = "BLOB")
    })
    @Property(
    		optionality=Optionality.OPTIONAL
    )
	public Blob getAttachment(){
		return attachment;
	}
	
	public void setAttachment(Blob attachment) {
		this.attachment = attachment;
	}
	//-- attachment --//
	
	//** startDate **//
	private LocalDate startDate;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(final LocalDate startDate){
		this.startDate = startDate;
	}	
	//-- startDate --//
	
	//** endDate **//
	private LocalDate endDate;
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(final LocalDate endDate){
		this.endDate = endDate;
	}	
	//-- startDate --//

    //region > imageUrl (property)
    private String imageUrl;

    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion
    
	//-- API: PROPERTIES --//
	
	//** API: COLLECTIONS **//
	
	//** profiles **//
    private SortedSet<Profile> profiles = new TreeSet<Profile>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demand", dependentElement = "true")
    public SortedSet<Profile> getProfiles() {
        return profiles;
    }
    
    public void setProfiles(final SortedSet<Profile> profiles){
        this.profiles = profiles;
    }
    //-- profiles --//
    
    //** demandAssessments **//
    private SortedSet<DemandFeedback> assessments = new TreeSet<DemandFeedback>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<DemandFeedback> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<DemandFeedback> demandAssessment) {
        this.assessments = demandAssessment;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  
    //-- demandAssessments --//
    
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
	
	//** updateDemand **//
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Demand updateDemand(
            @ParameterLayout(named="description")
            final String demandDescription,
            @ParameterLayout(named="summary", multiLine=3)
            @Parameter(optionality=Optionality.OPTIONAL)
            final String demandSummary,
            @ParameterLayout(named="story", multiLine=8)
            @Parameter(optionality=Optionality.OPTIONAL)
            final String demandStory,
            @ParameterLayout(named="attachment")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Blob demandAttachment,
            @ParameterLayout(named="startDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileStartDate,
            @ParameterLayout(named="endDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            final LocalDate demandOrSupplyProfileEndDate,
            @ParameterLayout(named="imageUrl")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String imageUrl
            ){
        this.setDescription(demandDescription);
        this.setSummary(demandSummary);
        this.setStory(demandStory);
        this.setAttachment(demandAttachment);
        this.setStartDate(demandOrSupplyProfileStartDate);
        this.setEndDate(demandOrSupplyProfileEndDate);
        this.setImageUrl(imageUrl);
        return this;
    }
    
    public String default0UpdateDemand(){
        return this.getDescription();
    }
    
    public String default1UpdateDemand(){
        return this.getSummary();
    }
    
    public String default2UpdateDemand(){
        return this.getStory();
    }
    
    public Blob default3UpdateDemand(){
        return this.getAttachment();
    }
    
    public LocalDate default4UpdateDemand(){
        return this.getStartDate();
    }
    
    public LocalDate default5UpdateDemand(){
        return this.getEndDate();
    }

    public String default6UpdateDemand(){
        return this.getImageUrl();
    }
    
    public String validateUpdateDemand(
    		final String demandDescription,
            final String demandSummary,
            final String demandStory,
            final Blob demandAttachment,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final String imageUrl
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
        return getOwner();
    }
    
    public String validateDeleteDemand(boolean confirmDelete) {
    	// test if there are any profilesRepo on this demand
    	if (!profilesRepo.findProfileByDemandProfileOwner(this).isEmpty()) {
    		
    		return "DELETE_ALL_PROFILES_FIRST";
    	}
    	
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
        		this.getStartDate(),
        		this.getEndDate(),
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
        return getDescription() + " - " + getOwner().title();
    }

	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getOwner();
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
        return profilesRepo.createDemandProfile(demandProfileDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, profileType, null, demandProfileOwner, ownedBy);
    }
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
	@javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles profilesRepo;
    
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

}
