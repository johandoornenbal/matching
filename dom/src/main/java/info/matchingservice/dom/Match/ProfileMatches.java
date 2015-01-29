package info.matchingservice.dom.Match;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Where;

@DomainService(repositoryFor = ProfileMatch.class)
@DomainServiceLayout(named="Matches", menuOrder="100")
public class ProfileMatches extends MatchingDomainService<ProfileMatch> {

    public ProfileMatches() {
        super(ProfileMatches.class, ProfileMatch.class);
    }

    @ActionLayout(named="Alle vastgelegde matches")
    public List<ProfileMatch> allProfileMatches() {
        return container.allInstances(ProfileMatch.class);
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public ProfileMatch newProfileMatch(
            @ParameterLayout(named="ownerActor")
            Actor ownerActor,
            @ParameterLayout(named="supplyCandidate")
            Actor vacancyCandidate,
            @ParameterLayout(named="demandProfile")
            Profile vacancyProfile,
            @ParameterLayout(named="matchingSupplyProfile")
            Profile matchingSupplyProfile
            ){
        return newProfileMatch(ownerActor, vacancyCandidate, vacancyProfile, matchingSupplyProfile, currentUserName(), CandidateStatus.CANDIDATE);
    }
    
    //Region> Helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic
    public ProfileMatch newProfileMatch(
            Actor ownerActor,
            Actor supplyCandidate,
            Profile demandProfile,
            Profile matchingSupplyProfile,
            String owner,
            CandidateStatus candidateStatus
            ){
        ProfileMatch newMatch = newTransientInstance(ProfileMatch.class);
        final UUID uuid=UUID.randomUUID();
        newMatch.setUniqueItemId(uuid);
        newMatch.setOwnerActor(ownerActor);
        newMatch.setSupplyCandidate(supplyCandidate);
        newMatch.setDemandProfile(demandProfile);
        newMatch.setMatchingSupplyProfile(matchingSupplyProfile);
        newMatch.setOwnedBy(owner);
        newMatch.setCandidateStatus(candidateStatus);
        persist(newMatch);
        return newMatch;
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
