package info.matchingservice.dom.Match;

import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Profile.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.Render.Type;

@DomainService
public class MatchingService {
    
    // return matches on vacancy
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    public List<Match> getMatches(Vacancy vacancy) {
        List<Match> matches = new ArrayList<Match>();
            for (Profile e : container.allInstances(Profile.class)) {
                Integer matchValue = 100 - 10*Math.abs(vacancy.getTestFigureForMatching() - e.getTestFigureForMatching());
                // uitsluiten van dezelfde owner
                // drempelwaarde is 70
                if (matchValue >= 70 && !e.getOwnedBy().equals(vacancy.getOwnedBy())) {
                    matches.add(new Match(vacancy, e, matchValue));
                }
            }
            Collections.sort(matches, new Comparator<Match>(){
                public int compare(Match o1, Match o2) {
                    return o2.getCalculatedMatchingValue().compareTo(o1.getCalculatedMatchingValue());
                }
            });
        return matches;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
