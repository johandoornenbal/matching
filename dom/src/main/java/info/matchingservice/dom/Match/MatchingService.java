package info.matchingservice.dom.Match;

import info.matchingservice.dom.Match.diff_match_patch.Diff;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Profile.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

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
    public List<Match> getMatches(VacancyProfile vacancy) {
        List<Match> matches = new ArrayList<Match>();
            for (Profile e : container.allInstances(Profile.class)) {
                //matching testFieldForMatching
                Integer matchvalue1 = dmp.match_main(e.getTestFieldForMatching(), vacancy.getTestFieldForMatching(), 1);
                LinkedList<Diff> listDifs=dmp.diff_main(e.getTestFieldForMatching(), vacancy.getTestFieldForMatching());
                dmp.diff_cleanupSemantic(listDifs);
                String diffs = dmp.diff_prettyHtml(listDifs);
                Integer measure = dmp.diff_levenshtein(listDifs);
                Integer matchValue = 100 - 10*Math.abs(vacancy.getTestFigureForMatching() - e.getTestFigureForMatching());
                // uitsluiten van dezelfde owner
                // drempelwaarde is 70
                if (matchValue >= 70 && !e.getOwnedBy().equals(vacancy.getOwnedBy())) {
                    Match matchTmp = new Match(vacancy, e, matchValue + matchvalue1);
                    matchTmp.setMatchedTextDiffs(diffs);
                    matchTmp.setmatchedTextMeasure(measure);
                    matches.add(matchTmp);
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
    
    @Inject
    private diff_match_patch dmp = new diff_match_patch();
}
