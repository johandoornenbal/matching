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

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.ProfileAssessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Tags.TagCategories;
import info.matchingservice.dom.Tags.Tags;

import java.util.List;
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
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.RenderType;
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
            name = "allSupplyProfiles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supplyProfileOwner != null"),
    @javax.jdo.annotations.Query(
            name = "allSupplyProfilesOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supplyProfileOwner != null && profileType == :profileType"),
    @javax.jdo.annotations.Query(
            name = "allDemandProfiles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demandProfileOwner != null"),
    @javax.jdo.annotations.Query(
            name = "allDemandProfilesOfType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demandProfileOwner != null && profileType == :profileType"),
    @javax.jdo.annotations.Query(
            name = "allSupplyProfilesOfTypeByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE supplyProfileOwner != null && profileType == :profileType && ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "searchNameOfProfilesOfTypeByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demandOrSupply == :demandOrSupply && profileType == :profileType && ownedBy == :ownedBy && profileName.indexOf(:profileName) >= 0")                    
})
@DomainObject(editing = Editing.DISABLED)
public class Profile extends MatchingSecureMutableObject<Profile> {
    
    public Profile() {
        super("profileName, profileType, ownedBy, uniqueItemId");
    }
    
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
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

    //Immutables //////////////////////////////////////////////////////////////////////////////////////
    
    private ProfileType profileType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileType getProfileType() {
        return profileType;
    }
    
    public void setProfileType(final ProfileType profileType){
        this.profileType = profileType;
    }
    
    private DemandOrSupply demandOrSupply;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public DemandOrSupply getDemandOrSupply(){
        return demandOrSupply;
    }
    
    public void setDemandOrSupply(final DemandOrSupply demandOrSupply){
        this.demandOrSupply = demandOrSupply;
    }
    
