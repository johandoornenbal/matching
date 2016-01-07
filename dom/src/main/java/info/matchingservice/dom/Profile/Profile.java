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

package info.matchingservice.dom.Profile;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.ProfileFeedback;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.HasImageUrl;
import info.matchingservice.dom.Match.*;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRule;
import info.matchingservice.dom.Tags.TagCategories;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.*;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
            name = "allSupplyProfiles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supply != null"),
    @javax.jdo.annotations.Query(
            name = "allSupplyProfilesOtherOwners", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supply != null && ownedBy != :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "allDemandProfilesOtherOwners", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demand != null && ownedBy != :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "allSupplyProfilesOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supply != null && type == :type"),
    @javax.jdo.annotations.Query(
            name = "allDemandProfiles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demand != null"),
    @javax.jdo.annotations.Query(
            name = "allDemandProfilesOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demand != null && type == :type"),
    @javax.jdo.annotations.Query(
            name = "allSupplyProfilesOfTypeByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supply != null && type == :type && ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "searchNameOfProfilesOfTypeByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demandOrSupply == :demandOrSupply && type == :type && ownedBy == :ownedBy && name.indexOf(:name) >= 0"),
    @javax.jdo.annotations.Query(
            name = "findProfileByDemandProfileOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demand == :demand")
})
@DomainObject(editing = Editing.DISABLED)
public class Profile extends MatchingSecureMutableObject<Profile> implements HasImageUrl {



    @Programmatic
    public  List<ProfileElement> getElementsOfType(ProfileElementType type){
        return getElements().stream().filter(profileElement -> profileElement.getProfileElementType() == type).collect(Collectors.toList());

    }

    @Programmatic
    public  java.util.Optional<ProfileElement> getElementOfType(ProfileElementType type){
        return getElements().stream().filter(profileElement -> profileElement.getProfileElementType() == type).findFirst();

    }




    public Profile() {
        super("name, type, ownedBy, timeStamp");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }
	//** API: PROPERTIES **//
	
	//** name **//
    private String name;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getName() {
        return name;
    }

    public void setName(final String test) {
        this.name = test;
    }
    //-- name --//
    
    //** weight **//
    private Integer weight;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    //-- weight --//

    //region > dateTime (property)
    private LocalDateTime timeStamp;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    //endregion
    
