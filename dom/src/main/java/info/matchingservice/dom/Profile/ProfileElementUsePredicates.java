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

package info.matchingservice.dom.Profile;

import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileElementUsePredicate.class)
public class ProfileElementUsePredicates extends MatchingDomainService<ProfileElementUsePredicate> {

    public ProfileElementUsePredicates() {
        super(ProfileElementUsePredicates.class, ProfileElementUsePredicate.class);
    }

    // with currentUserName
    @Programmatic
    public ProfileElementUsePredicate createProfileElementUsePredicate(
            final String description,
            final Integer weight,
            final boolean useTimePeriod,
            final boolean useAge,
            final ProfileElementType profileElementType,
            final Profile profileOwner
            ){

        return createProfileElementUsePredicate(
        		description,
                weight,
                useTimePeriod,
                useAge,
                profileElementType,
                profileOwner,
                currentUserName()
        		);
    }
    
    // without currentUser (for fixtures)
    @Programmatic
    public ProfileElementUsePredicate createProfileElementUsePredicate(
            final String description,
            final Integer weight,
            final boolean useTimePeriod,
            final boolean useAge,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementUsePredicate newProfileElement = newTransientInstance(ProfileElementUsePredicate.class);
        final UUID uuid=UUID.randomUUID();
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setUseTimePeriod(useTimePeriod);
        newProfileElement.setUseAge(useAge);
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setIsActive(true);
        newProfileElement.setWidgetType(ProfileElementWidgetType.PREDICATE);
        newProfileElement.setOwnedBy(ownedBy);
        persist(newProfileElement);
        return newProfileElement;
    }
    
    // Region>helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
