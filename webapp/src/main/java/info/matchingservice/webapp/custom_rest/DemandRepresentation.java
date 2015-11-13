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
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Profile.Profile;

/**
 * Created by jodo on 18/06/15.
 */
public class DemandRepresentation {

    DemandRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final Demand demand) {

        JsonRepresentation demandmap = JsonRepresentation.newMap();

        demandmap.mapPut("id", Utils.toApiID(demand.getOID()));
        demandmap.mapPut("URI", Utils.toObjectURI(demand.getOID()));
        demandmap.mapPut("description", demand.getDescription());
        demandmap.mapPut("summary", demand.getSummary());
        demandmap.mapPut("story", demand.getStory());
        if (demand.getStartDate() == null){
            demandmap.mapPut("startDate", "");
        } else {
            demandmap.mapPut("startDate", demand.getStartDate().toString());
        }
        if (demand.getEndDate() == null){
            demandmap.mapPut("endDate", "");
        } else {
            demandmap.mapPut("endDate", demand.getEndDate().toString());
        }
        if (demand.getImageUrl() == null) {
            demandmap.mapPut("imageUrl", "");
        } else {
            demandmap.mapPut("imageUrl", demand.getImageUrl());
        }


        // profiles
        JsonRepresentation profiles = JsonRepresentation.newArray();

        for (Profile profile : demand.getProfiles()) {

            ProfileRepresentation rep = new ProfileRepresentation();
            profiles.arrayAdd(rep.ObjectRepresentation(profile));

        }

        demandmap.mapPut("profiles", profiles);

        // assessments
        JsonRepresentation assessments = JsonRepresentation.newArray();

        for (Assessment assessment : demand.getAssessments()) {
            AssessmentRepresentation rep = new AssessmentRepresentation();
            assessments.arrayAdd(rep.ObjectRepresentation(assessment));
        }

        demandmap.mapPut("assessments", assessments);

        return demandmap;
    }
}