    //** startDate **//
    private LocalDate startDate;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate demandOrSupplyStartDate) {
		this.startDate = demandOrSupplyStartDate;
	}
    //-- startDate --//
	
	//** endDate **//
    private LocalDate endDate;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate demandOrSupplyEndDate) {
		this.endDate = demandOrSupplyEndDate;
	}
	//-- endDate --//
    
    //** type **//
    private ProfileType type;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileType getType() {
        return type;
    }
    
    public void setType(final ProfileType type){
        this.type = type;
    }
    //-- type --//
   
    //** demandOrSupply **//
    private DemandOrSupply demandOrSupply;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public DemandOrSupply getDemandOrSupply(){
        return demandOrSupply;
    }
    
    public void setDemandOrSupply(final DemandOrSupply demandOrSupply){
        this.demandOrSupply = demandOrSupply;
    }
    //-- demandOrSupply --//
    
    //** demand **//
    private Demand demand;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.ALL_TABLES)
    public Demand getDemand(){
        return demand;
    }
    
    public void setDemand(final Demand demand){
        this.demand = demand;
    }
    
    public boolean hideDemand(){
        if (getDemand() == null){
            return true;
        }
        
        return false;
    }
    //-- demand --//
    
    //** supply **//
    private Supply supply;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.ALL_TABLES)
    public Supply getSupply(){
        return supply;
    }
    
    public void setSupply(final Supply supply){
        this.supply = supply;
    }
    
    public boolean hideSupply(){
        if (getSupply() == null){
            return true;
        }
        
        return false;
    }
    //-- supply --//
    
    //** actorOwner **//
    @PropertyLayout(hidden=Where.PARENTED_TABLES)
    public Actor getOwner() {
        if (this.getDemandOrSupply().equals(DemandOrSupply.DEMAND)){
            return getDemand().getOwner();
        } else {
            return getSupply().getOwner();
        }
    }
    //-- actorOwner --//

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

    //** collectSupplyProfileComparisons **//
    @CollectionLayout(render = RenderType.EAGERLY)
    @Action(semantics = SemanticsOf.SAFE)
    public List<ProfileElementChoice> getCollectProfileElementChoices(){
        return profileElementChoices.profileElementChoices(this.demandOrSupply);
    }
    //-- collectSupplyProfileComparisons --//

    //** collectSupplyProfileComparisons **//
    private SortedSet<ProfileComparison> collectSupplyProfileComparisons = new TreeSet<ProfileComparison>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandProfile", dependentElement = "true")
    public SortedSet<ProfileComparison> getCollectSupplyProfileComparisons() {
        return collectSupplyProfileComparisons;
    }

    public void setCollectSupplyProfileComparisons(final SortedSet<ProfileComparison> profileComparisons) {
        this.collectSupplyProfileComparisons = profileComparisons;
    }

    // hidden on supply profile
    public boolean hideCollectSupplyProfileComparisons() {
        return this.getDemandOrSupply()==DemandOrSupply.SUPPLY;
    }
    //-- collectSupplyProfileComparisons --//

    //** collectDemandProfileComparisons **//
    private SortedSet<ProfileComparison> collectDemandProfileComparisons = new TreeSet<ProfileComparison>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "matchingSupplyProfile", dependentElement = "true")
    public SortedSet<ProfileComparison> getCollectDemandProfileComparisons() {
        return collectDemandProfileComparisons;
    }

    public void setCollectDemandProfileComparisons(final SortedSet<ProfileComparison> profileComparisons) {
        this.collectDemandProfileComparisons = profileComparisons;
    }

    // hidden on demand profile
    public boolean hideCollectDemandProfileComparisons() {
        return this.getDemandOrSupply()==DemandOrSupply.DEMAND;
    }
    //-- collectDemandProfileComparisons --//
    
    //** elements **//
    private SortedSet<ProfileElement> elements = new TreeSet<ProfileElement>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    public SortedSet<ProfileElement> getElements() {
        return elements;
    }
    
    public void setElements(final SortedSet<ProfileElement> vac) {
        this.elements = vac;
    }
    //-- elements --//
    
    //** collectAssessments **//
    private SortedSet<ProfileFeedback> collectAssessments = new TreeSet<ProfileFeedback>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "targetOfAssessment", dependentElement = "true")
    public SortedSet<ProfileFeedback> getCollectAssessments() {
        return collectAssessments;
    }

    public void setCollectAssessments(final SortedSet<ProfileFeedback> assessment) {
        this.collectAssessments = assessment;
    }

    public boolean hideCollectAssessments() {
        return this.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- collectAssessments --//

    //** collectPersistedProfileMatches **//
    private SortedSet<ProfileMatch> collectPersistedProfileMatches = new TreeSet<ProfileMatch>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "profile", dependentElement = "true")
    public SortedSet<ProfileMatch> getCollectPersistedProfileMatches() {return collectPersistedProfileMatches; }

    public void setCollectPersistedProfileMatches(final SortedSet<ProfileMatch> profileMatches) {
        this.collectPersistedProfileMatches = profileMatches;
    }

    public boolean hideCollectPersistedProfileMatches() {
        return this.demandOrSupply == DemandOrSupply.SUPPLY;
    }
    //-- collectPersistedProfileMatches --//


    
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
    
    //** collectProfileComparisons**//
    
    @Inject
    ProfileMatchingService profileMatchingService;
    
    @Action(semantics=SemanticsOf.SAFE)
    public List<ProfileComparison> updateSupplyProfileComparisons(){

        return profileMatchingService.collectProfileComparisons(this);

    }
    
    public boolean hideUpdateSupplyProfileComparisons(){
    	return this.getDemandOrSupply() != DemandOrSupply.DEMAND;
    }
    
    //-- collectProfileComparisons--//

    //** collectDemandProfileComparisons**//

    @Action(semantics=SemanticsOf.SAFE)
    public List<ProfileComparison> updateDemandProfileComparisons(){

        return profileMatchingService.collectDemandProfileComparisons(this);

    }

    public boolean hideUpdateDemandProfileComparisons(){
        return this.getDemandOrSupply() != DemandOrSupply.SUPPLY;
    }

    //-- collectProfileComparisons--//

    //** createRequiredProfileElementRole **//
    // Business rule:
    // alleen op profile van type PERSON of ORGANISATION
    // alleen op demand profiel
    // slechts 1 per profile

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile createRequiredProfileElementRole(
            @ParameterLayout(named="weight")
            final Integer weight,
            @ParameterLayout(named="student")
            final boolean student,
            @ParameterLayout(named="professional")
            final boolean professional,
            @ParameterLayout(named="principal")
            final boolean principal
    ){
        requiredProfileElementRoles.createRequiredProfileElementRole(
                "REQUIRED_ROLE_ELEMENT",
                weight,
                ProfileElementType.ROLE_REQUIRED,
                this,
                student,
                professional,
                principal
        );
        return this;
    }

    public boolean hideCreateRequiredProfileElementRole(
            final Integer weight,
            final boolean student,
            final boolean professional,
            final boolean principal
    ){

        // alleen op profile van type PERSON of ORGANISATION
        // alleen op demand profiel
        if ((this.getType() != ProfileType.PERSON_PROFILE && this.getType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
            return true;
        }

        // er  mag hooguit 1 Role element zijn
        QueryDefault<RequiredProfileElementRole> query =
                QueryDefault.create(
                        RequiredProfileElementRole.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.ROLE_REQUIRED,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }

        return false;
    }

    public String validateCreateRequiredProfileElementRole(
            final Integer weight,
            final boolean student,
            final boolean professional,
            final boolean principal
    ){

        // alleen op profile van type PERSON of ORGANISATION
        if ((this.getType() != ProfileType.PERSON_PROFILE && this.getType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
            return "ONLY_ON_PERSON_DEMAND_AND_PERSON_OR_ORGANISATION_PROFILE";
        }

        // er  mag hooguit 1 Role element zijn
        QueryDefault<RequiredProfileElementRole> query =
                QueryDefault.create(
                        RequiredProfileElementRole.class,
                        "findProfileElementOfType",
                        "profileElementType", ProfileElementType.ROLE_REQUIRED,
                        "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }

        return null;
    }

    //-- createRequiredProfileElementRole --//
    
    //** createPassionElement **//
    // Business rule:
    // alleen op profile van type PERSON of ORGANISATION
    // alleen op aanbod profiel
    // slechts 1 per profile
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public Profile createPassionElement(
    		@ParameterLayout(named="textValue")
    		final String textValue,
    		@ParameterLayout(named="weight")
    		final Integer weight
    		){
    	profileElementTexts.createProfileElementText(
    			"PASSION_ELEMENT",
    			weight, 
    			textValue, 
    			ProfileElementType.PASSION, 
    			this);
    	return this;
    }
    
    
    public boolean hideCreatePassionElement(
            final String passionText,
            final Integer weight
            ){
        
    	// alleen op profile van type PERSON of ORGANISATION
        // alleen op aanbod profiel
        if ((this.getType() != ProfileType.PERSON_PROFILE && this.getType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND){
            return true;
        }
        
        // er  mag hooguit 1 Passie element zijn
        QueryDefault<ProfileElementText> query = 
                QueryDefault.create(
                        ProfileElementText.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreatePassionElement(
            final String passionText,
            final Integer weight
            ){
        
    	// alleen op profile van type PERSON of ORGANISATION
        if ((this.getType() != ProfileType.PERSON_PROFILE && this.getType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND){
            return "ONLY_ON_PERSON_SUPPLY_AND_PERSON_OR_ORGANISATION_PROFILE";
        }
        
        // er  mag hooguit 1 Passie element zijn
        QueryDefault<ProfileElementText> query = 
                QueryDefault.create(
                        ProfileElementText.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createPassionElement --//
    
    //** createPassionTagElement **//
    // Business rule:
    // only on profile van type PERSON
    // only on DEMAND PROFILE
    // At Most one
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createPassionTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.createProfileElementTag(
                "PASSION_TAGS_ELEMENT", 
                weight, 
                ProfileElementType.PASSION_TAGS, 
                this);
    }
    
    public boolean hideCreatePassionTagElement(final Integer weight){
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
            return true;
        }
        
     // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreatePassionTagElement(final Integer weight){
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
            return "ONLY_ON_PERSON_DEMAND_AND_PERSON_PROFILE";
        }
        
     // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createPassionTagElement --//
    
    //** createBrancheTagElement **//
    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createBrancheTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.createProfileElementTag(
                "BRANCHE_TAGS_ELEMENT", 
                weight, 
                ProfileElementType.BRANCHE_TAGS, 
                this);
    }
    
    public boolean hideCreateBrancheTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
        if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return true;
        }
        
        // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.BRANCHE_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreateBrancheTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
    	if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }
        
    	// At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.BRANCHE_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createBrancheTagElement --//
    
    //** createQualityTagElement **//
    
    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    // 2 dezelfde kwaliteiten kiezen heeft geen zin => TODO: Deze moet in zijn algemeenheid worden opgelost bij tags denk ik
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createQualityTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.createProfileElementTag(
                "QUALITY_TAGS_ELEMENT", 
                weight, 
                ProfileElementType.QUALITY_TAGS, 
                this);
    }
    
    public boolean hideCreateQualityTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
        if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return true;
        }
        
        // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.QUALITY_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreateQualityTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
    	if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }
        
    	// At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.QUALITY_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createQualityTagElement --//
    
    //** createWeekdayTagElement **//
    
    // Business rule:
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    // 2 dezelfde kwaliteiten kiezen heeft geen zin => TODO: Deze moet in zijn algemeenheid worden opgelost bij tags denk ik
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout()
    public ProfileElementTag createWeekDayTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.createProfileElementTag(
                "WEEKDAY_TAGS_ELEMENT", 
                weight, 
                ProfileElementType.WEEKDAY_TAGS, 
                this);
    }
    
    public boolean hideCreateWeekDayTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
        if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return true;
        }
        
        // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.WEEKDAY_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreateWeekDayTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
    	if ((this.getType() != ProfileType.PERSON_PROFILE) && (this.getType() != ProfileType.ORGANISATION_PROFILE)){
            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
        }
        
    	// At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.WEEKDAY_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createWeekDayTagElement --//
    
    //** createLocationElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // textValue moet een geldig postcode formaat zijn (4 cijfers , geen spatie, 2 hoofdletters)
    
