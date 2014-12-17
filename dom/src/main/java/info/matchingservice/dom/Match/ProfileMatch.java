package info.matchingservice.dom.Match;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

/**
 * It takes an action on a Demand Profile to create and persist a ProfileMatch instance
 * 
 *
 * @version $Rev$ $Date$
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileMatchUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.ProfileMatch "
                    + "WHERE ownedBy == :ownedBy && supplyCandidate == :vacancyCandidate && demandProfile == :vacancyProfile")                  
})
@Immutable
public class ProfileMatch extends MatchingSecureMutableObject<ProfileMatch> {

    public ProfileMatch() {
        super("ownedBy, supplyCandidate, demandProfile, candidateStatus");
    }
    
    public String title(){
        return "Opgeslagen profiel match van " + this.getSupplyCandidate().title();
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
    
    private Actor ownerActor;
    
    @Named("Eigenaar")
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getOwnerActor() {
        return ownerActor;
    }
    
    public void setOwnerActor(final Actor ownerActor) {
        this.ownerActor = ownerActor;
    }
    
    private Actor supplyCandidate;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getSupplyCandidate() {
        return supplyCandidate;
    }
    
    public void setSupplyCandidate(final Actor candidate) {
        this.supplyCandidate = candidate;
    }
    
    private Profile demandProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vacancyProfile){
        this.demandProfile = vacancyProfile;
    }
    
    private CandidateStatus candidateStatus;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Status")
    public CandidateStatus getCandidateStatus() {
        return candidateStatus;
    }
    
    public void setCandidateStatus(final CandidateStatus candidateStatus){
        this.candidateStatus = candidateStatus;
    }
    
    private Profile matchingSupplyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getMatchingSupplyProfile(){
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile matchingSupplyProfile){
        this.matchingSupplyProfile = matchingSupplyProfile;
    }
    
    //Region>Actions
    
    public ProfileMatch ChangeStatus(final CandidateStatus status) {
        setCandidateStatus(status);
        return this;
    }
    
    public CandidateStatus default0ChangeStatus(final CandidateStatus status){
        return getCandidateStatus();
    }
    
    public Profile deleteMatch(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        return this.getDemandProfile();
    }
    
    public String validateDeleteMatch(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
// Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
