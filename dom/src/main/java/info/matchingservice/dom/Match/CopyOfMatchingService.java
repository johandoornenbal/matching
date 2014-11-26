package info.matchingservice.dom.Match;

import org.apache.isis.applib.AbstractService;

//@DomainService
public class CopyOfMatchingService extends AbstractService {
    
//    // This threshold is applied twice!! First for filtering elements on ElementComparison and later on for filtering totalMatch on ProfileComparison 
//    final Integer MATCHING_THRESHOLD = 70;
//    
//    //return matches on vacancyProfileElement
//    @NotInServiceMenu
//    @NotContributed(As.ACTION)
//    @ActionSemantics(Of.SAFE)
//    @Render(Type.EAGERLY)
//    @Named("Gevonden matching profiel elementen")
//    public List<ElementComparison> getElementMatches(VacancyProfileFigureElement element){
//        
//        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
//        
//        //Init Test: Only if there are any Profiles
//        if (container.allInstances(ProfileFigureElement.class).isEmpty()) {
//            return elementMatches;
//        }
//        
//            for (ProfileFigureElement e : container.allInstances(ProfileFigureElement.class)) {
//                if (e.getProfileElementType() == ProfileElementType.MATCHABLE_FIGURE){
//                    // uitsluiten van dezelfde owner
//                    // drempelwaarde is MATCHING_THRESHOLD
//                    Integer matchValue = 100 - 10*Math.abs(element.getFigure() - e.getFigure());
//                    if (matchValue >= MATCHING_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
//                        ElementComparison matchTmp = new ElementComparison(element.getVacancyProfileElementOwner(), element, e, e.getProfileElementOwner().getProfileOwner() ,matchValue);
//                        elementMatches.add(matchTmp);
//                    }
//                }
//            }
//        
//        return elementMatches;
//    }
//    
//    @NotInServiceMenu
//    @NotContributed(As.ACTION)
//    @ActionSemantics(Of.SAFE)
//    @Render(Type.EAGERLY)
//    @Named("Gevonden kandidaten")
//    public List<ProfileComparison> getMatches(DemandProfile vacancy) {
//        List<ProfileComparison> matches = new ArrayList<ProfileComparison>();
//        //Init Test: Only if there are any Profiles
//        if (container.allInstances(SupplyProfile.class).isEmpty()) {
//            return matches;
//        }
//        //For every Profile
//        for (SupplyProfile profile: container.allInstances(SupplyProfile.class)) {
//            
//            //Actually for every ProfileOwner (Actor)
//            Actor tempProfileOwner = profile.getProfileOwner();
//            
//            //TempElement ProfileComparison with matchingvalue 0
//            ProfileComparison tempMatch = new ProfileComparison(vacancy, profile, 0);
//            Integer elementCounter = 0;
//            
//            //For every figureElement on Vacancy
//            for (DemandProfileElement vpelement: vacancy.getDemandProfileElement()){
//                
//                //For all elementmatches on figures with tempProfileOwner as ProfileOwner
//                if (vpelement.getProfileElementType() == ProfileElementType.MATCHABLE_FIGURE){
//                    List<ElementComparison> templist = getElementMatches((VacancyProfileFigureElement) vpelement);
//                    for (ElementComparison e: templist){
//                        //only if tempProfileOwner is ProfileOwner
//                        if (e.getMatchingProfileOwner().equals(tempProfileOwner)){
//                            //Add to matching value
//                            tempMatch.setCalculatedMatchingValue(tempMatch.getCalculatedMatchingValue() + e.getCalculatedMatchingValue());
//                            //Add 1 to elementCounter
//                            elementCounter ++;
//                        }
//                    }
//                }
//            }
//            // average matching value
//            if (elementCounter > 0){
//                tempMatch.setCalculatedMatchingValue(tempMatch.getCalculatedMatchingValue()/elementCounter);
//            }
//            // drempelwaarde is MATCHING_THRESHOLD
//            if (tempMatch.getCalculatedMatchingValue() > MATCHING_THRESHOLD){
//                final ProfileComparison defMatch = newTransientInstance(ProfileComparison.class);
//                tempMatch.setMatchInitiator(tempMatch.getMatchInitiator());
//                tempMatch.setMatchingProfile(tempMatch.getMatchingProfile());
//                tempMatch.setCalculatedMatchingValue(tempMatch.getCalculatedMatchingValue());
//                persistIfNotAlready(defMatch);
//                matches.add(tempMatch);
//            }
//            
//            
//            
//        }
//        
//        return matches;
//    }
//    
//    private class CompareWeight {
//        Integer elementCounter;
//        Integer weight;
//        Integer matchingValue;
//    }
//    
////    // return matches on vacancyProfile
////    @NotInServiceMenu
////    @NotContributed(As.ACTION)
////    @ActionSemantics(Of.SAFE)
////    @Render(Type.EAGERLY)
////    @Named("Gevonden kandidaten")
////    public List<ProfileComparison> getMatches(VacancyProfile vacancy) {
////        
////        List<ProfileComparison> matches = new ArrayList<ProfileComparison>();
////        
////        //Init Test: Only if there are any Profiles
////        if (container.allInstances(Profile.class).isEmpty()) {
////            return matches;
////        }
////        
//////        for (Profile e : container.allInstances(Profile.class)) {
//////            
//////            //matching testFieldForMatching
//////
//////            LinkedList<Diff> listDifs=dmp.diff_main(e.getTestFieldForMatching(), vacancy.getTestFieldForMatching());
//////            dmp.diff_cleanupSemantic(listDifs);
//////            String diffs = dmp.diff_prettyHtml(listDifs);
//////            Integer measure = dmp.diff_levenshtein(listDifs);
//////            Integer matchValue2 = 100 - 10*Math.abs(vacancy.getTestFigureForMatching() - e.getTestFigureForMatching());
//////            Integer matchValue = matchValue2 + 100/(1+measure);
//////            // uitsluiten van dezelfde owner
//////            // drempelwaarde is 70
//////            if (matchValue >= 70 && !e.getOwnedBy().equals(vacancy.getOwnedBy())) {
//////                Match matchTmp = new Match(vacancy, e, matchValue);
//////                matchTmp.setMatchedTextDiffs(diffs);
//////                matchTmp.setmatchedTextMeasure(measure);
//////                matches.add(matchTmp);
//////            }
//////        }
//////        
//////        Collections.sort(matches, new Comparator<Match>(){
//////            public int compare(Match o1, Match o2) {
//////                return o2.getCalculatedMatchingValue().compareTo(o1.getCalculatedMatchingValue());
//////            }
//////        });
////        return matches;
////    }
//    
//    // Region>injections ////////////////////////////
//    @javax.inject.Inject
//    private DomainObjectContainer container;
//    
//    @Inject
//    private diff_match_patch dmp = new diff_match_patch();
}
