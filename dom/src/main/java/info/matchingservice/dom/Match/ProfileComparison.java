package info.matchingservice.dom.Match;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import com.google.common.collect.ComparisonChain;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.query.QueryDefault;


@ViewModel
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
public class ProfileComparison extends MatchingDomainObject<ProfileComparison> {
    
    public ProfileComparison() {
        super("demandProfile");
    }

    public ProfileComparison(Profile demandProfile, Profile matchingSupplyProfile, Integer value) {
        super("matchInitiator");
        this.demandProfile = demandProfile;
        this.matchingSupplyProfile = matchingSupplyProfile;
        this.calculatedMatchingValue = value;   
    }
    
    private Profile demandProfile;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vac) {
        this.demandProfile = vac;
    }
    
    private Profile matchingSupplyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getMatchingSupplyProfile() {
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile prof) {
        this.matchingSupplyProfile = prof;
    }
    
    private Integer calculatedMatchingValue;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    //Region>Actions ////////////////////////////////////////////////////////
    
    public ProfileMatch SaveMatch(){
        return profileMatches.newProfileMatch(getDemandProfile().getDemandProfileOwner().getDemandOwner(), getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner(), getDemandProfile());
    }
    
    //TODO: uitbreiden met controle (en hideXxx ) of er al een save is gemaakt met deze kenmerken...
    // Hide if not owner or if already saved match
    public boolean hideSaveMatch() {
        QueryDefault<ProfileMatch> query = 
                QueryDefault.create(
                        ProfileMatch.class, 
                    "findProfileMatchUnique", 
                    "ownedBy", currentUserName(),
                    "vacancyCandidate", getMatchingSupplyProfile().getSupplyProfileOwner(),
                    "vacancyProfile", getDemandProfile());
        return !getDemandProfile().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName()) || container.firstMatch(query) != null;
    }
    
    public String validateSaveMatch() {
        QueryDefault<ProfileMatch> query = 
                QueryDefault.create(
                        ProfileMatch.class, 
                    "findProfileMatchUnique", 
                    "ownedBy", currentUserName(),
                    "vacancyCandidate", getMatchingSupplyProfile().getSupplyProfileOwner(),
                    "vacancyProfile", getDemandProfile());
        if (container.firstMatch(query) != null) {
            return "You already saved this candidate for this vacancy";
        }
        if (!getDemandProfile().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName())){
            return "Sorry, you are not the owner of this match";
        } else {
            return null;
        }
            
    }
    
    
    //Region>Helpers ////////////////////////////////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getDemandProfile().toString() + " vs. " + getMatchingSupplyProfile().toString();
    }
    
    public Actor getProposedPerson() {
        return getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner();
    }    
    
    public int compareTo(ProfileComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingSupplyProfile, that.matchingSupplyProfile)
            .result();
    }
    
    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
