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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.DemandViewModel;
import info.matchingservice.dom.Api.Viewmodels.ProfileViewModel;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.Profile.Profile;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2")
public class DemandResourceV2 extends ResourceAbstract {

    private Gson gson = new Gson();
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
    final Demands demands = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Demands.class);

    @GET
    @Path("/demands/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getDemandServices(@PathParam("instanceId") String instanceId)  {

        Demand activeDemand = demands.matchDemandApiId(instanceId);

        // apply business logic - check if hidden
        Person demandOwner = (Person) activeDemand.getOwner();
        if (demandOwner.hideDemands()) {
            String error = "{\"success\" : 0 , \"error\" : \"Not authorized\"}";
            return Response.status(401).entity(error).build();
        }

        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        // demand
        DemandViewModel demandViewModel = new DemandViewModel(activeDemand, api);
        JsonElement demandRepresentation = gson.toJsonTree(demandViewModel);
        result.add("demand", demandRepresentation);

        // sideload profiles
        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Profile profile : api.getProfilesForDemand(activeDemand)) {
            profileViewModels.add(new ProfileViewModel(profile));
        }
        JsonElement profilesRepresentation = gson.toJsonTree(profileViewModels);
        result.add("profiles", profilesRepresentation);

        return Response.status(200).entity(result.toString()).build();
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

    @POST
    @Path("/demands")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response postDemandServices(InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            String errorMessage = "";
            boolean error = false;

            String id = "ownerId";
            Integer ownerId;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id, new Object[0]);
                ownerId = property.getInt("");
            } catch (Exception e) {
                ownerId = null;
                errorMessage = errorMessage.concat(" property 'ownerID' is mandatory");
                error = true;
            }

            String id1 = "description";
            String description;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                description = property.getString("");
            } catch (Exception e) {
                description = null;
                errorMessage = errorMessage.concat(" property 'description' is mandatory");
                error = true;
            }


            String id2 = "summary";
            String summary = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id2, new Object[0]);
                summary = property.getString("");
            } catch (Exception e) {
                //ignore
            }

            String id3 = "story";
            String story = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id3, new Object[0]);
                story = property.getString("");
            } catch (Exception e) {
                //ignore
            }

            String id4 = "startDate";
            String startDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id4, new Object[0]);
                startDate = property.getString("");
            } catch (Exception e) {
                //ignore
            }


            String id5 = "endDate";
            String endDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id5, new Object[0]);
                endDate = property.getString("");
            } catch (Exception e) {
                //ignore
            }


            //catch errors and return 400
            if (error){
                JsonObject result = new JsonObject();
                result.addProperty("error", errorMessage);
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            Person person = api.findPersonById(ownerId);

            Demand newDemand = api.createPersonDemand(person, description, summary, story, startDate, endDate);

            if (newDemand==null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "creation of new demand not succeeded; are you sure you have the rights?");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            JsonObject result = new JsonObject();
            DemandViewModel demandViewModel = new DemandViewModel(newDemand, api);
            JsonElement demandElement = gson.toJsonTree(demandViewModel);

            result.add("demand", demandElement);
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();
        }

    }

    @DELETE
    @GET
    @PUT
    @Path("/demands")
    public Response putgetdeleteDemandsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }

}
