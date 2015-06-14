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

import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileElementDropDown.class)
public class ProfileElementDropDowns extends MatchingDomainService<ProfileElementDropDown> {

    public ProfileElementDropDowns() {
        super(ProfileElementDropDowns.class, ProfileElementDropDown.class);
    }

    // with currentUserName
    @Programmatic
    public ProfileElementDropDown createProfileElementDropDown(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown,
            final ProfileElementType profileElementType,
            final Profile profileOwner
            ){
        
        return createProfileElementDropDown(description, weight, dropDown, profileElementType, profileOwner, currentUserName());
    }
    
    // without currentUser (for fixtures)
    @Programmatic
    public ProfileElementDropDown createProfileElementDropDown(
            final String description,
            final Integer weight,
            final DropDownForProfileElement dropDown,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementDropDown newProfileElement = newTransientInstance(ProfileElementDropDown.class);
        final UUID uuid=UUID.randomUUID();
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setDropDownValue(dropDown);
        newProfileElement.setDisplayValue(dropDown.getValue());
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setIsActive(true);
        newProfileElement.setWidgetType(ProfileElementWidgetType.SELECT);
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
