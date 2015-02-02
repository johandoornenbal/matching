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

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementDropDown;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Matching Algorithm
 * returns matches of ProfileElements of ProfileElementType.QUALITY on SUPPLY Profiles of ProfileType.PERSON_PROFILE
 * The matchValue for a matching element = 100. Otherwise 0. So no threshol for matchingValue required here.
 * BUSSINESSRULES
 * - hidden on SupplyProfileElements (or: only visible on DemandProfileElements)
 * - element (Actor) owner of the demand should not be the same owner of the supply
 * 
 * TODO: how to test this object?
 * 
 * @version 0.1 02-02-2015
 */
@DomainService
public class QualityElementComparisonService extends AbstractService {
    
  @NotInServiceMenu
  @NotContributed(As.ACTION)
  @CollectionLayout(render=RenderType.EAGERLY)
  @Render(Type.EAGERLY) // because of bug @CollectionLayout
  @Action(semantics=SemanticsOf.SAFE)
  @Named("Gevonden matching kwaliteiten op persoon profiel elementen")
  public List<ElementComparison> showDropDownElementMatches(ProfileElementDropDown element){
      
      List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
      
      //Init Test: Only if there are any Profiles
      if (container.allInstances(ProfileElementDropDown.class).isEmpty()) {
          return elementMatches;
      }
      
      for (ProfileElementDropDown e : container.allInstances(ProfileElementDropDown.class)) {
          if (e.getProfileElementOwner().getDemandOrSupply() == DemandOrSupply.SUPPLY  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.QUALITY){
              // uitsluiten van dezelfde owner
              // drempelwaarde is MATCHING_THRESHOLD
              Integer matchValue=0;
              if (element.getDropDownValue().equals(e.getDropDownValue())){
                  matchValue=100;
              }
              // Check if profile element is not owned by the actor having the demand 
              if (!e.getOwnedBy().equals(element.getOwnedBy())) {
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
      return element.getProfileElementOwner().getDemandOrSupply() != DemandOrSupply.DEMAND;
  }
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

