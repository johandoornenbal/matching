package info.matchingservice.dom.Supply;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@AutoComplete(repository=SupplyProfiles.class,  action="autoComplete")
@Immutable
public class SupplyProfile extends Profile {
    
    private Supply supplyProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Supply getSupplyProfileOwner() {
        return supplyProfileOwner;
    }
    
    public void setSupplyProfileOwner(final Supply supplyProfileOwner) {
        this.supplyProfileOwner = supplyProfileOwner;
    }
    
    // Region actions 
    //edit action /////////////////////////////////////////////////////////////////////////////////////
    public SupplyProfile EditProfileName(
            @MultiLine
            String newString
            ){
        this.setProfileName(newString);
        return this;
    }
    
    public String default0EditProfileName() {
        return getProfileName();
    }
    

    //delete action /////////////////////////////////////////////////////////////////////////////////////
    public Actor DeleteProfile(@Optional @Named("Verwijderen OK?") boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Profile deleted");
        return this.getSupplyProfileOwner().getSupplyOwner();
    }

    public String validateDeleteProfile(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }

    // helpers
    
    public String toString() {
        return "Aanbod profiel : " + this.getProfileName();
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

}
