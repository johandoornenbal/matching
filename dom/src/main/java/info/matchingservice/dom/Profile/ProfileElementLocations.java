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

import org.isisaddons.services.postalcode.Location;
import org.isisaddons.services.postalcode.postcodenunl.PostcodeNuService;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ProfileElementLocation.class)
public class ProfileElementLocations extends MatchingDomainService<ProfileElementLocation> {

    public ProfileElementLocations() {
        super(ProfileElementLocations.class, ProfileElementLocation.class);
    }

    // with currentUserName
    @Programmatic
    public ProfileElementLocation createProfileElementLocation(
            final String description,
            final Integer weight,
            final String postcode,
            final ProfileElementType profileElementType,
            final Profile profileOwner
            ){
    	return createProfileElementLocation(description, weight, postcode, profileElementType, profileOwner, currentUserName());
    }
    
    // without currentUser (for fixtures)
    @Programmatic
    public ProfileElementLocation createProfileElementLocation(
            final String description,
            final Integer weight,
            final String postcode,
            final ProfileElementType profileElementType,
            final Profile profileOwner,
            final String ownedBy
            ){
        final ProfileElementLocation newProfileElement = newTransientInstance(ProfileElementLocation.class);
        final UUID uuid=UUID.randomUUID();
        PostcodeNuService service = new PostcodeNuService();
        Location location = service.locationFromPostalCode(null, postcode);
        newProfileElement.setUniqueItemId(uuid);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setPostcode(postcode);
        newProfileElement.setLongitude(location.getLongitude());
        newProfileElement.setLatitude(location.getLatitude());
        newProfileElement.setIsValid(location.getSucces());
        newProfileElement.setDisplayValue(postcode);
        newProfileElement.setProfileElementType(profileElementType);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setIsActive(true);
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
