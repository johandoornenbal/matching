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

package info.matchingservice.webapp.custom_rest;

import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;

import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElements;

/**
 * Created by jodo on 18/06/15.
 */
public class ProfileElementRepresentation {

    ProfileElementRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final ProfileElement element) {
        JsonRepresentation profileElementMap = JsonRepresentation.newMap();
        profileElementMap.mapPut("id", Utils.toApiID(element.getOID()));
        profileElementMap.mapPut("URI", Utils.toObjectURI(element.getOID()));
        profileElementMap.mapPut("description", element.getDescription());
        profileElementMap.mapPut("displayValue", element.getDisplayValue());
        profileElementMap.mapPut("isActive", element.getIsActive());
        if (element.getProfileElementType().equals(ProfileElementType.PASSION)) {
            ProfileElementText passionTag = (ProfileElementText) profileElementsRepo.findProfileElementByUniqueId(element.getUniqueItemId()).get(0);
            profileElementMap.mapPut("passion", passionTag.getTextValue());
        }
        return profileElementMap;
    }

    private ProfileElements profileElementsRepo = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileElements.class);

}
