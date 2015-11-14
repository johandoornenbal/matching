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

import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;

import info.matchingservice.dom.Match.ProfileMatch;

/**
 * Created by jodo on 18/06/15.
 */
public class ProfileMatchRepresentation {

    ProfileMatchRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final ProfileMatch match) {

        JsonRepresentation profileMatchMap = JsonRepresentation.newMap();
        profileMatchMap.mapPut("id", Utils.toApiID(match.getOID()));
        profileMatchMap.mapPut("URI", Utils.toObjectURI(match.getOID()));
        profileMatchMap.mapPut("title", match.title());
        profileMatchMap.mapPut("supplyCandidateFullName", match.getSupplyCandidate().title());
        profileMatchMap.mapPut("supplyCandidatePictureLink", match.getSupplyCandidate().getImageUrl());
        profileMatchMap.mapPut("supplyCandidateURI", Utils.toObjectURI(match.getSupplyCandidate().getOID()));
        profileMatchMap.mapPut("supplyCandidateId", Utils.toApiID(match.getSupplyCandidate().getOID()));
        profileMatchMap.mapPut("demandProfileId", Utils.toApiID(match.getProfile().getOID()));
        profileMatchMap.mapPut("demandProfileURI", Utils.toObjectURI(match.getProfile().getOID()));
        profileMatchMap.mapPut("matchingSupplyProfileId", Utils.toApiID(match.getMatchingSupplyProfile().getOID()));
        profileMatchMap.mapPut("matchingSupplyProfileURI", Utils.toObjectURI(match.getMatchingSupplyProfile().getOID()));
        profileMatchMap.mapPut("candidateStatus", match.getCandidateStatus().toString());
        profileMatchMap.mapPut("ownerFullName", match.getOwner().title());
        profileMatchMap.mapPut("ownerPictureLink", match.getOwner().getImageUrl());
        profileMatchMap.mapPut("ownerURI", Utils.toObjectURI(match.getOwner().getOID()));
        profileMatchMap.mapPut("ownerId", Utils.toApiID(match.getOwner().getOID()));

        return profileMatchMap;
    }

}
