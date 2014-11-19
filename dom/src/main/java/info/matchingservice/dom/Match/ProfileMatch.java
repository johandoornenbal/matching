package info.matchingservice.dom.Match;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.VacancyProfile;

/**
 * It takes an action on a VacancyProfile to create and persist a ProfileMatch instance
 * 
 *
 * @version $Rev$ $Date$
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
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
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getOwnerActor() {
        return ownerActor;
    }
    
    public void setOwnerActor(final Actor ownerActor) {
        this.ownerActor = ownerActor;
    }
    
    private Actor vacancyCandidate;
    
    @Named("Gevonden kandidaat")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getVacancyCandidate() {
        return vacancyCandidate;
    }
    
    public void setVacancyCandidate(final Actor candidate) {
        this.vacancyCandidate = candidate;
    }
    
    private VacancyProfile vacancyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Stoel")
    public VacancyProfile getVacancyProfile() {
        return vacancyProfile;
    }
    
    public void setVacancyProfile(final VacancyProfile vacancyProfile){
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

}
