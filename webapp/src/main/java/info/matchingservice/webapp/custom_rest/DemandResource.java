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

import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v1")
public class DemandResource extends ResourceAbstract {

    @GET
    @Path("/demands/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getDemandServices(@PathParam("instanceId") String instanceId)  {

        final Demands demands = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Demands.class);

        Demand activeDemand = demands.matchDemandApiId(instanceId);

        return Response.status(200).entity(demandRepresentation(activeDemand).toString()).build();
    }


    @DELETE
    @Path("/demands/{instanceId}")
    public Response deleteDemandsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the demands resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/demands/{instanceId}")
    public Response postDemandsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the demands resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/demands/{instanceId}")
    public Response putDemandsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the demands resource is not allowed.", new Object[0]);
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

    private JsonRepresentation demandRepresentation(Demand activeDemand){

        JsonRepresentation all = JsonRepresentation.newMap();

        // activedemand
        JsonRepresentation activedemand = JsonRepresentation.newMap();
        activedemand.mapPut("id", toApiID(activeDemand.getOID()));
        activedemand.mapPut("URI", toObjectURI(activeDemand.getOID()));
        activedemand.mapPut("description", activeDemand.getDemandDescription());
        activedemand.mapPut("summary", activeDemand.getDemandSummary());
        activedemand.mapPut("story", activeDemand.getDemandStory());
        if (activeDemand.getDemandOrSupplyProfileStartDate() == null){
            activedemand.mapPut("startDate", "");
        } else {
            activedemand.mapPut("startDate", activeDemand.getDemandOrSupplyProfileStartDate().toString());
        }
        if (activeDemand.getDemandOrSupplyProfileEndDate() == null){
            activedemand.mapPut("endDate", "");
        } else {
            activedemand.mapPut("endDate", activeDemand.getDemandOrSupplyProfileEndDate().toString());
        }

        // profiles
        JsonRepresentation demandsAndProfilesAndElements = JsonRepresentation.newArray();

            JsonRepresentation profiles = JsonRepresentation.newArray();

            for (Profile profile : activeDemand.getCollectDemandProfiles()) {

                JsonRepresentation profileElements = JsonRepresentation.newArray();


                for (ProfileElement element : profile.getCollectProfileElements()) {
                    JsonRepresentation profileElementMap = JsonRepresentation.newMap();
                    profileElementMap.mapPut("id", toApiID(element.getOID()));
                    profileElementMap.mapPut("URI", toObjectURI(element.getOID()));
                    profileElementMap.mapPut("description", element.getDescription());
                    profileElements.arrayAdd(profileElementMap);
                }

                JsonRepresentation profileAndElementMap = JsonRepresentation.newMap();
                profileAndElementMap.mapPut("id", toApiID(profile.getOID()));
                profileAndElementMap.mapPut("URI", toObjectURI(profile.getOID()));
                profileAndElementMap.mapPut("description", profile.getProfileName());

                //ProfileMatches
                JsonRepresentation profileMatchesArray = JsonRepresentation.newArray();
                for (ProfileMatch match : profile.getCollectPersistedProfileMatches()) {
                    JsonRepresentation profileMatchMap = JsonRepresentation.newMap();
                    profileMatchMap.mapPut("id", toApiID(match.getOID()));
                    profileMatchMap.mapPut("URI", toObjectURI(match.getOID()));
                    profileMatchMap.mapPut("title", match.title());
                    profileMatchMap.mapPut("demandProfileId", toApiID(match.getDemandProfile().getOID()));
                    profileMatchMap.mapPut("demandProfileURI", toObjectURI(match.getDemandProfile().getOID()));
                    profileMatchMap.mapPut("candidateStatus", match.getCandidateStatus().toString());
                    profileMatchesArray.arrayAdd(profileMatchMap);
                }
                profileAndElementMap.mapPut("profileMatches", profileMatchesArray);
                profileAndElementMap.mapPut("profileElements", profileElements);



                profiles.arrayAdd(profileAndElementMap);

            }

        activedemand.mapPut("profiles",profiles);

        all.mapPut("demand",activedemand);

        return all;

    }

}