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
            name = "allDemandProfiles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE demandProfileOwner != null")                  
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
    @Named("NewDropDownTest")
    public ProfileElementDropDown newProfileElementDropDown(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown
            ){
        return profileElementDropDowns.newProfileElementDropDown(
                description, 
                weight,
                dropDown,
                ProfileElementCategory.QUALITY, 
                this);
    }
    
    public List<DropDownForProfileElement> autoComplete2NewProfileElementDropDown(String search) {
        return dropDownForProfileElements.findDropDowns(search);
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
                ProfileElementCategory.QUALITY, 
                this);
    }
    
    public List<DropDownForProfileElement> autoComplete2NewProfileElementDropDownAndText(String search) {
        return dropDownForProfileElements.findDropDowns(search);
    }
    
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
                ProfileElementCategory.TEXT, 
                this);
    }
    
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
                ProfileElementCategory.NUMERIC, 
                this);
    }
       
    
//    @Named("Nieuw (single) profiel element")
//    @Hidden
//    public Profile newProfileElement(
//                @Named("Profiel element beschrijving")
//                final String profileElementDescription) {
//        newProfileElement(profileElementDescription, this, currentUserName());
//        return this;
//    }
//    
//    @Programmatic
//    public void newDropdownElement(final Quality keyword, final Profile profileElementOwner, final String ownedBy) {
//        profileFigureElements.newDropdownElement(keyword, profileElementOwner, ownedBy, ProfileElementNature.MULTI_ELEMENT);
//    }
//
//    public boolean hideNewProfileElement(final String profileElementDescription) {
//        return hideNewProfileElement(profileElementDescription, this);
//    }
//
//    public String validateNewProfileElement(final String profileElementDescription) {
//        return validateNewProfileElement(profileElementDescription, this);
//    }
//    
//    @Programmatic
//    public void newProfileElement(final String profileElementDescription, final Profile profileElementOwner, final String ownedBy) {
//        profileFigureElements.newProfileElement(profileElementDescription, profileElementOwner, ownedBy, ProfileElementNature.SINGLE_ELEMENT);
//    }
//
//    @Programmatic
//    public boolean hideNewProfileElement(final String profileElementDescription, final Profile profileElementOwner) {
//        // if you have already profile
//        QueryDefault<ProfileElement> query = 
//                QueryDefault.create(
//                        ProfileElement.class, 
//                    "findProfileElementByOwnerProfileAndNature", 
//                    "profileElementOwner", profileElementOwner,
//                    "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
//        return container.firstMatch(query) != null ?
//                true        
//                :false;
//    }
//
//    @Programmatic
//    public String validateNewProfileElement(final String profileElementDescription, final Profile profileElementOwner) {
//        // if you have already profile
//        QueryDefault<ProfileElement> query = 
//                QueryDefault.create(
//                        ProfileElement.class, 
//                        "findProfileElementByOwnerProfileAndNature", 
//                        "profileElementOwner", profileElementOwner,
//                        "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
//        return container.firstMatch(query) != null?
//                "This VacancyProfile has this single element already!"        
//                :null;
//    }
//
//    @Programmatic
//    public void newFigureElement(final String profileElementDescription, final Integer figure, final Profile profileElementOwner, final String ownedBy) {
//        pe_figures.newProfileElement(profileElementDescription, figure, profileElementOwner, ownedBy);
//    }
//
//
//
//    @Named("Nieuw kwaliteiten element")
//    public Profile newDropdownElement(@Named("Keyword")
//                final Quality keyword) {
//        newDropdownElement(keyword, this, currentUserName());
//        return this;
//    }
//
//    public List<Quality> autoComplete0NewDropdownElement(String search) {
//        return qualities.findQualities(search);
//    }
//    
//    @Named("Nieuw getal element")
//    public Profile newFigureElement(@Named("Profiel element beschrijving")
//                final String profileElementDescription, @Named("Getal")
//                final Integer figure) {
//        newFigureElement(profileElementDescription, figure, this, currentUserName());
//        return this;
//    }


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