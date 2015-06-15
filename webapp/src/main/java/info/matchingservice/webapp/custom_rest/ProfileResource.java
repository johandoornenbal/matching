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

import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Match.ProfileComparison;
import info.matchingservice.dom.Match.ProfileComparisons;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementChoice;
import info.matchingservice.dom.Profile.ProfileElementChoices;
import info.matchingservice.dom.Profile.ProfileElementText;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.Profiles;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v1")
public class ProfileResource extends ResourceAbstract {

    @GET
    @Path("/demandprofiles/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getProfileServices(@PathParam("instanceId") String instanceId)  {

        final Profiles profiles = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Profiles.class);

        Profile activeProfile = profiles.matchProfileApiId(instanceId);

        return Response.status(200).entity(profileRepresentation(activeProfile).toString()).build();
    }


    @DELETE
    @Path("/demandprofiles/{instanceId}")
    public Response deleteProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the demandprofiles resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/demandprofiles/{instanceId}")
    public Response postProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the demandprofiles resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/demandprofiles/{instanceId}")
    public Response putProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the demandprofiles resource is not allowed.", new Object[0]);
    }


    @GET
    @Path("/supplyprofiles/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getSupplyProfileServices(@PathParam("instanceId") String instanceId)  {

        final Profiles profiles = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Profiles.class);

        Profile activeProfile = profiles.matchProfileApiId(instanceId);

        return Response.status(200).entity(profileRepresentation(activeProfile).toString()).build();
    }


    @DELETE
    @Path("/supplyprofiles/{instanceId}")
    public Response deleteSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the supplyprofiles resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/supplyprofiles/{instanceId}")
    public Response postSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the supplyprofiles resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/supplyprofiles/{instanceId}")
    public Response putSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the supplyprofiles resource is not allowed.", new Object[0]);
    }



    private String toObjectURI(final String OID){
        String[] parts = OID.split(Pattern.quote("[OID]"));
        String part1 = parts[0];
        String part2 = parts[1];
        String URI = "objects/".concat(part2).concat("/L_").concat(part1);
        return URI;
    }

    private String toApiID(final String OID){
        String[] parts = OID.split(Pattern.quote("[OID]"));
        String part1 = parts[0];
        String ApiID = "L_".concat(part1);
        return ApiID;
    }

    private JsonRepresentation profileRepresentation(Profile activeProfile){

        JsonRepresentation all = JsonRepresentation.newMap();

        // activeprofile
        JsonRepresentation activeprofile = JsonRepresentation.newMap();
        activeprofile.mapPut("id", toApiID(activeProfile.getOID()));
        activeprofile.mapPut("URI", toObjectURI(activeProfile.getOID()));
        activeprofile.mapPut("description", activeProfile.getProfileName());
        if (activeProfile.getProfileStartDate()==null){
            activeprofile.mapPut("startDate", "");
        } else {
            activeprofile.mapPut("startDate", activeProfile.getProfileStartDate().toString());
        }
        if (activeProfile.getProfileEndDate()==null){
            activeprofile.mapPut("endDate", "");
        } else {
            activeprofile.mapPut("endDate", activeProfile.getProfileEndDate().toString());
        }
        if (activeProfile.getChosenProfileMatch() == null) {
            activeprofile.mapPut("chosenProfileMatchId", "");
            activeprofile.mapPut("chosenProfileMatchURI", "");
        } else {
            activeprofile.mapPut("chosenProfileMatchId", toApiID(activeProfile.getChosenProfileMatch().getOID()));
            activeprofile.mapPut("chosenProfileMatchURI", toObjectURI(activeProfile.getChosenProfileMatch().getOID()));
        }

        // profilesElements
        JsonRepresentation profileElements = JsonRepresentation.newArray();

        for (ProfileElement element : activeProfile.getCollectProfileElements()) {
            JsonRepresentation profileElementMap = JsonRepresentation.newMap();
            profileElementMap.mapPut("id", toApiID(element.getOID()));
            profileElementMap.mapPut("URI", toObjectURI(element.getOID()));
            profileElementMap.mapPut("description", element.getDescription());
            profileElementMap.mapPut("displayValue", element.getDisplayValue());
            profileElementMap.mapPut("isActive", element.getIsActive());
            if (element.getProfileElementType().equals(ProfileElementType.PASSION)) {
                ProfileElementText passionTag = (ProfileElementText) profileElementsRepo.findProfileElementByUniqueId(element.getUniqueItemId()).get(0);
                        profileElementMap.mapPut("passion", passionTag.getTextValue());
            }
            profileElements.arrayAdd(profileElementMap);
        }

        activeprofile.mapPut("profileElements", profileElements);

        //ProfileComparisons
        JsonRepresentation profileComparisonsArray = JsonRepresentation.newArray();
        for (ProfileComparison comp : profileComparisonsRepo.collectDemandProfileComparisons(activeProfile)) {
            JsonRepresentation profileComparisonMap = JsonRepresentation.newMap();
            profileComparisonMap.mapPut("calculatedMatchingValue", comp.getCalculatedMatchingValue());
            profileComparisonMap.mapPut("demandProfileId", toApiID(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileURI", toObjectURI(comp.getDemandProfile().getOID()));
            profileComparisonMap.mapPut("demandProfileDescription", comp.getDemandProfile().getProfileName());
            Person demandingPerson = (Person) comp.getDemandingPerson();
            profileComparisonMap.mapPut("demandingPersonId", toApiID(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonURI", toObjectURI(demandingPerson.getOID()));
            profileComparisonMap.mapPut("demandingPersonName", demandingPerson.title());
            profileComparisonsArray.arrayAdd(profileComparisonMap);
        }
        activeprofile.mapPut("profileComparisons", profileComparisonsArray);

        //ProfileMatches
        JsonRepresentation profileMatchesArray = JsonRepresentation.newArray();
        for (ProfileMatch match : activeProfile.getCollectPersistedProfileMatches()) {
            JsonRepresentation profileMatchMap = JsonRepresentation.newMap();
            profileMatchMap.mapPut("id", toApiID(match.getOID()));
            profileMatchMap.mapPut("URI", toObjectURI(match.getOID()));
            profileMatchMap.mapPut("title", match.title());
            profileMatchMap.mapPut("demandProfileId", toApiID(match.getDemandProfile().getOID()));
            profileMatchMap.mapPut("demandProfileURI", toObjectURI(match.getDemandProfile().getOID()));
            profileMatchMap.mapPut("candidateStatus", match.getCandidateStatus().toString());
            profileMatchesArray.arrayAdd(profileMatchMap);
        }
        activeprofile.mapPut("profileMatches", profileMatchesArray);


        //Choices
        JsonRepresentation profileElementChoicesArray = JsonRepresentation.newArray();
        for (ProfileElementChoice choice : profileElementChoices.profileElementChoices(activeProfile.getDemandOrSupply())){
            JsonRepresentation profileElementChoiceMap = JsonRepresentation.newMap();
            profileElementChoiceMap.mapPut("widgetType", choice.getWidgetType().toString());
            profileElementChoiceMap.mapPut("description", choice.getDescription());
            profileElementChoiceMap.mapPut("action", choice.getAction());
            profileElementChoicesArray.arrayAdd(profileElementChoiceMap);
        }
        activeprofile.mapPut("profileElementChoices", profileElementChoicesArray);

        if (activeProfile.getDemandOrSupply() == DemandOrSupply.DEMAND) {
            all.mapPut("demandprofile", activeprofile);
        } else {
            all.mapPut("supplyprofile", activeprofile);
        }

        return all;

    }

    private ProfileElementChoices profileElementChoices = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileElementChoices.class);
    private ProfileElements profileElementsRepo = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileElements.class);
    private ProfileComparisons profileComparisonsRepo = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileComparisons.class);
}
