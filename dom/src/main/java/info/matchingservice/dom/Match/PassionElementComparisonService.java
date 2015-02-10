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
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
 * returns matches of ProfileElements of ProfileElementType.PASSION on SUPPLY Profiles of ProfileType.PERSON_PROFILE of ORGANISARION_PROFILE
 * elements of ProfileElementType.PASSION_TAGS on DEMAND profile are measures against the ProfileElements of ProfileElementType.PASSION on SUPPLY Profile
 * Every tag (in tagholder) that matches 1 or more patterns in textValue on supply profileElement contributes 100 to matchingValue.
 * Non-matching tags contribute 0 and matchingValue is average in the end.
 * A threshold is defined by MATCHING_ElEMENT_THRESHOLD; under this threshold the element is not taken into account
 * BUSSINESSRULES
 * - hidden on SupplyProfileElements (or: only visible on DemandProfileElements)
 * - element (Actor) owner of the demand should not be the same owner of the supply
 * 
 * TODO: how to test this object?
 * 
 * @version 0.2 02-02-2015
 */
@DomainService
public class PassionElementComparisonService extends AbstractService {
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 20;
    
    //the difference here with other comparison services is this
    // - on supplyprofile the (text)element of type PASSION is matched against
    // - every tag (wrapped in TagHolder) on demandprofile with (tag)element of type PASSION_TAG
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @CollectionLayout(render=RenderType.EAGERLY)
    @Render(Type.EAGERLY) // because of bug @CollectionLayout
    @Action(semantics=SemanticsOf.SAFE)
    public List<ElementComparison> showElementMatches(ProfileElementTag element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
       //Init Test: Only if there are any ProfileElements
        if (container.allInstances(ProfileElementTag.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileElementText e : container.allInstances(ProfileElementText.class)) {
            if (
            		e.getProfileElementOwner().getDemandOrSupply() == DemandOrSupply.SUPPLY  &&  
            		(e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE || 
            		e.getProfileElementOwner().getProfileType() == ProfileType.ORGANISATION_PROFILE) && 
            		e.getProfileElementType() == ProfileElementType.PASSION){
                // uitsluiten van dezelfde owner
                if (!e.getOwnedBy().equals(element.getOwnedBy())){
                    Integer matchValue = 0;
                    for (final Iterator<TagHolder> it = element.getCollectTagHolders().iterator(); it.hasNext();){
                        Tag tag = it.next().getTag();
                        if (e.getTextValue().toLowerCase().matches("(.*)" + tag.getTagDescription() + "(.*)")){
                            matchValue += 100;
                        }
                    }
                    // take the average matchValue of all Tags
                    if (element.getCollectTagHolders().size()>0){
                    	matchValue = matchValue / element.getCollectTagHolders().size();
                    } else {
                    	matchValue =0;
                    }
                    if (matchValue >= MATCHING_ElEMENT_THRESHOLD){
                        ElementComparison matchTmp = new ElementComparison(element.getProfileElementOwner(), element, e, e.getProfileElementOwner(), e.getProfileElementOwner().getSupplyProfileOwner().getSupplyOwner(), matchValue);
                        elementMatches.add(matchTmp);
                    }
                }
            }
        }
        Collections.sort(elementMatches);
        Collections.reverse(elementMatches);
        return elementMatches;
    }
    
    // Hide the contributed List except on Profiles of type Demand_Person_Profile
    // Strictly this should be impossible by other business logic but we leave it here anyway
    public boolean hideShowElementMatches(ProfileElementTag element){
        return element.getProfileElementOwner().getDemandOrSupply() != DemandOrSupply.DEMAND;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

