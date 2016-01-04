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
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.SupplyFeedback;
import info.matchingservice.dom.HasImageUrl;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;

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
            name = "findSupplyByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE ownedBy == :ownedBy && supplyType == :supplyType"),
    @javax.jdo.annotations.Query(
            name = "findSupplyByActorOwnerAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE owner == :owner && supplyType == :supplyType"),
    @javax.jdo.annotations.Query(
            name = "findSupplyByDescription", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE ownedBy == :ownedBy && description.indexOf(:description) >= 0")
})
@DomainObject(editing=Editing.DISABLED)
public class Supply extends MatchingSecureMutableObject<Supply> implements HasImageUrl {



    public Supply() {
        super("owner, supplyType, description, weight, startDate, endDate, ownedBy");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }

    //** owner **//
    private Actor owner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout()
    public Actor getOwner() {
        return owner;
    }
    
    public void setOwner(final Actor owner) {
        this.owner = owner;
    }
    //-- owner --//
    
    //** description **//
    private String description;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(multiLine=4)
    public String getDescription(){
        return description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    //-- description --//
    
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

    @Override
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion

    //** supplyProfiles **//
    private SortedSet<Profile> profiles = new TreeSet<Profile>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "supply", dependentElement = "true")
    public SortedSet<Profile> getProfiles() {
        return profiles;
    }
    
    public void setProfiles(final SortedSet<Profile> supplyProfile){
        this.profiles = supplyProfile;
    }
    //-- supplyProfiles --//
    
    //** supplyAssessments **//
    private SortedSet<SupplyFeedback> assessments = new TreeSet<SupplyFeedback>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<SupplyFeedback> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<SupplyFeedback> assessment) {
        this.assessments = assessment;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- supplyAssessments --//
    

    
    //** updateSupply **//
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public Supply updateSupply(
            @ParameterLayout(named="description", multiLine=4)
            final String supplyDescription,
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
        this.setDescription(supplyDescription);
        this.setStartDate(demandOrSupplyProfileStartDate);
        this.setEndDate(demandOrSupplyProfileEndDate);
        this.setImageUrl(imageUrl);
        return this;
    }
    
    public String default0UpdateSupply(){
        return this.getDescription();
    }
    
    public LocalDate default1UpdateSupply(){
        return this.getStartDate();
    }
    
    public LocalDate default2UpdateSupply(){
        return this.getEndDate();
    }

    public String default3UpdateSupply(){
        return this.getImageUrl();
    }
    
    public String validateUpdateSupply(
            final String supplyDescription,
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
    
    //-- updateSupply --//

    //** createPersonSupplyProfile **//
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createPersonSupplyProfile(){
        return createSupplyProfile("PERSON_PROFILE_OF " + this.getOwner().title(), 10, null, null, ProfileType.PERSON_PROFILE, this, currentUserName());
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
                   "type", ProfileType.PERSON_PROFILE);
         if (container.firstMatch(query) != null) {
           return true;
         }
         
         if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
             return true;
         }
         
         if (!(((Person) getOwner()).getIsStudent() || ((Person) getOwner()).getIsProfessional())){
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
           
           if (!(((Person) getOwner()).getIsStudent() || ((Person) getOwner()).getIsProfessional())){
               return "NO_STUDENT_OR_PROFESSIONAL";
           }
           
           if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
               return "ONLY_ON_PERSON_SUPPLY";
           }
           
           return null;
           
           
     }
     //-- createPersonSupplyProfile --//
    


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

    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getDescription() + " - " + getOwner().title();
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
        return allSupplyProfiles.createSupplyProfile(supplyProfileDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, profileType, null, supplyProfileOwner, ownedBy);
    }

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


    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    Profiles allSupplyProfiles;

}
