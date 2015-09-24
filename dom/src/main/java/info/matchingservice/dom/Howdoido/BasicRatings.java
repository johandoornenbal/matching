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

package info.matchingservice.dom.Howdoido;

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicRating.class)
public class BasicRatings extends MatchingDomainService<BasicRating> {

    public BasicRatings() {
        super(BasicRatings.class, BasicRating.class);
    }

    @Programmatic
    public List<BasicRating> allBasicRating () {
        return allInstances(BasicRating.class);
    }

    @Programmatic
    public List<BasicRating> findByReceiver (final BasicUser receiver)
    {
        return allMatches("findBasicRatingByReceiver", "receiver", receiver);
    }

    @Programmatic
    public List<BasicRating> findByCreator (final BasicUser creator)
    {
        return allMatches("findBasicRatingByCreator","creator",creator);
    }

    @Programmatic
    public List<BasicRating> findByCategory (final BasicCategory category)
    {
        return allMatches("findBasicRatingByCategory", "basicCategory", category);
    }

    @Programmatic
    public List<BasicRating> findByReceiverAndCategory (final BasicUser receiver, final BasicCategory category)
    {
        return allMatches("findBasicRatingByReceiverAndCategory", "receiver", receiver, "basicCategory", category);
    }



    @Programmatic
    public BasicRating createBasicRating(
            @ParameterLayout(named = "creator")
            final BasicUser creator,
            @ParameterLayout(named = "receiver")
            final BasicUser receiver,
            @ParameterLayout(named = "rate")
            final FeedbackRating rating,
            @ParameterLayout(named = "category")
            final BasicCategory category) {

        BasicRating newRating = newTransientInstance(BasicRating.class);

        for (BasicCategory c : category.getAllAscendantCategories()) {

            createBasicRating(creator,receiver,rating,c);

        }

        final UUID uuid=UUID.randomUUID();

        newRating.setCreator(creator);
        newRating.setReceiver(receiver);
        newRating.setFeedbackRating(rating);
        newRating.setBasicCategory(category);
        newRating.setUniqueItemId(uuid);

        persistIfNotAlready(newRating);

        return newRating;

    }



}
