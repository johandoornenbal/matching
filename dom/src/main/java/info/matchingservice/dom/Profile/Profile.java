package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.ProfileAssessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;

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
                    + "WHERE supplyProfileOwner != null && profileType == :profileType && ownedBy == :ownedBy")
})
public class Profile extends MatchingSecureMutableObject<Profile> {
    
    public Profile() {
        super("profileName, ownedBy");
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
    private String ownedBy;

    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //Immutables //////////////////////////////////////////////////////////////////////////////////////
    
    private ProfileType profileType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileType getProfileType() {
        return profileType;
    }
    
    public void setProfileType(final ProfileType profileType){
        this.profileType = profileType;
    }    
    
    private Demand demandProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    public Demand getDemandProfileOwner(){
        return demandProfileOwner;
    }
    
    public void setDemandProfileOwner(final Demand demandProfileOwner){
        this.demandProfileOwner = demandProfileOwner;
    }
    
    private Supply supplyProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Disabled
    public Supply getSupplyProfileOwner(){
        return supplyProfileOwner;
    }
    
    public void setSupplyProfileOwner(final Supply supplyProfileOwner){
        this.supplyProfileOwner = supplyProfileOwner;
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
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    
    // Region> ProfileElements
    
    //XTALUS VOOR PERSONEN
    
    //**KWALITEITEN*//
    //BUSINESSRULE
    // alleen tonen op profile van type PERSON
    // 2 dezelfde kwaliteiten kiezen heeft geen zin
    
    @Named("Nieuwe kwaliteit")
    public Profile newQualityElementDropDown(
            @Named("Gewicht")
            final Integer weight,
            @Named("Zoek kwaliteit door te beginnen met typen")
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
    
    public List<DropDownForProfileElement> autoComplete1NewQualityElementDropDown(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }
    
    public boolean hideNewQualityElementDropDown(
            final Integer weight,
            final DropDownForProfileElement dropDown
            ){
        
        if (this.getProfileType() != ProfileType.PERSON_PROFILE){
            return true;
        }
        
        return false;
    }
    
    public String validateNewQualityElementDropDown(
            final Integer weight,
            final DropDownForProfileElement dropDown
            ){
        
        if (this.getProfileType() != ProfileType.PERSON_PROFILE){
            return "Alleen op persoons profiel";
        }
        
        // twee dezelfde kwaliteiten heeft geen zin
        QueryDefault<ProfileElementDropDown> query = 
                QueryDefault.create(
                        ProfileElementDropDown.class, 
                    "findProfileElementDropDownByOwnerProfileAndDropDownValue", 
                    "dropDownValue", dropDown,
                    "profileElementOwner", this);
        if (container.firstMatch(query) != null) {
            return "Deze kwaliteit heb je al gekozen";
        }
        
        return null;
    }
    
    //XTALUS VOOR CURSUSSEN
    
    //**PRIJS*//    
    //BUSINESSRULE
    //Er kan maar een prijs element zijn
    //Alleen op cursusprofiel
    
    @Named("Nieuwe prijs")
    public ProfileElementNumeric newProfileElementPrice(
            @Named("Prijs (in hele 'credits'")
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
            @MultiLine
            String newString
            ){
        this.setProfileName(newString);
        return this;
    }
    
    public String default0EditProfileName() {
        return getProfileName();
    }
    
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
    public Actor DeleteProfile(@Optional @Named("Verwijderen OK?") boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Profile deleted");
        if (this.demandProfileOwner!=null){
            return this.getDemandProfileOwner().getProfileOwnerIsOwnedBy();
        } else {
            return this.getSupplyProfileOwner().getProfileOwnerIsOwnedBy();
        }
            
    }

    public String validateDeleteProfile(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    @Hidden
    @Named("NewDropDownTest")
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

    
    @Hidden
    @Named("NewDropDownAndTextTest")
    public ProfileElementDropDownAndText newProfileElementDropDownAndText(
            final String description,
            final Integer weight,
            @Optional
            final DropDownForProfileElement dropDown,
            @Optional
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
    
    @Hidden
    @Named("NewTextTest")
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
    
    @Hidden
    @Named("NewNumericTest")
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

    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    @Named("Profiel elementen")
    public SortedSet<ProfileElement> getProfileElement() {
        return profileElement;
    }
    
    public void setProfileElement(final SortedSet<ProfileElement> vac) {
        this.profileElement = vac;
    }

    //Assessments ///////////////////////////////////////////////////////////////////////////////
    private SortedSet<ProfileAssessment> assessments = new TreeSet<ProfileAssessment>();

    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
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
    
    // Used in case owner chooses identical vacancyDescription and weight
    @SuppressWarnings("unused")
    private String profileId;

    @Hidden
    public String getProfileId() {
        if (this.getId() != null) {
            return this.getId();
        }
        return "";
    }
    
    public void setProfileId() {
        this.profileId = this.getId();
    }
    
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    DropDownForProfileElements dropDownForProfileElements;
    
    @Inject
    ProfileElementDropDowns profileElementDropDowns;
    
    @Inject
    ProfileElementDropDownAndTexts profileElementDropDownsAndTexts;
    
    @Inject
    ProfileElementTexts profileElementTexts;
    
    @Inject
    ProfileElementNumerics profileElementNumerics;
    
}