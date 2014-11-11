package info.matchingservice.dom.Match;

import java.util.List;

import javax.inject.Inject;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Need.Vacancies;
import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;

@DomainService(menuOrder = "70", repositoryFor = Match.class)
@Named("Matches")
public class Matches extends MatchingDomainService<Match> {

    public Matches() {
        super(Matches.class, Match.class);
    }
    
    public List<Match> allMatches() {
        return allInstances();
    }
    
    public Match newMatch(
            final Vacancy matchInitiator,
            final Profile matchingProfile,
            final @Named("calculated macthing value") Integer matchingValue
            ){
        Match newMatch = newTransientInstance(Match.class);
        newMatch.setMatchInitiator(matchInitiator);
        newMatch.setMatchingProfile(matchingProfile);
        newMatch.setCalculatedMatchingValue(matchingValue);
        persist(newMatch);
        return newMatch;
    }
    
    public List<Vacancy> autoComplete0NewMatch(final String search) {
        return vacancies.allVacancies();
    }
    
    public List<Profile> autoComplete1NewMatch(final String search) {
        return profiles.allProfiles();
    }
    
    //Injects
    
    @Inject
    Vacancies vacancies;

    @Inject
    Profiles profiles;
}
