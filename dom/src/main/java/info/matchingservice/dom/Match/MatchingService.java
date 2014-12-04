package info.matchingservice.dom.Match;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
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
    
    //NUMERIC MATCHES//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //return matches on Numeric ProfileElements only for profiles of Type Supply_Person_Profile
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden matching persoon profiel elementen")
    public List<ElementComparison> showElementMatches(ProfileElementNumeric element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileElementNumeric.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileElementNumeric e : container.allInstances(ProfileElementNumeric.class)) {
            if (e.getProfileElementOwner().getSupplyProfileOwner()!=null  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.NUMERIC){
                // uitsluiten van dezelfde owner
                // drempelwaarde is MATCHING_THRESHOLD
                Integer matchValue = 100 - 10*Math.abs(element.getNumericValue() - e.getNumericValue());
                if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                    ElementComparison matchTmp = new ElementComparison(element.getProfileElementOwner(), element, e, e.getProfileElementOwner(), e.getProfileElementOwner().getSupplyProfileOwner().getSupplyOwner(), matchValue);
                    elementMatches.add(matchTmp);
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    // Hide the contributed List except on Profiles of type Demand_Person_Profile
    public boolean hideShowElementMatches(ProfileElementNumeric element){
        return element.getProfileElementOwner().getDemandProfileOwner()==null;
    }
    
    
//    //DROPDOWNS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //return matches on Dropdown ProfileElements only for profiles of Type Supply_Person_Profile
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden matching kwaliteiten op persoon profiel elementen")
    public List<ElementComparison> showDropDownElementMatches(ProfileElementDropDown element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileElementDropDown.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileElementDropDown e : container.allInstances(ProfileElementDropDown.class)) {
            if (e.getProfileElementOwner().getSupplyProfileOwner()!=null  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.QUALITY){
                // uitsluiten van dezelfde owner
                // drempelwaarde is MATCHING_THRESHOLD
                Integer matchValue=0;
                if (element.getDropDownValue().equals(e.getDropDownValue())){
                    matchValue=100;
                }
                if (matchValue >= MATCHING_ElEMENT_THRESHOLD && !e.getOwnedBy().equals(element.getOwnedBy())) {
                    ElementComparison matchTmp = new ElementComparison(element.getProfileElementOwner(), element, e, e.getProfileElementOwner() , e.getProfileElementOwner().getSupplyProfileOwner().getSupplyOwner(), matchValue);
                    elementMatches.add(matchTmp);
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    // Hide the contributed List except on Profiles of type Demand_Person_Profile
    public boolean hideShowDropDownElementMatches(ProfileElementDropDown element){
        return element.getProfileElementOwner().getDemandProfileOwner()==null;
    }
    
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionSemantics(Of.SAFE)
    @Render(Type.EAGERLY)
    @Named("Gevonden kandidaten")
    public List<ProfileComparison> getMatches(Profile demandProfile) {
        List<ProfileComparison> matches = new ArrayList<ProfileComparison>();
        //Init Test: Only if there are any Profiles
        if (container.allInstances(Profile.class).isEmpty()) {
            return matches;
        }
        //For all Supply Profiles
        for (Profile profile: profiles.allSupplyProfiles()) {

                
                //Actually for every ProfileOwner (Actor)
                Actor tempProfileOwner = profile.getSupplyProfileOwner().getSupplyOwner();
                
                //TempElement ProfileComparison with matchingvalue 0
                //This is a temporary Object that we will transfer the values of to a persistent object
                ProfileComparison tempMatch = new ProfileComparison(demandProfile, profile, 0);
                Integer elementCounter = 0;
                
                // For every figureElement and DropdownElement on Vacancy
                // We determine the cumulative weight and the avarage weight in case no weight is given
                // if nowhere a weight is given we will use default 1 for avarage weight;            
                Integer cumWeight = 0;
                Integer avarageWeight = 1;
                Integer weightCounter = 0;
                Integer elCounter = 0;
                for (ProfileElement demandProfileElement: demandProfile.getProfileElement()){
                    //Only for elements of type figure
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.NUMERIC || demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY){
                        elCounter ++;
                        if (demandProfileElement.getWeight() != null && demandProfileElement.getWeight()>0){
                            cumWeight+=demandProfileElement.getWeight();
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
                for (ProfileElement demandProfileElement: demandProfile.getProfileElement()){
                    
                    //Only for elementmatches on figures with tempProfileOwner as ProfileOwner
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.NUMERIC){
                        
                        // Get the matching profileElements in ElementComparison Object
                        List<ElementComparison> tempListOfElements = showElementMatches((ProfileElementNumeric) demandProfileElement);
                        if (!tempListOfElements.isEmpty()){
                            for (ElementComparison e: tempListOfElements){
                                
                                //only if tempProfileOwner is ProfileOwner
                                if (e.getMatchingProfileElementActorOwner().equals(tempProfileOwner)){
                                    
                                    //Add 1 to elementCounter
                                    elementCounter ++;
                                    //Add to matching value
                                    if (demandProfileElement.getWeight()!=null && demandProfileElement.getWeight() > 0){
                                        totalMatchingValue+=e.getCalculatedMatchingValue()*demandProfileElement.getWeight()/cumWeight;
                                    } else {
                                        totalMatchingValue+=e.getCalculatedMatchingValue()*avarageWeight/cumWeight;
                                    }
                                    
                                }
                            }
                        }
                    }
                    
                    //Only for elementmatches on DropDownElements with tempProfileOwner as ProfileOwner
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY){
                        
                     // Get the matching profileElements in ElementComparison Object
                        List<ElementComparison> tempListOfElements = showDropDownElementMatches((ProfileElementDropDown) demandProfileElement);
                        if (!tempListOfElements.isEmpty()){
                            for (ElementComparison e: tempListOfElements){
                                
                              //only if tempProfileOwner is ProfileOwner
                                if (e.getMatchingProfileElementActorOwner().equals(tempProfileOwner)){
                                    //Add 1 to elementCounter
                                    elementCounter ++;
                                    //Add to matching value
                                    if (demandProfileElement.getWeight()!=null && demandProfileElement.getWeight() > 0){
                                        totalMatchingValue+=e.getCalculatedMatchingValue()*demandProfileElement.getWeight()/cumWeight;
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
                    tempMatch.setDemandProfile(tempMatch.getDemandProfile());
                    tempMatch.setMatchingSupplyProfile(tempMatch.getMatchingSupplyProfile());
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
    
    @javax.inject.Inject
    private Profiles profiles;
    

}
