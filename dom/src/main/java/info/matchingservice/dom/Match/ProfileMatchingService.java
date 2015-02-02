/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package info.matchingservice.dom.Match;

import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementNumeric;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.Profiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Matching Algorithm
 * This service returns a List of profile comparison objects on a Demand Profile after persisting them first.
 * It uses the different Element_Comparison_Services to determine the element_comparison_objects for every profile_comparison_object 
 * and to calculate a calculatedMatchingValue on the profile_comparison_object.
 * 
 * Procedure
 * - First a check if there are any profiles at all (INIT)
 * - Stage 1: cumulative weight of all demand profile_elements is calculated. 
 *            Where there is no weight given the average weight of the elements with a given weight is taken.
 *            If there is no weight given at all then the avarage weight is set to 1.
 * - Stage 2: Calculate the calculatedMatchingValue and persist the profile_comparison_object (for every profile_comparison_object)
 * - Stage 3: Return the list of ProfileComparisons (as a contributed collection on the demand profile)         
 * 
 * BUSINESSRULES
 * - only profiles of the same ProfileType are matched
 * - hidden except on Demand Profiles
 * - every profile element comparison should have a matchingvalue of at most 100. 
 * TODO: (This is not enforced or verified anywhere at the moment.)
 * 
 * TODO: how to test this object?
 * 
 * @version 0.1 02-02-2015
 */
@DomainService
public class ProfileMatchingService extends AbstractService {
    
