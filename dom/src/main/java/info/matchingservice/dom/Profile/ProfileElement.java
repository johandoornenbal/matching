package info.matchingservice.dom.Profile;

import java.util.UUID;

import info.matchingservice.dom.MatchingSecureMutableObject;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
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
@DomainObject(editing=Editing.DISABLED)
public class ProfileElement extends MatchingSecureMutableObject<ProfileElement> {

    public ProfileElement() {
        super("uniqueItemId, ownedBy, profileElementDescription, profileElementOwner, profileElementId");
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
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private Profile profileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.PARENTED_TABLES)
    public Profile getProfileElementOwner() {
        return profileElementOwner;
    }
    
    public void setProfileElementOwner(final Profile vacancyProfileOwner) {
        this.profileElementOwner = vacancyProfileOwner;
    }
    
    private ProfileElementType profileElementType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileElementType getProfileElementType(){
        return profileElementType;
    }
    
    public void setProfileElementType(final ProfileElementType profileElementCategory){
        this.profileElementType = profileElementCategory;
    }
    
    //element description /////////////////////////////////////////////////////////////////////////////////////
    
    private String profileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getProfileElementDescription(){
        return profileElementDescription;
    }
    
    public void setProfileElementDescription(final String description) {
        this.profileElementDescription = description;
    }
    
    public ProfileElement EditProfileDescription(
            @ParameterLayout(named="profileElementDescription", multiLine=4)
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
    
//    public boolean hideWeight(){
//        return (this.getProfileElementOwner() instanceof SupplyProfile);
//    }
    
    public ProfileElement EditWeight(
            @ParameterLayout(named="weight")
            Integer newWeight
            ){
        this.setWeight(newWeight);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    
    //Should be set by subClasses
    private String displayValue;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @PropertyLayout(hidden=Where.OBJECT_FORMS)
    public String getDisplayValue(){
        return displayValue;
    }
    
    public void setDisplayValue(final String displayValue){
        this.displayValue = displayValue;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @ActionLayout(named="Verwijder element")
    public Profile DeleteProfileElement(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure
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

    @ActionLayout(hidden=Where.EVERYWHERE)
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
