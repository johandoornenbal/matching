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

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Match.ProfileComparisons;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementChoice;
import info.matchingservice.dom.Profile.ProfileElementChoices;

/**
 * Created by jodo on 18/06/15.
 */
public class ProfileRepresentation {

    ProfileRepresentation(){
    }

    public JsonRepresentation ObjectRepresentation(final Profile profile) {

        JsonRepresentation profileAndElementMap = JsonRepresentation.newMap();

        profileAndElementMap.mapPut("id", Utils.toApiID(profile.getOID()));
        profileAndElementMap.mapPut("URI", Utils.toObjectURI(profile.getOID()));
        profileAndElementMap.mapPut("description", profile.getProfileName());
        if (profile.getProfileStartDate()==null){
            profileAndElementMap.mapPut("startDate", "");
        } else {
            profileAndElementMap.mapPut("startDate", profile.getProfileStartDate().toString());
        }
        if (profile.getProfileEndDate()==null){
            profileAndElementMap.mapPut("endDate", "");
        } else {
            profileAndElementMap.mapPut("endDate", profile.getProfileEndDate().toString());
        }
        if (profile.getChosenProfileMatch() == null) {
            profileAndElementMap.mapPut("chosenProfileMatchId", "");
            profileAndElementMap.mapPut("chosenProfileMatchURI", "");
        } else {
            profileAndElementMap.mapPut("chosenProfileMatchId", Utils.toApiID(profile.getChosenProfileMatch().getOID()));
            profileAndElementMap.mapPut("chosenProfileMatchURI", Utils.toObjectURI(profile.getChosenProfileMatch().getOID()));
        }
        if (profile.getImageUrl() == null) {
            profileAndElementMap.mapPut("imageUrl", "");
        } else {
            profileAndElementMap.mapPut("imageUrl", profile.getImageUrl());
        }

        //Profile Elements
        JsonRepresentation profileElements = JsonRepresentation.newArray();
        for (ProfileElement element : profile.getCollectProfileElements()) {
            ProfileElementRepresentation rep = new ProfileElementRepresentation();
            profileElements.arrayAdd(rep.ObjectRepresentation(element));
        }
        profileAndElementMap.mapPut("profileElements", profileElements);

        //ProfileMatches
        JsonRepresentation profileMatchesArray = JsonRepresentation.newArray();
        for (ProfileMatch match : profile.getCollectPersistedProfileMatches()) {
            ProfileMatchRepresentation rep = new ProfileMatchRepresentation();
            profileMatchesArray.arrayAdd(rep.ObjectRepresentation(match));
        }
        profileAndElementMap.mapPut("profileMatches", profileMatchesArray);

        //ProfileComparisons
        JsonRepresentation profileComparisonsArray = JsonRepresentation.newArray();
        //for demandprofile
        for (ProfileComparison comp : profileComparisonsRepo.collectProfileComparisons(profile)) {
            JsonRepresentation profileComparisonMap = JsonRepresentation.newMap();
            profileComparisonMap.mapPut("calculatedMatchingValue", comp.getCalculatedMatchingValue());
            profileComparisonMap.mapPut("demandProfileId", Utils.toApiID(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileURI", Utils.toObjectURI(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileDescription", comp.getDemandProfile().getProfileName());
            Person demandingPerson = (Person) comp.getDemandingPerson();
            profileComparisonMap.mapPut("demandingPersonId", Utils.toApiID(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonURI", Utils.toObjectURI(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonName", demandingPerson.title());
            profileComparisonMap.mapPut("demandingPersonPictureLink", demandingPerson.getPictureUrl());
            Person supplyingPerson = (Person) comp.getProposedPerson();
            profileComparisonMap.mapPut("proposedPersonId", Utils.toApiID(supplyingPerson.getOID()));
            profileComparisonMap.mapPut("proposedPersonURI", Utils.toObjectURI(supplyingPerson.getOID()));
            profileComparisonMap.mapPut("proposedPersonName", supplyingPerson.title());
            profileComparisonMap.mapPut("proposedPersonPictureLink", supplyingPerson.getPictureUrl());
            profileComparisonsArray.arrayAdd(profileComparisonMap);
        }
        //for supply profile
        for (ProfileComparison comp : profileComparisonsRepo.collectDemandProfileComparisons(profile)) {
            JsonRepresentation profileComparisonMap = JsonRepresentation.newMap();
            profileComparisonMap.mapPut("calculatedMatchingValue", comp.getCalculatedMatchingValue());
            profileComparisonMap.mapPut("demandProfileId", Utils.toApiID(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileURI", Utils.toObjectURI(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileDescription", comp.getDemandProfile().getProfileName());
            Person demandingPerson = (Person) comp.getDemandingPerson();
            profileComparisonMap.mapPut("demandingPersonId", Utils.toApiID(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonURI", Utils.toObjectURI(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonName", demandingPerson.title());
            profileComparisonMap.mapPut("demandingPersonPictureLink", demandingPerson.getPictureUrl());
            Person supplyingPerson = (Person) comp.getProposedPerson();
            profileComparisonMap.mapPut("proposedPersonId", Utils.toApiID(supplyingPerson.getOID()));
            profileComparisonMap.mapPut("proposedPersonURI", Utils.toObjectURI(supplyingPerson.getOID()));
            profileComparisonMap.mapPut("proposedPersonName", supplyingPerson.title());
            profileComparisonMap.mapPut("proposedPersonPictureLink", supplyingPerson.getPictureUrl());
            profileComparisonsArray.arrayAdd(profileComparisonMap);
        }
        profileAndElementMap.mapPut("profileComparisons", profileComparisonsArray);

        //Choices
        JsonRepresentation profileElementChoicesArray = JsonRepresentation.newArray();
        for (ProfileElementChoice choice : profileElementChoices.profileElementChoices(profile.getDemandOrSupply())){
            JsonRepresentation profileElementChoiceMap = JsonRepresentation.newMap();
            profileElementChoiceMap.mapPut("widgetType", choice.getWidgetType().toString());
            profileElementChoiceMap.mapPut("description", choice.getDescription());
            profileElementChoiceMap.mapPut("action", choice.getAction());
            profileElementChoicesArray.arrayAdd(profileElementChoiceMap);
        }
        profileAndElementMap.mapPut("profileElementChoices", profileElementChoicesArray);

        //Assessments
        JsonRepresentation assessmentsArray = JsonRepresentation.newArray();
        for (Assessment assessment : profile.getCollectAssessments()){
            AssessmentRepresentation rep = new AssessmentRepresentation();
            assessmentsArray.arrayAdd(rep.ObjectRepresentation(assessment));
        }
        profileAndElementMap.mapPut("assessments", assessmentsArray);

        return profileAndElementMap;
    }

    private ProfileElementChoices profileElementChoices = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileElementChoices.class);
    private ProfileComparisons profileComparisonsRepo = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileComparisons.class);
}
