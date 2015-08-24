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

import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;

/**
 * Created by jodo on 18/06/15.
 */
public class SupplyRepresentation {

    SupplyRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final Supply supply) {

        JsonRepresentation supplymap = JsonRepresentation.newMap();

        supplymap.mapPut("id", Utils.toApiID(supply.getOID()));
        supplymap.mapPut("URI", Utils.toObjectURI(supply.getOID()));
        supplymap.mapPut("description", supply.getSupplyDescription());
        if (supply.getDemandOrSupplyProfileStartDate() == null){
            supplymap.mapPut("startDate", "");
        } else {
            supplymap.mapPut("startDate", supply.getDemandOrSupplyProfileStartDate().toString());
        }
        if (supply.getDemandOrSupplyProfileEndDate() == null){
            supplymap.mapPut("endDate", "");
        } else {
            supplymap.mapPut("endDate", supply.getDemandOrSupplyProfileEndDate().toString());
        }
        if (supply.getImageUrl() == null) {
            supplymap.mapPut("imageUrl", "");
        } else {
            supplymap.mapPut("imageUrl", supply.getImageUrl());
        }

        // profiles
        JsonRepresentation profiles = JsonRepresentation.newArray();

        for (Profile profile : supply.getCollectSupplyProfiles()) {

            ProfileRepresentation rep = new ProfileRepresentation();
            profiles.arrayAdd(rep.ObjectRepresentation(profile));

        }

        supplymap.mapPut("profiles", profiles);

        // assessments
        JsonRepresentation assessments = JsonRepresentation.newArray();

        for (Assessment assessment : supply.getCollectSupplyAssessments()) {
            AssessmentRepresentation rep = new AssessmentRepresentation();
            assessments.arrayAdd(rep.ObjectRepresentation(assessment));
        }

        supplymap.mapPut("assessments", assessments);

        return supplymap;
    }
}
