package info.matchingservice.dom.Match;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import com.google.common.collect.ComparisonChain;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Demand.DemandProfile;
import info.matchingservice.dom.Supply.SupplyProfile;

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
        super("matchInitiator");
    }

    public ProfileComparison(DemandProfile matchInitiator, DemandProfile matchingProfile, Integer value) {
        super("matchInitiator");
        this.matchInitiator = matchInitiator;
        this.matchingProfile = matchingProfile;
        this.calculatedMatchingValue = value;   
    }
    
    private DemandProfile matchInitiator;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public DemandProfile getMatchInitiator() {
        return matchInitiator;
    }
    
    public void setMatchInitiator(final DemandProfile vac) {
        this.matchInitiator = vac;
    }
    
    private DemandProfile matchingProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public DemandProfile getMatchingProfile() {
        return matchingProfile;
    }
    
    public void setMatchingProfile(final DemandProfile prof) {
        this.matchingProfile = prof;
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
//    
//    public ProfileMatch SaveMatch(){
//        return profileMatches.newProfileMatch(getMatchInitiator().getDemandProfileOwner().getDemandOwner(), getMatchingProfile().getSupplyProfileOwner(), getMatchInitiator());
//    }
    
    //TODO: uitbreiden met controle (en hideXxx ) of er al een save is gemaakt met deze kenmerken...
    // Hide if not owner or if already saved match
    public boolean hideSaveMatch() {
        QueryDefault<ProfileMatch> query = 
                QueryDefault.create(
                        ProfileMatch.class, 
                    "findProfileMatchUnique", 
                    "ownedBy", currentUserName(),
                    "vacancyCandidate", getMatchingProfile().getDemandProfileOwner(),
                    "vacancyProfile", getMatchInitiator());
        return !getMatchInitiator().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName()) || container.firstMatch(query) != null;
    }
    
    public String validateSaveMatch() {
        QueryDefault<ProfileMatch> query = 
                QueryDefault.create(
                        ProfileMatch.class, 
                    "findProfileMatchUnique", 
                    "ownedBy", currentUserName(),
                    "vacancyCandidate", getMatchingProfile().getDemandProfileOwner(),
                    "vacancyProfile", getMatchInitiator());
        if (container.firstMatch(query) != null) {
            return "You already saved this candidate for this vacancy";
        }
        if (!getMatchInitiator().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName())){
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
        return getMatchInitiator().toString() + " vs. " + getMatchingProfile().toString();
    }
    
//    public Actor getProposedPerson() {
//        return getMatchingProfile().getSupplyProfileOwner();
//    }    
    
    public int compareTo(ProfileComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfile, that.matchingProfile)
            .result();
    }
    
    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
