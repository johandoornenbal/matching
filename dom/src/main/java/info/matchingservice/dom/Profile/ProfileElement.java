package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

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
    
    private SupplyProfile profileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Profiel")
    @Hidden(where=Where.PARENTED_TABLES)
    public SupplyProfile getProfileElementOwner() {
        return profileElementOwner;
    }
    
    public void setProfileElementOwner(final SupplyProfile vacancyProfileOwner) {
        this.profileElementOwner = vacancyProfileOwner;
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
    
    @Named("Bewerk beschrijving")
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
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder element")
    public SuperProfile DeleteProfileElement(
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
