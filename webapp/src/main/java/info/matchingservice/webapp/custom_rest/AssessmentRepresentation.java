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

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.Assessment.ProfileFeedback;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;

/**
 * Created by jodo on 18/06/15.
 */
public class AssessmentRepresentation {

    AssessmentRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final Assessment assessment) {

        JsonRepresentation assessmentMap = JsonRepresentation.newMap();
        assessmentMap.mapPut("description" , assessment.getAssessmentDescription());
        try {
            ProfileFeedback feedback = (ProfileFeedback) assessment;
            assessmentMap.mapPut("feedback" , feedback.getFeedback());
        } catch (Exception e) {
            assessmentMap.mapPut("feedback" , "");
        }
        Person assessmentOwner = (Person) assessment.getAssessmentOwnerActor();
        assessmentMap.mapPut("assessmentOwnerId", Utils.toApiID(assessmentOwner.getOID()));
        assessmentMap.mapPut("assessmentOwnerURI", Utils.toObjectURI(assessmentOwner.getOID()));
        assessmentMap.mapPut("assessmentOwnerFullName", assessmentOwner.title());
        assessmentMap.mapPut("assessmentOwnerRoles", assessmentOwner.getRoles());
        assessmentMap.mapPut("assessmentOwnerPictureLink", assessmentOwner.getPictureLink());
        Person assessmentTargetOwner = (Person) assessment.getTargetOwnerActor();
        assessmentMap.mapPut("assessmentTargetOwnerId", Utils.toApiID(assessmentTargetOwner.getOID()));
        assessmentMap.mapPut("assessmentTargetOwnerURI", Utils.toObjectURI(assessmentTargetOwner.getOID()));
        assessmentMap.mapPut("assessmentTargetOwnerFullName", assessmentTargetOwner.title());
        assessmentMap.mapPut("assessmentTargetOwnerRoles", assessmentTargetOwner.getRoles());
        assessmentMap.mapPut("assessmentTargetOwnerPictureLink", assessmentTargetOwner.getPictureLink());

        //try casting target to demand
        try {

            Demand targetDemand = (Demand) assessment.getTargetOfAssessment();
            assessmentMap.mapPut("targetOfAssessmentId", Utils.toApiID(targetDemand.getOID()));
            assessmentMap.mapPut("targetOfAssessmentURI", Utils.toObjectURI(targetDemand.getOID()));
//            assessmentMap.mapPut("targetType", targetDemand.getClass().toString());

        } catch (Exception e) {

        }

        //try casting target to supply
        try {

            Supply targetSupply = (Supply) assessment.getTargetOfAssessment();
            assessmentMap.mapPut("targetOfAssessmentId", Utils.toApiID(targetSupply.getOID()));
            assessmentMap.mapPut("targetOfAssessmentURI", Utils.toObjectURI(targetSupply.getOID()));
//            assessmentMap.mapPut("targetType", targetSupply.getClass().toString());

        } catch (Exception e) {

        }

        //try casting target to profile
        try {

            Profile targetProfile = (Profile) assessment.getTargetOfAssessment();
            assessmentMap.mapPut("targetOfAssessmentId", Utils.toApiID(targetProfile.getOID()));
            assessmentMap.mapPut("targetOfAssessmentURI", Utils.toObjectURI(targetProfile.getOID()));
//            assessmentMap.mapPut("targetType", targetProfile.getClass().toString());

        } catch (Exception e) {

        }

        return assessmentMap;
    }

}
