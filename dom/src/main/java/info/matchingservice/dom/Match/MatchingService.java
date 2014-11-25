package info.matchingservice.dom.Match;

import info.matchingservice.dom.ProfileElementType;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Need.DemandProfile;
import info.matchingservice.dom.Need.VacancyProfileDropDownElement;
import info.matchingservice.dom.Need.DemandProfileElement;
import info.matchingservice.dom.Need.VacancyProfileFigureElement;
import info.matchingservice.dom.Profile.ProfileDropDownElement;
import info.matchingservice.dom.Profile.ProfileFigureElement;
import info.matchingservice.dom.Profile.SupplyProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;

@DomainService
public class MatchingService extends AbstractService {
    
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 70;
    final Integer MATCHING_PROFILE_THRESHOLD = 50;
    
    //FIGURES//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //return matches on vacancyProfileElement of Figure elements
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden matching profiel elementen")
    public List<ElementComparison> getElementMatches(VacancyProfileFigureElement element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileFigureElement.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileFigureElement e : container.allInstances(ProfileFigureElement.class)) {
            if (e.getProfileElementType() == ProfileElementType.MATCHABLE_FIGURE){
                // uitsluiten van dezelfde owner
                // drempelwaarde is MATCHING_THRESHOLD
                Integer matchValue = 100 - 10*Math.abs(element.getFigure() - e.getFigure());
                if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                    ElementComparison matchTmp = new ElementComparison(element.getVacancyProfileElementOwner(), element, e, e.getProfileElementOwner().getProfileOwner() ,matchValue);
                    elementMatches.add(matchTmp);
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    //DROPDOWNS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //return matches on vacancyProfileElement of DROPDOWN elements
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden matching profiel elementen")
    public List<ElementComparison> getDropDownElementMatches(VacancyProfileDropDownElement element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileDropDownElement.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileDropDownElement e : container.allInstances(ProfileDropDownElement.class)) {
            if (e.getProfileElementType() == ProfileElementType.MATCHABLE_DROPDOWN){
                // uitsluiten van dezelfde owner
                // drempelwaarde is MATCHING_THRESHOLD
                Integer matchValue=0;
                if (element.getKeyword().equals(e.getKeyword())){
                    matchValue=100;
                }
                if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                    ElementComparison matchTmp = new ElementComparison(element.getVacancyProfileElementOwner(), element, e, e.getProfileElementOwner().getProfileOwner() ,matchValue);
                    elementMatches.add(matchTmp);
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden kandidaten")
    public List<ProfileComparison> getMatches(DemandProfile vacancy) {
        List<ProfileComparison> matches = new ArrayList<ProfileComparison>();
        //Init Test: Only if there are any Profiles
        if (container.allInstances(SupplyProfile.class).isEmpty()) {
            return matches;
        }
        //For every Profile
        for (SupplyProfile profile: container.allInstances(SupplyProfile.class)) {
            
            //Actually for every ProfileOwner (Actor)
            Actor tempProfileOwner = profile.getProfileOwner();
            
            //TempElement ProfileComparison with matchingvalue 0
            //This is a temporary Object that we will transfer the values of to a persistent object
            ProfileComparison tempMatch = new ProfileComparison(vacancy, profile, 0);
            Integer elementCounter = 0;
            
            // For every figureElement and DropdownElement on Vacancy
            // We determine the cumulative weight and the avarage weight in case no weight is given
            // if nowhere a weight is given we will use default 1 for avarage weight;            
            Integer cumWeight = 0;
            Integer avarageWeight = 1;
            Integer weightCounter = 0;
            Integer elCounter = 0;
            for (DemandProfileElement vpelement: vacancy.getDemandProfileElement()){
                //Only for elements of type figure
                if (vpelement.getProfileElementType() == ProfileElementType.MATCHABLE_FIGURE || vpelement.getProfileElementType() == ProfileElementType.MATCHABLE_DROPDOWN){
                    elCounter ++;
                    if (vpelement.getWeight() != null && vpelement.getWeight()>0){
                        cumWeight+=vpelement.getWeight();
                        weightCounter++;
                    }
                }
            }
            if (cumWeight>0 && weightCounter>0){
                avarageWeight = cumWeight/weightCounter;
            }
            // we now add average weight for the elements without weight to cumulative Weight
            if (elCounter > weightCounter){
                cumWeight += (elCounter - weightCounter)*avarageWeight;
            }
            
            // For every figureElement and DropDownElement on Vacancy we add to totalMatching value
            Long totalMatchingValue = (long) 0;
            for (DemandProfileElement vpelement: vacancy.getDemandProfileElement()){
                
                //Only for elementmatches on figures with tempProfileOwner as ProfileOwner
                if (vpelement.getProfileElementType() == ProfileElementType.MATCHABLE_FIGURE){
                    
                    // Get the matching profileElements in ElementComparison Object
                    List<ElementComparison> tempListOfElements = getElementMatches((VacancyProfileFigureElement) vpelement);
                    if (!tempListOfElements.isEmpty()){
                        for (ElementComparison e: tempListOfElements){
                            
                            //only if tempProfileOwner is ProfileOwner
                            if (e.getMatchingProfileOwner().equals(tempProfileOwner)){
                                
                                //Add 1 to elementCounter
                                elementCounter ++;
                                //Add to matching value
                                if (vpelement.getWeight()!=null && vpelement.getWeight() > 0){
                                    totalMatchingValue+=e.getCalculatedMatchingValue()*vpelement.getWeight()/cumWeight;
                                } else {
                                    totalMatchingValue+=e.getCalculatedMatchingValue()*avarageWeight/cumWeight;
                                }
                                
                            }
                        }
                    }
                }
                
                //Only for elementmatches on DropDownElements with tempProfileOwner as ProfileOwner
                if (vpelement.getProfileElementType() == ProfileElementType.MATCHABLE_DROPDOWN){
                    
                 // Get the matching profileElements in ElementComparison Object
                    List<ElementComparison> tempListOfElements = getDropDownElementMatches((VacancyProfileDropDownElement) vpelement);
                    if (!tempListOfElements.isEmpty()){
                        for (ElementComparison e: tempListOfElements){
                            
                          //only if tempProfileOwner is ProfileOwner
                            if (e.getMatchingProfileOwner().equals(tempProfileOwner)){
                                //Add 1 to elementCounter
                                elementCounter ++;
                                //Add to matching value
                                if (vpelement.getWeight()!=null && vpelement.getWeight() > 0){
                                    totalMatchingValue+=e.getCalculatedMatchingValue()*vpelement.getWeight()/cumWeight;
                                } else {
                                    totalMatchingValue+=e.getCalculatedMatchingValue()*avarageWeight/cumWeight;
                                }                                                              
                            }
                        }
                    }
                }
                
            }
            // Divide totalMatchingValue through number of elements if any are found
            if (elementCounter > 0) {
                tempMatch.setCalculatedMatchingValue((int) (totalMatchingValue/elementCounter));
            }
            
            // drempelwaarde is MATCHING_THRESHOLD
            if (totalMatchingValue > MATCHING_PROFILE_THRESHOLD){
                final ProfileComparison defMatch = newTransientInstance(ProfileComparison.class);
                tempMatch.setMatchInitiator(tempMatch.getMatchInitiator());
                tempMatch.setMatchingProfile(tempMatch.getMatchingProfile());
                tempMatch.setCalculatedMatchingValue(totalMatchingValue.intValue());
                persistIfNotAlready(defMatch);
                matches.add(tempMatch);
            }
                 
        }
        Collections.sort(matches);
        Collections.reverse(matches);
        return matches;
    }
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
