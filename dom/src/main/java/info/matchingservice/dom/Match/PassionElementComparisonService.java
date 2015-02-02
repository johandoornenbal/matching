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

@DomainService
public class PassionElementComparisonService extends AbstractService {
    // Thresholds
    final Integer MATCHING_ElEMENT_THRESHOLD = 50;
    
    //PASSION MATCHES//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //return matches on Profile ProfileElements of type PASSION_TAG and PASSION (the last only for profiles of Type Supply_Person_Profile)
    //the difference here with other comparison services is this
    // - on supplyprofile the (text)element of type PASSION is matched against
    // - every tag (wrapped in TagHolder) on demandprofile with (tag)element of type PASSION_TAG
    @NotInServiceMenu
    @NotContributed(As.ACTION)
//    @CollectionLayout(render=RenderType.EAGERLY)
    @Render(Type.EAGERLY)
    @Action(semantics=SemanticsOf.SAFE)
    public List<ElementComparison> showElementMatches(ProfileElementTag element){
        
        List<ElementComparison> elementMatches = new ArrayList<ElementComparison>();
        
        //Init Test: Only if there are any Profiles
        if (container.allInstances(ProfileElementText.class).isEmpty()) {
            return elementMatches;
        }
        
        for (ProfileElementText e : container.allInstances(ProfileElementText.class)) {
            if (e.getProfileElementOwner().getSupplyProfileOwner()!=null  &&  e.getProfileElementOwner().getProfileType() == ProfileType.PERSON_PROFILE && e.getProfileElementType() == ProfileElementType.PASSION){
                // uitsluiten van dezelfde owner
                if (!e.getOwnedBy().equals(element.getOwnedBy())){
                    Integer matchValue = 0;
                    for (final Iterator<TagHolder> it = element.getTagHolders().iterator(); it.hasNext();){
                        Tag tag = it.next().getTag();
                        //TODO: Afmaken algoritme
                        if (e.getTextValue().toLowerCase().matches("(.*)" + tag.getTagDescription() + "(.*)")){
                            matchValue += 100;
                        }
                    }
                    // take the average matchValue of all Tags
                    matchValue = matchValue / element.getTagHolders().size();
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
        return element.getProfileElementOwner().getDemandProfileOwner()==null;
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}