    // Thresholds
    final Integer MATCHING_PROFILE_THRESHOLD = 30;

    
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @Action(semantics=SemanticsOf.SAFE)
    @CollectionLayout(render=RenderType.EAGERLY)
    @Render(Type.EAGERLY) // because of bug @CollectionLayout
    public List<ProfileComparison> showProfileMatches(Profile demandProfile) {
        List<ProfileComparison> profileComparisons = new ArrayList<ProfileComparison>();
        
        //***********INIT**************//
        //Init Test: Only if there are any Profiles
        if (container.allInstances(Profile.class).isEmpty()) {
            return profileComparisons;
        }
        
        
        //For all Supply Profiles
        //BUSINESSRULE: we match demand/supply of the same ProfileType here
        for (Profile profile: profiles.allSupplyProfilesOfType(demandProfile.getProfileType())) {

                
                //Actually for every Supply Profile
                Supply tempProfileOwner = profile.getSupplyProfileOwner();
                
                //TempElement ProfileComparison with matchingvalue 0
                //This is a temporary Object that we will copy the values of into a persistent object
                ProfileComparison tempMatch = new ProfileComparison(demandProfile, profile, 0);
                Integer elementCounter = 0;
                
                //**** STAGE 1 ****WEIGTH CALCULATION *****/
                // For every figureElement and DropdownElement on Vacancy
                // We determine the cumulative weight and the avarage weight in case no weight is given
                // if nowhere a weight is given we will use default 1 for avarage weight;            
                Integer cumWeight = 0;
                Integer avarageWeight = 1;
                Integer weightCounter = 0;
                Integer elCounter = 0;
                for (ProfileElement demandProfileElement: demandProfile.getProfileElement()){
                    //Only for elements of type Numeric, type Quality and type PASSION_TAGS
                    if (
                            demandProfileElement.getProfileElementType() == ProfileElementType.NUMERIC 
                            || demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY
                            || demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS
                            )
                    {
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
                
                //**** END STAGE 1 ******** WEIGTH CALCULATION *****/
                
                //**** STAGE 2 ****//
                
                //**** STAGE 2 ****NUMERIC ELEMENTS: calculate and add to matchingValue *****/
                // For every NumericElement, DropDownElement and PassionElement on Demand we add to totalMatching value
                Long totalMatchingValue = (long) 0;
                for (ProfileElement demandProfileElement: demandProfile.getProfileElement()){
                    
                    //Only for elementmatches on NumericElements with tempProfileOwner as ProfileOwner
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.NUMERIC){
                        
                        // Get the matching profileElements in ElementComparison Object
                        List<ElementComparison> tempListOfElements = numericElementMatches.showElementMatches((ProfileElementNumeric) demandProfileElement);
                        if (!tempListOfElements.isEmpty()){
                            for (ElementComparison e: tempListOfElements){
                                
                                //only if tempProfileOwner is ProfileOwner
                                if (e.getMatchingProfileElementOwner().getSupplyProfileOwner().equals(tempProfileOwner)){
                                    
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
                    
                    //**** STAGE 2 ****QUALITY ELEMENTS: calculate and add to matchingValue *****/
                    //Only for elementmatches on DropDownElements with tempProfileOwner as ProfileOwner
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.QUALITY){
                        
                     // Get the matching profileElements in ElementComparison Object
                        List<ElementComparison> tempListOfElements = dropDownElementMatches.showDropDownElementMatches((ProfileElementDropDown) demandProfileElement);
                        if (!tempListOfElements.isEmpty()){
                            for (ElementComparison e: tempListOfElements){
                                
                              //only if tempProfileOwner is ProfileOwner
                                if (e.getMatchingProfileElementOwner().getSupplyProfileOwner().equals(tempProfileOwner)){
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
                    
                    //**** STAGE 2 ****PASSION ELEMENTS: calculate and add to matchingValue *****/
                    //Only for elementmatches on PassionElements with tempProfileOwner as ProfileOwner
                    if (demandProfileElement.getProfileElementType() == ProfileElementType.PASSION_TAGS){
                        
                     // Get the matching profileElements in ElementComparison Object
                        List<ElementComparison> tempListOfElements = passionElementMatches.showElementMatches((ProfileElementTag) demandProfileElement);
                        if (!tempListOfElements.isEmpty()){
                            for (ElementComparison e: tempListOfElements){
                                
                              //only if tempProfileOwner is ProfileOwner
                                if (e.getMatchingProfileElementOwner().getSupplyProfileOwner().equals(tempProfileOwner)){
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
                
                //**** STAGE 2 ****Determine the calculatedMatchingValue by taking the total value divided by the number of elements *****/
                // Divide totalMatchingValue through number of elements if any are found
                if (elementCounter > 0) {
                    tempMatch.setCalculatedMatchingValue((int) (totalMatchingValue/elementCounter));
                }
                
                //**** STAGE 2 ****Persist the profileComparison object *****/
                // drempelwaarde is MATCHING_THRESHOLD
                if (totalMatchingValue > MATCHING_PROFILE_THRESHOLD){
                    final ProfileComparison defMatch = newTransientInstance(ProfileComparison.class);
                    final UUID uuid=UUID.randomUUID();
                    tempMatch.setUniqueItemId(uuid);
                    tempMatch.setDemandProfile(tempMatch.getDemandProfile());
                    tempMatch.setMatchingSupplyProfile(tempMatch.getMatchingSupplyProfile());
                    tempMatch.setCalculatedMatchingValue(totalMatchingValue.intValue());
                    persistIfNotAlready(defMatch);
                    profileComparisons.add(tempMatch);
                }
                
                //END **** STAGE 2 ****//
    
        }
        
        //**** STAGE 3 ****//
        Collections.sort(profileComparisons);
        Collections.reverse(profileComparisons);
        return profileComparisons;
    }
    
    // this one is meant for demand profiles only
    public boolean hideShowProfileMatches(Profile demandProfile){
        return demandProfile.getDemandOrSupply() != DemandOrSupply.DEMAND;
    }
    

    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @javax.inject.Inject
    private Profiles profiles;
    
    @Inject
    QualityElementComparisonService dropDownElementMatches;
    
    @Inject
    PassionElementComparisonService passionElementMatches;
    
    @Inject
    NumericElementComparisonService numericElementMatches;
    

}
