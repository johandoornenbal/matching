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
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.ProfileViewModel;
import info.matchingservice.dom.Api.Viewmodels.TagHolderViewModel;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2")
public class ProfileResource extends ResourceAbstract {

    private Gson gson = new Gson();
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

    @GET
    @Path("/profiles/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getProfileServices(@PathParam("instanceId") Integer instanceId)  {

        Profile activeProfile = api.matchProfileApiId(instanceId);
        if (activeProfile == null) {
            String error = "{\"success\" : 0 , \"error\" : \"Profile not found or not authorized\"}";
            return Response.status(400).entity(error).build();
        }

        JsonObject result = createProfileResult(activeProfile);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }

    private JsonObject createProfileResult(final Profile activeProfile) {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        // profile
        ProfileViewModel profileViewModel = new ProfileViewModel(activeProfile);
        JsonElement profileRepresentation = gson.toJsonTree(profileViewModel);
        result.add("profile", profileRepresentation);

        // sideload elements
        JsonElement profileElements = GenerateJsonElementService.generateProfileElements(new ArrayList<>(activeProfile.getElements()));
        result.add("elements", profileElements);

        // sideload tagholders
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (ProfileElement element : activeProfile.getElements()){
             if (element.getClass().equals(ProfileElementTag.class)) {
                 profileElementTagList.add((ProfileElementTag) element);
             }
        }
        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList){
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        JsonElement tagholders = gson.toJsonTree(tagHolderViewModels);
        result.add("tagholders", tagholders);

        return result;
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
    public Response getSupplyProfileServices(@PathParam("instanceId") Integer instanceId)  {

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


    private JsonRepresentation profileRepresentation(Profile activeProfile){

        JsonRepresentation all = JsonRepresentation.newMap();

        ProfileRepresentation rep = new ProfileRepresentation();

        if (activeProfile.getDemandOrSupply() == DemandOrSupply.DEMAND) {
            all.mapPut("demandprofile", rep.ObjectRepresentation(activeProfile));
        } else {
            all.mapPut("supplyprofile", rep.ObjectRepresentation(activeProfile));
        }

        return all;

    }

}
