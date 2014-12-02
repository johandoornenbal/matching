package info.matchingservice.dom.Match;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Demand.DemandProfile;

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
                    + "WHERE ownedBy == :ownedBy && vacancyCandidate == :vacancyCandidate && vacancyProfile == :vacancyProfile")                  
})
@Immutable
public class ProfileMatch extends MatchingSecureMutableObject<ProfileMatch> {

    public ProfileMatch() {
        super("ownedBy");
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
    
    private Actor vacancyCandidate;
    
    @Named("Gevonden kandidaat")
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getVacancyCandidate() {
        return vacancyCandidate;
    }
    
    public void setVacancyCandidate(final Actor candidate) {
        this.vacancyCandidate = candidate;
    }
    
    private DemandProfile vacancyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Stoel")
    @Disabled
    public DemandProfile getVacancyProfile() {
        return vacancyProfile;
    }
    
    public void setVacancyProfile(final DemandProfile vacancyProfile){
        this.vacancyProfile = vacancyProfile;
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
    
    //Region>Actions
    
    public ProfileMatch ChangeStatus(final CandidateStatus status) {
        setCandidateStatus(status);
        return this;
    }
    
    public CandidateStatus default0ChangeStatus(final CandidateStatus status){
        return getCandidateStatus();
    }

}
