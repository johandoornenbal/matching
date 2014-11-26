package info.matchingservice.dom.Demand;

import info.matchingservice.dom.Profile.Profile;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MultiLine;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@AutoComplete(repository=DemandProfiles.class,  action="autoComplete")
@Immutable
public class DemandProfile extends Profile {
    
    private Demand demandProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Demand getDemandProfileOwner() {
        return demandProfileOwner;
    }
    
    public void setDemandProfileOwner(final Demand demandOwner) {
        this.demandProfileOwner = demandOwner;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    // Region actions
    public DemandProfile EditProfileName(
            @MultiLine
            String newString
            ){
        this.setProfileName(newString);
        return this;
    }
    
    public String default0EditProfileName() {
        return getProfileName();
    }
    
    public DemandProfile EditWeight(
            Integer newInteger
            ){
        this.setWeight(newInteger);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    


    // helpers
    
    public String toString() {
        return "Stoel : " + this.getProfileName();
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

}
