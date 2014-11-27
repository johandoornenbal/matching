package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Supply.SupplyProfile;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;

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
            name = "findProfileElementByOwnerProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElement "
                    + "WHERE profileElementOwner == :profileElementOwner"),
    @javax.jdo.annotations.Query(
            name = "findProfileElementByOwnerProfileAndNature", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.ProfileElement "
                    + "WHERE profileElementOwner == :profileElementOwner && profileElementNature == :profileElementNature")
})
@Immutable
public class ProfileElement extends MatchingSecureMutableObject<ProfileElement> {

    public ProfileElement() {
        super("ownedBy, profileElementDescription, profileElementOwner, profileElementId");
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
    
    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private ProfileElementNature profileElementNature;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementNature getProfileElementNature() {
        return profileElementNature;
    }
    
    public void setProfileElementNature(final ProfileElementNature nature) {
        this.profileElementNature = nature;
    }
    
    private ProfileElementType profileElementType;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public ProfileElementType getProfileElementType() {
        return profileElementType;
    }
    
    public void setProfileElementType(final ProfileElementType type) {
        this.profileElementType = type;
    }
    
    private Profile profileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Profiel")
    @Hidden(where=Where.PARENTED_TABLES)
    public Profile getProfileElementOwner() {
        return profileElementOwner;
    }
    
    public void setProfileElementOwner(final Profile vacancyProfileOwner) {
        this.profileElementOwner = vacancyProfileOwner;
    }
    
    private ProfileElementCategory profileElementCategory;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public ProfileElementCategory getProfileElementCategory(){
        return profileElementCategory;
    }
    
    public void setProfileElementCategory(final ProfileElementCategory profileElementCategory){
        this.profileElementCategory = profileElementCategory;
    }
    
    //element description /////////////////////////////////////////////////////////////////////////////////////
    
    private String profileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Profiel element beschrijving")
    @MultiLine
    public String getProfileElementDescription(){
        return profileElementDescription;
    }
    
    public void setProfileElementDescription(final String description) {
        this.profileElementDescription = description;
    }
    
    public ProfileElement EditProfileDescription(
            @Named("Beschrijving")
            @MultiLine
            String newDescr
            ){
        this.setProfileElementDescription(newDescr);
        return this;
    }
    
    public String default0EditProfileDescription() {
        return getProfileElementDescription();
    }
    
    //weight in case of owner is Demand Profile
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight){
        this.weight=weight;
    }
    
    public boolean hideWeight(){
        return (this.getProfileElementOwner() instanceof SupplyProfile);
    }
    
    public ProfileElement EditWeight(
            @Named("Gewicht")
            @MultiLine
            Integer newWeight
            ){
        this.setWeight(newWeight);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    
    public boolean hideEditWeight(){
        return (this.getProfileElementOwner() instanceof SupplyProfile);
    }
    
    //Should be set by subClasses
    private String displayValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getDisplayValue(){
        return displayValue;
    }
    
    public void setDisplayValue(final String displayValue){
        this.displayValue = displayValue;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder element")
    public Profile DeleteProfileElement(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Element verwijderd");
        return getProfileElementOwner();
    }
    
    public String validateDeleteProfileElement(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
        
    // Helpers
    
    public String toString(){
        return this.profileElementDescription;
    }
    
    // Used in case owner chooses identical description and weight
    @SuppressWarnings("unused")
    private String profileElementId;

    @Hidden
    public String getProfileElementId() {
        if (this.getId() != null) {
            return this.getId();
        }
        return "";
    }
    
    public void setProfileElementId() {
        this.profileElementId = this.getId();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
