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
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

/**
 * Matching Algorithm
 * returns matches of ProfileElements of ProfileElementType.BRANCHE_TAGS on SUPPLY ProfileType.PERSON_PROFILE of ORGANISARION_PROFILE as a contributed collection on the DEMAND ProfileELement
 * Every tag (in tagholder) of a profile element on a SUPPLY profile that matches a tag on the counterpart of the DEMAND profileElement contributes 100 to matchingValue.
 * The matchingValue is the total divided by the number of Tags on the DemandProfile.
 * A threshold is defined by MATCHING_ElEMENT_THRESHOLD; under this threshold the element is not taken into account
 * BUSSINESSRULES
 * - hidden on SupplyProfileElements (or: only visible on DemandProfileElements)
 * - element (Actor) owner of the demand should not be the same owner of the supply
 * 
 * TODO: how to test this object?
 * 
 * @version 0.1 04-02-2015
 */
@DomainService
public class TagElementComparisonService extends AbstractService {
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 20;
    
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @CollectionLayout(render=RenderType.EAGERLY)
    @Render(Type.EAGERLY) // because of bug @CollectionLayout
    @Action(semantics=SemanticsOf.SAFE)
    // Notabene: element is the profileElement on the DEMAND profile
    public List<ElementComparison> showElementMatches(ProfileElementTag element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any ProfileElements
        if (container.allInstances(ProfileElementTag.class).isEmpty()) {
            return elementMatches;
        }
        
        // look at all the corresponding profileELements on SUPPLY profiles
        for (ProfileElementTag e : container.allInstances(ProfileElementTag.class)) {
            if (
            		e.getProfileElementOwner().getDemandOrSupply() == DemandOrSupply.SUPPLY  &&  
            		(e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE || 
            		e.getProfileElementOwner().getProfileType() == ProfileType.ORGANISATION_PROFILE) && 
            		(e.getProfileElementType() == ProfileElementType.BRANCHE_TAGS || 
            		e.getProfileElementType() == ProfileElementType.QUALITY_TAGS)
            		){
                // uitsluiten van dezelfde owner
                if (!e.getOwnedBy().equals(element.getOwnedBy())){
                    Integer matchValue = 0;
                    Integer numberOfTagsOnDemand = 0;
                    //Iterate over all the tags (this is: tagholders) on the demand profileElement element
                    for (final Iterator<TagHolder> it_demand = element.getCollectTagHolders().iterator(); it_demand.hasNext();){
                        Tag tag_demand = it_demand.next().getTag();
                        numberOfTagsOnDemand += 1;
                        //if there are any tags on supply profileElement
                        //TODO: this check should be made earlier for performance gain
                        if (e.getCollectTagHolders().size()>0){
                        	//iterate over all the tags (tagholders) on supply element
                        	for (final Iterator<TagHolder> it_supply = e.getCollectTagHolders().iterator(); it_supply.hasNext();){
                        		Tag tag_supply = it_supply.next().getTag();
                        		if (tag_supply.equals(tag_demand)){
                        			matchValue += 100;
                        		}
                        	}
                        }
                    }
                    // take the average matchValue of all Tags
                    if (numberOfTagsOnDemand>0){
                    	matchValue = matchValue / numberOfTagsOnDemand;
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