//    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
//    public Profile createLocationElement(
//    		@ParameterLayout(named="postcode")
//    		@Parameter(regexPattern="^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")
//    		final String textValue,
//    		@ParameterLayout(named="weight")
//    		final Integer weight
//    		){
//    	profileElementTexts.createProfileElementText("LOCATION_ELEMENT", weight, textValue, ProfileElementType.LOCATION, this);
//    	return this;
//    }
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createLocationElement(
    		@ParameterLayout(named="postcode")
    		@Parameter(regexPattern="^[1-9]{1}[0-9]{3}[A-Z]{2}$")
    		final String textValue,
    		@ParameterLayout(named="weight")
    		final Integer weight
    		){
    	profileElementLocations.createProfileElementLocation("LOCATION_ELEMENT", weight, textValue, ProfileElementType.LOCATION, this);
    	return this;
    }
    
    public boolean hideCreateLocationElement(final String textValue, final Integer weight){
    	
        QueryDefault<ProfileElementLocation> query = 
                QueryDefault.create(
                        ProfileElementLocation.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return true;
        }
    	
    	// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    	if (this.type == ProfileType.PERSON_PROFILE || this.type == ProfileType.ORGANISATION_PROFILE){
    		return false;
    	}
    	
    	return true;
    }
    
    public String validateCreateLocationElement(final String textValue, final Integer weight){
    	
        QueryDefault<ProfileElementLocation> query = 
                QueryDefault.create(
                        ProfileElementLocation.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
    	
    	// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    	if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){
    		return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
    	}
    	
    	//textValue moet een geldig postcode formaat zijn
    	
    	
    	return null;
    }
    //-- createLocationElement --//

    //** createHourlyRateElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createHourlyRateElement(
            @ParameterLayout(named="hourly rate")
            final Integer rate,
            @ParameterLayout(named="weight")
            final Integer weight
    )
    {
        profileElementNumerics.createProfileElementNumeric("HOURLY_RATE_ELEMENT", weight, rate, ProfileElementType.HOURLY_RATE, this);

        return this;
    }

    public boolean hideCreateHourlyRateElement(final Integer rate, final Integer weight){

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.HOURLY_RATE);

        if (
                        // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                        (this.getType() == ProfileType.PERSON_PROFILE || this.getType() == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                )
        {

            return false;
        }

        return true;
    }

    public String validateCreateHourlyRateElement(final Integer rate, final Integer weight){

        QueryDefault<ProfileElementNumeric> query =
                QueryDefault.create(
                        ProfileElementNumeric.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.HOURLY_RATE);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        return null;
    }
    //-- createHourlyRateElement --//
    
    //** createAgeElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op demands
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createAgeElement(
    		@ParameterLayout(named="age")
    		final Integer age,
    		@ParameterLayout(named="weight")
    		final Integer weight
    		) 
    {
    	profileElementNumerics.createProfileElementNumeric("AGE_ELEMENT", weight, age, ProfileElementType.AGE, this);
    	
    	return this;
    }
    
    public boolean hideCreateAgeElement(final Integer age, final Integer weight){
    	
    	QueryDefault<ProfileElementNumeric> query = 
                QueryDefault.create(
                		ProfileElementNumeric.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.AGE);
    	
    	if (
    			// alleen op demands
    			this.getDemandOrSupply() == DemandOrSupply.DEMAND
    			
    			&&
    			
    			// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    			(this.getType() == ProfileType.PERSON_PROFILE || this.getType() == ProfileType.ORGANISATION_PROFILE)
    			
    			&&
    			
    			// er mag er hooguit een van zijn
    			container.firstMatch(query) == null
    		)
    	{
    		
    		return false;
    	}
    	
    	return true;
    }
    
   public String validateCreateAgeElement(final Integer age, final Integer weight){
    	
    	QueryDefault<ProfileElementNumeric> query = 
                QueryDefault.create(
                		ProfileElementNumeric.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.AGE);
    	
    	if (container.firstMatch(query) != null) {
    		
    		return "ONE_INSTANCE_AT_MOST";
    		
    	}
    	
    	if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){
    		
    		return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
    		
    	}
    	
    	return null;
    }
    //-- createAgeElement --//
   
   //** createUseAgeElement **//
   // Business rule:
   // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
   // er mag er hooguit een van zijn
   // alleen op supplies
   @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
   public Profile createUseAgeElement(
   		@ParameterLayout(named="weight")
   		final Integer weight) {
   	
   	profileElementUsePredicates.createProfileElementUsePredicate("USE_AGE_ELEMENT", weight, false, true, ProfileElementType.USE_AGE, this);
   	
   	return this;
   }
   
   public boolean hideCreateUseAgeElement(final Integer weight) {
   	
   	QueryDefault<ProfileElementUsePredicate> query = 
               QueryDefault.create(
               		ProfileElementUsePredicate.class, 
                   "findProfileElementOfType",
                   "profileElementOwner", this, "profileElementType", ProfileElementType.USE_AGE);
   	
   	if (
   			// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
   			(this.type == ProfileType.PERSON_PROFILE || this.type == ProfileType.ORGANISATION_PROFILE)
   			
   			&&
   			
   			// alleen op demands
   			this.getDemandOrSupply() == DemandOrSupply.SUPPLY
   			
   			&&
   			
   			// er mag er hooguit een van zijn
   			container.firstMatch(query) == null
   		) 
   	{
   		return false;
   	}
   	
   	return true;
   }
   
   public String validateCreateUseAgeElement(final Integer weight) {
   	
   	QueryDefault<ProfileElementUsePredicate> query = 
               QueryDefault.create(
                       ProfileElementUsePredicate.class,
                       "findProfileElementOfType",
                       "profileElementOwner", this, "profileElementType", ProfileElementType.USE_AGE);
   	
   	if (container.firstMatch(query) != null) {
   		
   		return "ONE_INSTANCE_AT_MOST";
   		
   	}
   	
   	if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){
   		
   		return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
   		
   	}
   	
   	if (this.getDemandOrSupply() != DemandOrSupply.SUPPLY) {
   		
   		return "ONLY_ON_SUPPLY";
   		
   	}
   	
   	return null;
   	
   }
   //-- createUseAgeElement --//
    
    //** createTimePeriodElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op demands
    // de 'gewone' datum regels gelden
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createTimePeriodElement(
    		@ParameterLayout(named="startDate")
    		final LocalDate startDate,
    		@ParameterLayout(named="endDate")
    		final LocalDate endDate,
    		@ParameterLayout(named="weight")
    		final Integer weight) {
    	
    	profileElementTimePeriods.createProfileElementTimePeriod("TIME_PERIOD_ELEMENT", weight, startDate, endDate, ProfileElementType.TIME_PERIOD, this);
    	
    	return this;
    }
    
    public boolean hideCreateTimePeriodElement(final LocalDate startDate, final LocalDate endDate, final Integer weight) {
    	
    	QueryDefault<ProfileElementTimePeriod> query = 
                QueryDefault.create(
                        ProfileElementTimePeriod.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.TIME_PERIOD);
    	
    	if (
    			// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    			(this.type == ProfileType.PERSON_PROFILE || this.type == ProfileType.ORGANISATION_PROFILE)
    			
    			&&
    			
    			// alleen op demands
    			this.getDemandOrSupply() == DemandOrSupply.DEMAND
    			
    			&&
    			
    			// er mag er hooguit een van zijn
    			container.firstMatch(query) == null
    		) 
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    public String validateCreateTimePeriodElement(final LocalDate startDate, final LocalDate endDate, final Integer weight) {
    	
    	QueryDefault<ProfileElementTimePeriod> query = 
                QueryDefault.create(
                		ProfileElementTimePeriod.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.TIME_PERIOD);
    	
    	if (container.firstMatch(query) != null) {
    		
    		return "ONE_INSTANCE_AT_MOST";
    		
    	}
    	
    	if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){
    		
    		return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
    		
    	}
    	
    	if (this.getDemandOrSupply() != DemandOrSupply.DEMAND) {
    		
    		return "ONLY_ON_DEMAND";
    		
    	}
    	
    	//Date validation
    	
    	final LocalDate today = LocalDate.now();
    	if (endDate != null && endDate.isBefore(today))
    	{
    		return "ENDDATE_BEFORE_TODAY";
    	}
    	
    	if (
    			endDate != null 
    			
    			&& 
    			
    			startDate != null
    			
    			&&
    			
    			endDate.isBefore(startDate)
    			
    			)
    	{
    		return "ENDDATE_BEFORE_STARTDATE";
    	}
    	
    	
    	return null;
    }
    
    //-- createTimePeriodElement --//
    
    //** createUseTimePeriodElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // alleen op supplies
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createUseTimePeriodElement(
    		@ParameterLayout(named="weight")
    		final Integer weight) {
    	
    	profileElementUsePredicates.createProfileElementUsePredicate("USE_TIME_PERIOD_ELEMENT", weight, true, false, ProfileElementType.USE_TIME_PERIOD, this);
    	
    	return this;
    }
    
    public boolean hideCreateUseTimePeriodElement(final Integer weight) {
    	
    	QueryDefault<ProfileElementUsePredicate> query = 
                QueryDefault.create(
                		ProfileElementUsePredicate.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.USE_TIME_PERIOD);
    	
    	if (
    			// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    			(this.type == ProfileType.PERSON_PROFILE || this.type == ProfileType.ORGANISATION_PROFILE)
    			
    			&&
    			
    			// alleen op demands
    			this.getDemandOrSupply() == DemandOrSupply.SUPPLY
    			
    			&&
    			
    			// er mag er hooguit een van zijn
    			container.firstMatch(query) == null
    		) 
    	{
    		return false;
    	}
    	
    	return true;
    }
    
    public String validateCreateUseTimePeriodElement(final Integer weight) {
    	
    	QueryDefault<ProfileElementUsePredicate> query = 
                QueryDefault.create(
                        ProfileElementUsePredicate.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.USE_TIME_PERIOD);
    	
    	if (container.firstMatch(query) != null) {
    		
    		return "ONE_INSTANCE_AT_MOST";
    		
    	}
    	
    	if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){
    		
    		return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";
    		
    	}
    	
    	if (this.getDemandOrSupply() != DemandOrSupply.SUPPLY) {
    		
    		return "ONLY_ON_SUPPLY";
    		
    	}
    	
    	return null;
    	
    }
    //-- createUseTimePeriodElement --//



    /// CREATE TIME AVAILABLE ELEMENT
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createTimeAvailableElement(
            @ParameterLayout(named = "weight")
            final Integer weight,
            @ParameterLayout(named = "timeavailable")
            final int  timeAvailable
    ){
        profileElementNumerics.createProfileElementNumeric("TIME_AVAILABLE", weight, timeAvailable, ProfileElementType.TIME_AVAILABLE, this);
        return this;
    }

    //** createEducationLevelElement **//
    // Business rule:
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile createEducationLevelElement(
            @ParameterLayout(named = "weight")
            final Integer weight,
            @ParameterLayout(named = "dropDownValue")
            final DropDownForProfileElement dropDown
    ){
        profileElementDropDowns.createProfileElementDropDown(
                "EDUCATION_LEVEL",
                weight,
                dropDown,
                ProfileElementType.EDUCATION_LEVEL,
                this);
        return this;
    }

    public List<DropDownForProfileElement> autoComplete1CreateEducationLevelElement(String search) {
        return dropDownForProfileElements.findDropDowns(search, ProfileElementType.EDUCATION_LEVEL);
    }

    public boolean hideCreateEducationLevelElement(final Integer weight, final DropDownForProfileElement dropDown) {

        QueryDefault<ProfileElementDropDown> query =
                QueryDefault.create(
                        ProfileElementDropDown.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.EDUCATION_LEVEL);

        if (
            // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
                (this.type == ProfileType.PERSON_PROFILE || this.type == ProfileType.ORGANISATION_PROFILE)

                        &&

                        // er mag er hooguit een van zijn
                        container.firstMatch(query) == null
                )
        {
            return false;
        }

        return true;
    }

    public String validateCreateEducationLevelElement(final Integer weight, final DropDownForProfileElement dropDown) {

        QueryDefault<ProfileElementDropDown> query =
                QueryDefault.create(
                        ProfileElementDropDown.class,
                        "findProfileElementOfType",
                        "profileElementOwner", this, "profileElementType", ProfileElementType.EDUCATION_LEVEL);

        if (container.firstMatch(query) != null) {

            return "ONE_INSTANCE_AT_MOST";

        }

        if (this.type != ProfileType.PERSON_PROFILE && this.type != ProfileType.ORGANISATION_PROFILE){

            return "ONLY_ON_PERSON_OR_ORGANISATION_PROFILE";

        }

        return null;

    }




    @Programmatic
    public ProfileElementText createBackgroundImgElement(final String backgroundImg){

        return profileElementTexts.createProfileElementText("background_image", 10, backgroundImg, ProfileElementType.URL_PROFILE_BACKGROUND, this);

    }
    @Programmatic
    public ProfileElementText createStoryElement(final String story){

        return profileElementTexts.createProfileElementText("story_element", 10, story, ProfileElementType.STORY, this);

    }


    //-- createEducationLevelElement --//

    //** createPriceElement **//
    // Business rule:
    // Er kan maar een prijs element zijn
    // Alleen op cursusprofiel
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public ProfileElementNumeric createPriceElement(
            @ParameterLayout(named="numericValue")
            final Integer numericValue
            ){
        return profileElementNumerics.createProfileElementNumeric(
                "PRICE_ELEMENT", 
                10,
                numericValue,
                ProfileElementType.NUMERIC, 
                this);
    }
    
    public boolean hideCreatePriceElement(
            final Integer numericValue
            ){
        
        if (this.getType() != ProfileType.COURSE_PROFILE){
            return true;
        }
        
        QueryDefault<ProfileElementNumeric> query = 
                QueryDefault.create(
                        ProfileElementNumeric.class, 
                    "findProfileElementNumericByOwnerProfile",
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    public String validateCreatePriceElement(
            final Integer numericValue
            ){
        
        if (this.getType() != ProfileType.COURSE_PROFILE){
            return "ONLY_ON_COURSE_PROFILE";
        }
        
        QueryDefault<ProfileElementNumeric> query = 
                QueryDefault.create(
                        ProfileElementNumeric.class, 
                    "findProfileElementNumericByOwnerProfile",
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    //-- createPriceElement --//
    
    //** updateProfile **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Profile updateProfile(
            @ParameterLayout(named="name")
            String newString,
            @ParameterLayout(named="weight")
            Integer newInteger,
            @ParameterLayout(named="startDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            LocalDate profileStartDate,
            @ParameterLayout(named="endDate")
            @Parameter(optionality=Optionality.OPTIONAL)
            LocalDate profileEndDate,
            @ParameterLayout(named="imageUrl")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String imageUrl
            ){
        this.setName(newString);
        this.setWeight(newInteger);
        this.setStartDate(profileStartDate);
        this.setEndDate(profileEndDate);
        this.setImageUrl(imageUrl);
        return this;
    }
    
    public String default0UpdateProfile() {
        return getName();
    }
    
    public Integer default1UpdateProfile() {
        return getWeight();
    }
    
    public LocalDate default2UpdateProfile() {
        return getStartDate();
    }
    
    public LocalDate default3UpdateProfile() {
        return getEndDate();
    }

    public String default4UpdateProfile() {
        return getImageUrl();
    }
    //-- updateProfile --//
    
    //** deleteProfile **//
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Demand deleteProfile( 
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
        boolean confirmDelete) {
    	// first delete related data: all persisted matches
    	for (Iterator<ProfileMatch> it = profileMatches.findProfileMatchesByDemandProfile(this).iterator(); it.hasNext();) {
    		container.remove(it.next());
    		container.informUser("ProfileMatch deleted");
    	}
    	// and all PersistedProfileElementComparisons
    	for (Iterator<PersistedProfileElementComparison> it = persistedProfileElementComparisons.findProfileElementComparisons(ownedBy).iterator(); it.hasNext();) {
    		container.remove(it.next());
    		container.informUser("PersistedProfileElementComparison deleted");
    	}
        container.removeIfNotAlready(this);
        container.informUser("Profile deleted");
        return this.getDemand();
            
    }

    public String validateDeleteProfile(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
    public boolean hideDeleteProfile(boolean confirmDelete) {
    	
    	if (this.getDemandOrSupply() == DemandOrSupply.DEMAND) {
    		return false;
    	}
        return true;
    }
    
    //-- deleteProfile --//

    //** getChosenProfileMatch **//

    public ProfileMatch getChosenProfileMatch() {

        if (profileMatches.findProfileMatchesByDemandProfileAndStatus(this, CandidateStatus.CHOSEN).size()>0) {
            return profileMatches.findProfileMatchesByDemandProfileAndStatus(this, CandidateStatus.CHOSEN).get(0);
        }

        if (profileMatches.findProfileMatchesByDemandProfileAndStatus(this, CandidateStatus.RESERVED).size()>0) {
            return profileMatches.findProfileMatchesByDemandProfileAndStatus(this, CandidateStatus.RESERVED).get(0);
        }

        return null;
    }

    public boolean hideChosenProfileMatch() {
        return this.demandOrSupply.equals(DemandOrSupply.SUPPLY);
    }

    //** getChosenProfileMatch **//
    
	//-- API: ACTIONS --//
    
	//** GENERIC OBJECT STUFF **//
	//** constructor **//

	
	//** ownedBy - Override for secure object **//
    private String ownedBy;

    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
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
    public String toString() {
        return "Profiel: " + this.name;
    }
	//-- HELPERS: generic object helpers --//
	//** HELPERS: programmatic actions **//
    
    @Programmatic
    // help for fixtures
    public List<ProfileElement> findProfileElementByOwnerProfileAndDescription(final String profileElementDescription){
    	return profileElements.findProfileElementByOwnerProfileAndDescription(profileElementDescription, this);
    }
    
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
    
	//** INJECTIONS **//
    
	@javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @Inject
    ProfileElementTags profileElementTags;
    
    @Inject
    ProfileElementTimePeriods profileElementTimePeriods;
    
    @Inject
    ProfileElementUsePredicates profileElementUsePredicates;
    
    @Inject
    Tags tags;
    
    @Inject
    TagCategories tagCategories;
    
    @Inject
    ProfileElementDropDownAndTexts profileElementDropDownsAndTexts;
    
    @Inject
    ProfileElementTexts profileElementTexts;
    
    @Inject
    ProfileElementLocations profileElementLocations;
    
    @Inject
    ProfileElementNumerics profileElementNumerics;
    
    @Inject
    ProfileElements profileElements;
    
    @Inject
    ProfileMatches profileMatches;
    
    @Inject
    PersistedProfileElementComparisons persistedProfileElementComparisons;

    @Inject
    RequiredProfileElementRoles requiredProfileElementRoles;

    @Inject
    ProfileComparisons profileComparisons;

    @Inject
    ProfileElementChoices profileElementChoices;
    
	//-- INJECTIONS --//
    
	//** HIDDEN: PROPERTIES **//
    
    private ProfileTypeMatchingRule profileTypeMatchingRule;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
//    @PropertyLayout(hidden=Where.EVERYWHERE)
	public ProfileTypeMatchingRule getProfileTypeMatchingRule() {
		return profileTypeMatchingRule;
	}

	public void setProfileTypeMatchingRule(ProfileTypeMatchingRule canMatchProfileOfType) {
		this.profileTypeMatchingRule = canMatchProfileOfType;
	}
	
	//-- HIDDEN: PROPERTIES --//
    
	//** HIDDEN: ACTIONS **//

    @ActionLayout(hidden=Where.EVERYWHERE)
    public Profile newProfileElementDropDown(
            final Integer weight,
            final DropDownForProfileElement dropDown
            ){
        profileElementDropDowns.createProfileElementDropDown(
                "kwaliteit " + dropDown.title(),
                weight,
                dropDown,
                ProfileElementType.QUALITY,
                this);
        return this;
    }

    public List<DropDownForProfileElement> autoComplete1NewProfileElementDropDown(String search) {
        return dropDownForProfileElements.findDropDowns(search, ProfileElementType.QUALITY);
    }


    @ActionLayout(hidden=Where.EVERYWHERE)
    public ProfileElementDropDownAndText newProfileElementDropDownAndText(
            final String description,
            final Integer weight,
            @Parameter(optionality=Optionality.OPTIONAL)
            final DropDownForProfileElement dropDown,
            @Parameter(optionality=Optionality.OPTIONAL)
            final String text
            ){
        return profileElementDropDownsAndTexts.createProfileElementDropDownAndText(
                description,
                weight,
                dropDown,
                text,
                ProfileElementType.QUALITY,
                this);
    }

    public List<DropDownForProfileElement> autoComplete2NewProfileElementDropDownAndText(String search) {
        return dropDownForProfileElements.findDropDowns(search, ProfileElementType.QUALITY);
    }

    @ActionLayout(hidden=Where.EVERYWHERE)
    public ProfileElementText newProfileElementText(
            final String description,
            final Integer weight,
            final String textValue
            ){
        return profileElementTexts.createProfileElementText(
                description,
                weight,
                textValue,
                ProfileElementType.TEXT,
                this);
    }

    @ActionLayout(hidden=Where.EVERYWHERE)
    public ProfileElementNumeric newProfileElementNumeric(
            final String description,
            final Integer weight,
            final Integer numericValue
            ){
        return profileElementNumerics.createProfileElementNumeric(
                description,
                weight,
                numericValue,
                ProfileElementType.NUMERIC,
                this);
    }

    
	//-- HIDDEN: ACTIONS --//

}