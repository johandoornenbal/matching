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

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileElementText.class, nature=NatureOfService.DOMAIN)
public class ProfileElements extends MatchingDomainService<ProfileElement> {

    public ProfileElements() {
        super(ProfileElements.class, ProfileElement.class);
    }

    @Programmatic
    public List<ProfileElement> findProfileElementByOwnerProfileAndDescription(final String profileElementDescription, final Profile profileElementOwner){
    	return allMatches("findProfileElementByOwnerProfileAndDescription",
        		"description", profileElementDescription,
        		"profileElementOwner", profileElementOwner);
    }

}