    private Demand demandProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.ALL_TABLES)
    public Demand getDemandProfileOwner(){
        return demandProfileOwner;
    }
    
    public void setDemandProfileOwner(final Demand demandProfileOwner){
        this.demandProfileOwner = demandProfileOwner;
    }
    
    public boolean hideDemandProfileOwner(){
        if (getDemandProfileOwner() == null){
            return true;
        }
        
        return false;
    }
    
    private Supply supplyProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.ALL_TABLES)
    public Supply getSupplyProfileOwner(){
        return supplyProfileOwner;
    }
    
    public void setSupplyProfileOwner(final Supply supplyProfileOwner){
        this.supplyProfileOwner = supplyProfileOwner;
    }
    
    public boolean hideSupplyProfileOwner(){
        if (getSupplyProfileOwner() == null){
            return true;
        }
        
        return false;
    }
    
    @PropertyLayout(hidden=Where.PARENTED_TABLES, named="Eigenaar")
    public Actor getActorOwner() {
        if (this.getDemandOrSupply().equals(DemandOrSupply.DEMAND)){
            return getDemandProfileOwner().getDemandOwner();
        } else {
            return getSupplyProfileOwner().getSupplyOwner();
        }
    }
    
    //ProfileName /////////////////////////////////////////////////////////////////////////////////////
    private String profileName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(final String test) {
        this.profileName = test;
    }
    
    //weight /////////////////////////////////////////////////////////////////////////////////////
    private Integer weight;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    private LocalDate demandOrSupplyProfileStartDate;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public LocalDate getDemandOrSupplyProfileStartDate() {
		return demandOrSupplyProfileStartDate;
	}

	public void setDemandOrSupplyProfileStartDate(LocalDate demandOrSupplyStartDate) {
		this.demandOrSupplyProfileStartDate = demandOrSupplyStartDate;
	}
    
    private LocalDate demandOrSupplyProfileEndDate;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
	public LocalDate getDemandOrSupplyProfileEndDate() {
		return demandOrSupplyProfileEndDate;
	}

	public void setDemandOrSupplyProfileEndDate(LocalDate demandOrSupplyEndDate) {
		this.demandOrSupplyProfileEndDate = demandOrSupplyEndDate;
	}
    
    
    // Region> ProfileElements
    
    //XTALUS VOOR PERSONEN
    
    //**PASSIE** *******SUPPLY PROFILE*******************//
    //BUSINESSRULE
    // alleen op profile van type PERSON of ORGANISATION
    // alleen op aanbod profiel
    // slechts 1 per profile
    @ActionLayout(
    		named="Nieuwe passie"
    		)
    public Profile newPassion(
    		@ParameterLayout(named="textValue")
    		final String textValue,
    		@ParameterLayout(named="weight")
    		final Integer weight
    		){
    	profileElementTexts.newProfileElementText(
    			"Passie omschrijving", 
    			weight, 
    			textValue, 
    			ProfileElementType.PASSION, 
    			this);
    	return this;
    }
    
    
    public boolean hideNewPassion(
            final String passionText,
            final Integer weight
            ){
        
    	// alleen op profile van type PERSON of ORGANISATION
        // alleen op aanbod profiel
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND){
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
    
    public String validateNewPassion(
            final String passionText,
            final Integer weight
            ){
        
    	// alleen op profile van type PERSON of ORGANISATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE && this.getProfileType() != ProfileType.ORGANISATION_PROFILE) || this.demandOrSupply == DemandOrSupply.DEMAND){
            return "Alleen op aanbod en op persoon- of organisatie profiel";
        }
        
        // er  mag hooguit 1 Passie element zijn
        QueryDefault<ProfileElementText> query = 
                QueryDefault.create(
                        ProfileElementText.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Je hebt al je passie ingevuld; pas deze aan indien nodig";
        }
        
        return null;
    }
    
    //END >> **PASSIE** *******SUPPLY PROFILE*******************//
    
    //**PASSIE TAG** *******DEMAND PROFILE*******************//
    //BUSINESSRULE
    // only on profile van type PERSON
    // only on DEMAND PROFILE
    // At Most one
    @ActionLayout(
            named="Passie steekwoorden"
            )
    public ProfileElementTag newPassionTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.newProfileElementTag(
                "passie steekwoorden", 
                weight, 
                ProfileElementType.PASSION_TAGS, 
                this);
    }
    
    public boolean hideNewPassionTagElement(final Integer weight){
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
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
    
    public String validateNewPassionTagElement(final Integer weight){
        // only on profile van type PERSON
        // only on DEMAND PROFILE
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) || this.demandOrSupply == DemandOrSupply.SUPPLY){
            return "Alleen op gevraagd persoonsprofiel";
        }
        
     // At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.PASSION_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Er mag maar een element met passie steekwoorden zijn";
        }
        
        return null;
    }
    
    
    
    //END >> **PASSIE TAG** *******DEMAND PROFILE*******************//
    
    //**BRANCHE TAG** *******DEMAND/SUPPLY PROFILE*******************//
    //BUSINESSRULE
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    @ActionLayout(
            named="Branche steekwoorden"
            )
    public ProfileElementTag newBrancheTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.newProfileElementTag(
                "branche steekwoorden", 
                weight, 
                ProfileElementType.BRANCHE_TAGS, 
                this);
    }
    
    public boolean hideNewBrancheTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)){
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
    
    public String validateNewBrancheTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
    	if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)){
            return "Alleen op persoons- of organisatie profiel";
        }
        
    	// At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.BRANCHE_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Er mag maar een element met branche steekwoorden zijn";
        }
        
        return null;
    }
    
    
    
    //END >> **BRANCHE TAG** *******DEMAND/SUPPPLY PROFILE*******************//
    
    // **QUALITY TAG** *******DEMAND/SUPPPLY PROFILE*******************//
    //BUSINESSRULE
    // only on profile of type PERSON and ORGANISATION
    // At Most one
    // 2 dezelfde kwaliteiten kiezen heeft geen zin => TODO: Deze moet in zijn algemeenheid worden opgelost bij tags denk ik
    
    
    @ActionLayout(
            named="Kwaliteiten"
            )
    public ProfileElementTag newQualityTagElement(
            @ParameterLayout(named="weight")
            final Integer weight
            ){
        return profileElementTags.newProfileElementTag(
                "kwaliteiten", 
                weight, 
                ProfileElementType.QUALITY_TAGS, 
                this);
    }
    
    public boolean hideNewQualityTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
        if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)){
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
    
    public String validateNewQualityTagElement(final Integer weight){
        // only on profile of type PERSON and ORGANSATION
    	if ((this.getProfileType() != ProfileType.PERSON_PROFILE) && (this.getProfileType() != ProfileType.ORGANISATION_PROFILE)){
            return "Alleen op persoons- of organisatie profiel";
        }
        
    	// At Most one
        QueryDefault<ProfileElementTag> query = 
                QueryDefault.create(
                        ProfileElementTag.class, 
                    "findProfileElementOfType", 
                    "profileElementType", ProfileElementType.QUALITY_TAGS,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Er mag maar een element met kwaliteiten zijn";
        }
        
        return null;
    }
        
    
    
    //END >> **QUALITY TAG** *******DEMAND/SUPPPLY PROFILE*******************//
    
//    //**KWALITEITEN*//
//    //BUSINESSRULE
//    // alleen tonen op profile van type PERSON
//    // 2 dezelfde kwaliteiten kiezen heeft geen zin
//    
//    public Profile newQualityElementDropDown(
//            @ParameterLayout(named="dropDownValue")
//            final DropDownForProfileElement dropDown,
//            @ParameterLayout(named="weight")
//            final Integer weight
//            ){
//        profileElementDropDowns.newProfileElementDropDown(
//                "kwaliteit " + dropDown.title(), 
//                weight,
//                dropDown,
//                ProfileElementType.QUALITY, 
//                this);
//        return this;
//    }
//    
//    public List<DropDownForProfileElement> autoComplete0NewQualityElementDropDown(String search) {
//        return dropDownForProfileElements.findDropDowns(search);
//    }
//    
//    public boolean hideNewQualityElementDropDown(
//            final DropDownForProfileElement dropDown,
//            final Integer weight
//            ){
//        
//        if (this.getProfileType() != ProfileType.PERSON_PROFILE){
//            return true;
//        }
//        
//        return false;
//    }
//    
//    public String validateNewQualityElementDropDown(
//            final DropDownForProfileElement dropDown,
//            final Integer weight
//            ){
//        
//        if (this.getProfileType() != ProfileType.PERSON_PROFILE){
//            return "Alleen op persoons profiel";
//        }
//        
//        // twee dezelfde kwaliteiten heeft geen zin
//        QueryDefault<ProfileElementDropDown> query = 
//                QueryDefault.create(
//                        ProfileElementDropDown.class, 
//                    "findProfileElementDropDownByOwnerProfileAndDropDownValue", 
//                    "dropDownValue", dropDown,
//                    "profileElementOwner", this);
//        if (container.firstMatch(query) != null) {
//            return "Deze kwaliteit heb je al gekozen";
//        }
//        
//        return null;
//    }
    
    // LOKATIE (Postcode)
    // BUSINESSRULE
    // alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    // er mag er hooguit een van zijn
    // textValue moet een geldig postcode formaat zijn (4 cijfers , al of niet een spatie, 2 hoofdletters)
    public Profile newLocation(
    		@ParameterLayout(named="postcode")
    		@Parameter(regexPattern="^[1-9]{1}[0-9]{3} ?[A-Z]{2}$")
    		final String textValue,
    		@ParameterLayout(named="weight")
    		final Integer weight
    		){
    	profileElementTexts.newProfileElementText("lokatie", weight, textValue, ProfileElementType.LOCATION, this);
    	return this;
    }
    
    public boolean hideNewLocation(final String textValue, final Integer weight){
    	
        QueryDefault<ProfileElementText> query = 
                QueryDefault.create(
                        ProfileElementText.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return true;
        }
    	
    	// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    	if (this.profileType == ProfileType.PERSON_PROFILE || this.profileType == ProfileType.ORGANISATION_PROFILE){
    		return false;
    	}
    	
    	return true;
    }
    
    public String validateNewLocation(final String textValue, final Integer weight){
    	
        QueryDefault<ProfileElementText> query = 
                QueryDefault.create(
                        ProfileElementText.class, 
                    "findProfileElementOfType",
                    "profileElementOwner", this, "profileElementType", ProfileElementType.LOCATION);
        if (container.firstMatch(query) != null) {
            return "Je hebt al een lokatie opgegeven.";
        }
    	
    	// alleen op ProfileType.PERSON_PROFILE en ORGANISATION_PROFILE
    	if (this.profileType != ProfileType.PERSON_PROFILE && this.profileType != ProfileType.ORGANISATION_PROFILE){
    		return "Alleen op persoons of organisatie profiel.";
    	}
    	
    	//textValue moet een geldig postcode formaat zijn
    	
    	
    	return null;
    }
    
    //XTALUS VOOR CURSUSSEN
    
    //**PRIJS*//    
    //BUSINESSRULE
    //Er kan maar een prijs element zijn
    //Alleen op cursusprofiel
    
    public ProfileElementNumeric newProfileElementPrice(
            @ParameterLayout(named="numericValue")
            final Integer numericValue
            ){
        return profileElementNumerics.newProfileElementNumeric(
                "Prijs in credits", 
                10,
                numericValue,
                ProfileElementType.NUMERIC, 
                this);
    }
    
    public boolean hideNewProfileElementPrice(
            final Integer numericValue
            ){
        
        if (this.getProfileType() != ProfileType.COURSE_PROFILE){
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
    
    public String validateNewProfileElementPrice(
            final Integer numericValue
            ){
        
        if (this.getProfileType() != ProfileType.COURSE_PROFILE){
            return "Kan alleen op Cursus profiel";
        }
        
        QueryDefault<ProfileElementNumeric> query = 
                QueryDefault.create(
                        ProfileElementNumeric.class, 
                    "findProfileElementNumericByOwnerProfile",
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Er is al een prijs";
        }
        
        return null;
    }

    
    // Region actions
    public Profile EditProfileName(
            @ParameterLayout(named="profileName")
            String newString
            ){
        this.setProfileName(newString);
        return this;
    }
    
    public String default0EditProfileName() {
        return getProfileName();
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Profile EditWeight(
            Integer newInteger
            ){
        this.setWeight(newInteger);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    public Actor DeleteProfile( 
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
        boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Profile deleted");
        return this.getActorOwner();
            
    }

    public String validateDeleteProfile(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE, named="NewDropDownTest")
    public Profile newProfileElementDropDown(
            final Integer weight,
            final DropDownForProfileElement dropDown
            ){
        profileElementDropDowns.newProfileElementDropDown(
                "kwaliteit " + dropDown.title(), 
                weight,
                dropDown,
                ProfileElementType.QUALITY, 
                this);
        return this;
    }
    
    public List<DropDownForProfileElement> autoComplete1NewProfileElementDropDown(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }

    
    @ActionLayout(hidden=Where.EVERYWHERE, named="NewDropDownAndTextTest")
    public ProfileElementDropDownAndText newProfileElementDropDownAndText(
            final String description,
            final Integer weight,
            @Parameter(optional=Optionality.TRUE)
            final DropDownForProfileElement dropDown,
            @Parameter(optional=Optionality.TRUE)
            final String text
            ){
        return profileElementDropDownsAndTexts.newProfileElementDropDownAndText(
                description, 
                weight,
                dropDown,
                text,
                ProfileElementType.QUALITY, 
                this);
    }
    
    public List<DropDownForProfileElement> autoComplete2NewProfileElementDropDownAndText(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE, named="NewTextTest")
    public ProfileElementText newProfileElementText(
            final String description,
            final Integer weight,
            final String textValue
            ){
        return profileElementTexts.newProfileElementText(
                description, 
                weight,
                textValue,
                ProfileElementType.TEXT, 
                this);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE, named="NewNumericTest")
    public ProfileElementNumeric newProfileElementNumeric(
            final String description,
            final Integer weight,
            final Integer numericValue
            ){
        return profileElementNumerics.newProfileElementNumeric(
                description, 
                weight,
                numericValue,
                ProfileElementType.NUMERIC, 
                this);
    }
       
    
    //Profile Elements ///////////////////////////////////////////////////////////////////////////////
    private SortedSet<ProfileElement> profileElement = new TreeSet<ProfileElement>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    public SortedSet<ProfileElement> getProfileElement() {
        return profileElement;
    }
    
    public void setProfileElement(final SortedSet<ProfileElement> vac) {
        this.profileElement = vac;
    }

    //Assessments ///////////////////////////////////////////////////////////////////////////////
    private SortedSet<ProfileAssessment> assessments = new TreeSet<ProfileAssessment>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    public SortedSet<ProfileAssessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(final SortedSet<ProfileAssessment> assessment) {
        this.assessments = assessment;
    }

    public boolean hideAssessments() {
        return this.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
   
    //HELPERS
    
    public String toString() {
        return "Profiel: " + this.profileName;
    }
    
//    // Used in case owner chooses identical vacancyDescription and weight
//    @SuppressWarnings("unused")
//    private String profileId;
//
//    @ActionLayout(hidden=Where.EVERYWHERE)
//    public String getProfileId() {
//        if (this.getId() != null) {
//            return this.getId();
//        }
//        return "";
//    }
//    
//    public void setProfileId() {
//        this.profileId = this.getId();
//    }
    
    
    // Injects

	@javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @Inject
    ProfileElementTags profileElementTags;
    
    @Inject
    Tags tags;
    
    @Inject
    TagCategories tagCategories;
    
    @Inject
    ProfileElementDropDownAndTexts profileElementDropDownsAndTexts;
    
    @Inject
    ProfileElementTexts profileElementTexts;
    
    @Inject
    ProfileElementNumerics profileElementNumerics;
    
}