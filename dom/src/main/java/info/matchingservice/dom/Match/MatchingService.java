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
import org.apache.isis.applib.annotation.Named;
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
    @Named("Gevonden kandidaten")
    public List<Match> getMatches(VacancyProfile vacancy) {
        
        List<Match> matches = new ArrayList<Match>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(Profile.class).isEmpty()) {
            return matches;
        }
        
//        for (Profile e : container.allInstances(Profile.class)) {
//            
//            //matching testFieldForMatching
//
//            LinkedList<Diff> listDifs=dmp.diff_main(e.getTestFieldForMatching(), vacancy.getTestFieldForMatching());
//            dmp.diff_cleanupSemantic(listDifs);
//            String diffs = dmp.diff_prettyHtml(listDifs);
//            Integer measure = dmp.diff_levenshtein(listDifs);
//            Integer matchValue2 = 100 - 10*Math.abs(vacancy.getTestFigureForMatching() - e.getTestFigureForMatching());
//            Integer matchValue = matchValue2 + 100/(1+measure);
//            // uitsluiten van dezelfde owner
//            // drempelwaarde is 70
//            if (matchValue >= 70 && !e.getOwnedBy().equals(vacancy.getOwnedBy())) {
//                Match matchTmp = new Match(vacancy, e, matchValue);
//                matchTmp.setMatchedTextDiffs(diffs);
//                matchTmp.setmatchedTextMeasure(measure);
//                matches.add(matchTmp);
//            }
//        }
//        
//        Collections.sort(matches, new Comparator<Match>(){
//            public int compare(Match o1, Match o2) {
//                return o2.getCalculatedMatchingValue().compareTo(o1.getCalculatedMatchingValue());
//            }
//        });
        return matches;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private diff_match_patch dmp = new diff_match_patch();
}
