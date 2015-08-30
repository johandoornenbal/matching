/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.dom.Match.MatchingHelpers;

import java.util.Iterator;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import info.matchingservice.dom.Match.ProfileElementComparison;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.TagHolder;

/**
 * Created by jodo on 28/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class ProfileElementTagComparison {

    public ProfileElementComparison getProfileElementComparison(
            final ProfileElementTag demandProfileElementTag,
            final ProfileElementTag supplyProfileElementTag
            ){

        Integer matchValue = 0;
        Integer numberOfTagsOnDemand = 0;

        //Iterate over all the tags (this is: tagholders) on the demand profileElement element
        for (final Iterator<TagHolder> it_demand = demandProfileElementTag.getCollectTagHolders().iterator(); it_demand.hasNext();){

            Tag tag_demand = it_demand.next().getTag();
            numberOfTagsOnDemand += 1;

            if (supplyProfileElementTag.getCollectTagHolders().size() > 0)
            {

                //iterate over all the tags (tagholders) on supply element
                for (final Iterator<TagHolder> it_supply = supplyProfileElementTag.getCollectTagHolders().iterator(); it_supply.hasNext();){

                    Tag tag_supply = it_supply.next().getTag();

                    if (tag_demand.equals(tag_supply)){

                        matchValue += 100;
                        System.out.println("match from getProfileElementTagComparison() in ProfileMatchingService.class:");
                        System.out.println("supply of: " + supplyProfileElementTag.getOwnedBy() + " type: " + supplyProfileElementTag.getProfileElementType().toString());
                        System.out.println(tag_supply.getTagDescription() + " matchValue: " + matchValue);

                    }

                }

            }

        }

        // take the average matchValue of all Tags
        if (numberOfTagsOnDemand > 0) {

            matchValue = matchValue / numberOfTagsOnDemand;

        } else {

            matchValue = 0;

        }

        ProfileElementComparison profileElementComparison = new ProfileElementComparison(
                demandProfileElementTag.getProfileElementOwner(),
                demandProfileElementTag,
                supplyProfileElementTag,
                supplyProfileElementTag.getProfileElementOwner(),
                supplyProfileElementTag.getProfileElementOwner().getActorOwner(),
                matchValue,
                demandProfileElementTag.getWeight()
        );
        return profileElementComparison;
    }

}
