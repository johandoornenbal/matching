package info.matchingservice.dom.Match;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.VacancyProfile;

@DomainService(menuOrder = "100", repositoryFor = ProfileMatch.class)
@Named("Matches")
public class ProfileMatches extends MatchingDomainService<ProfileMatch> {

    public ProfileMatches() {
        super(ProfileMatches.class, ProfileMatch.class);
    }

    @Named("Alle vastgelegde matches")
    public List<ProfileMatch> allProfileMatches() {
        return container.allInstances(ProfileMatch.class);
    }
    
    @Hidden
    public ProfileMatch newProfileMatch(
            @Named("Eigenaar")
            Actor ownerActor,
            @Named("Gevonden kandidaat")
            Actor vacancyCandidate,
            @Named("Stoel")
            VacancyProfile vacancyProfile
            ){
        return newProfileMatch(ownerActor, vacancyCandidate, vacancyProfile, currentUserName(), CandidateStatus.CANDIDATE);
    }
    
    //Region> Helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic
    public ProfileMatch newProfileMatch(
            Actor ownerActor,
            Actor vacancyCandidate,
            VacancyProfile vacancyProfile,
            String owner,
            CandidateStatus candidateStatus
            ){
        ProfileMatch newMatch = newTransientInstance(ProfileMatch.class);
        newMatch.setOwnerActor(ownerActor);
        newMatch.setVacancyCandidate(vacancyCandidate);
        newMatch.setVacancyProfile(vacancyProfile);
        newMatch.setOwnedBy(owner);
        newMatch.setCandidateStatus(candidateStatus);
        persist(newMatch);
        return newMatch;
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
