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

import info.matchingservice.dom.MatchingDomainService;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = ProfileElementText.class)
public class ProfileElementTexts extends MatchingDomainService<ProfileElementText> {

    public ProfileElementTexts() {
        super(ProfileElementTexts.class, ProfileElementText.class);
    }

    @Programmatic
    public ProfileElementText createProfileElementText(
            final String description,
            final Integer weight,
            final String textValue,
            final ProfileElementType profileElementCategory,
            final Profile profileOwner
            ){
        final ProfileElementText newProfileElement = newTransientInstance(ProfileElementText.class);
        final UUID uuid=UUID.randomUUID();
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setTextValue(textValue);
        newProfileElement.setDisplayValue(textValue);
        newProfileElement.setProfileElementType(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setOwnedBy(currentUserName());
        persist(newProfileElement);
        return newProfileElement;
    }
    
    @Programmatic
    public ProfileElementText createProfileElementText(
            final String description,
            final Integer weight,
            @Parameter(maxLength=2048)
            final String textValue,
            final ProfileElementType profileElementCategory,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementText newProfileElement = newTransientInstance(ProfileElementText.class);
        final UUID uuid=UUID.randomUUID();
        Integer textLength = 20;
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setTextValue(textValue);
        if (textValue.length() < textLength){
        	textLength = textValue.length();
        }
        newProfileElement.setDisplayValue(textValue.substring(0, textLength-1));
        newProfileElement.setProfileElementType(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
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
