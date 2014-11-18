package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
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
public class ProfileElement extends MatchingSecureMutableObject<ProfileElement> {

    public ProfileElement() {
        super("ownedBy, profileElementDescription");
    }
    
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
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
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
    
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    private String profileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Profiel element beschrijving")
    public String getProfileElementDescription(){
        return profileElementDescription;
    }
    
    public void setProfileElementDescription(final String description) {
        this.profileElementDescription = description;
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
    
    /// Helpers
    
    public String toString(){
        return this.profileElementDescription;
    }

}
