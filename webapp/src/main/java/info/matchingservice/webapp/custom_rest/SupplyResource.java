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
import info.matchingservice.dom.Api.Viewmodels.SupplyViewModel;
import info.matchingservice.dom.Api.Viewmodels.TagHolderViewModel;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Tags.TagHolder;
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

@Path("/v2")
public class SupplyResource extends ResourceAbstract {

    private Gson gson = new Gson();
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

    @GET
    @Path("/supplies/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getSupplyServices(@PathParam("instanceId") Integer instanceId)  {

        Supply activeSupply = api.matchSupplyApiId(instanceId);
        if (activeSupply==null){
            String error = "{\"success\" : 0 , \"error\" : \"Supply not found or not authorized\"}";
            return Response.status(400).entity(error).build();
        }

        JsonObject result = createSupplyResult(activeSupply);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @Path("/supplies/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response putSupplyServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            Supply supplyToUpdate = api.matchSupplyApiIdForOwner(instanceId);

            if (supplyToUpdate==null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "supply not found or not authorized");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            String id1 = "description";
            String description;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                description = property.getString("");
            } catch (Exception e) {
                description = supplyToUpdate.getDescription();
            }


            String id4 = "startDate";
            String startDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id4, new Object[0]);
                startDate = property.getString("");
            } catch (Exception e) {
                startDate = supplyToUpdate.getStartDate().toString();
            }


            String id5 = "endDate";
            String endDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id5, new Object[0]);
                endDate = property.getString("");
            } catch (Exception e) {
                endDate = supplyToUpdate.getEndDate().toString();
            }

            String id6 = "imageUrl";
            String imageUrl = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id6, new Object[0]);
                imageUrl = property.getString("");
            } catch (Exception e) {
                imageUrl = supplyToUpdate.getImageUrl();
            }

            String id7 = "weight";
            Integer weight;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id7, new Object[0]);
                weight = property.getInt("");
            } catch (Exception e) {
                weight = supplyToUpdate.getWeight();
            }

            supplyToUpdate = api.updateSupply(instanceId, description, startDate, endDate, imageUrl, weight);

            JsonObject result = createSupplyResult(supplyToUpdate);
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();

        }

    }

    @POST
    @DELETE
    @Path("/supplies/{instanceId}")
    public Response putDemandsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }


    /* ****************************************** Supplies ************************************************************* */

    @GET
    @Path("/supplies")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getSuppliesServices() {

        // persons
        List<SupplyViewModel> supplyViewModels = new ArrayList<>();
        for (Supply supply : api.allSupplies()) {
            SupplyViewModel supplyViewModel = new SupplyViewModel(supply);
            supplyViewModels.add(supplyViewModel);
        }
        JsonElement supplyRepresentation = gson.toJsonTree(supplyViewModels);

        // build result
        JsonObject result = new JsonObject();
        result.add("supplies", supplyRepresentation);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();


    }

    @DELETE
    @POST
    @PUT
    @Path("/supplies")
    public Response putpostdeleteSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }

    /**
     * 
     * @param activeSupply
     * @return
     */
    private JsonObject createSupplyResult(final Supply activeSupply) {

        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        // supply
        SupplyViewModel supplyViewModel = new SupplyViewModel(activeSupply);
        JsonElement supplyRepresentation = gson.toJsonTree(supplyViewModel);
        result.add("supply", supplyRepresentation);

        // sideload profiles
        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(activeSupply)) {
            profileViewModels.add(new ProfileViewModel(profile));
        }
        JsonElement profilesRepresentation = gson.toJsonTree(profileViewModels);
        result.add("profiles", profilesRepresentation);

        // sideload (profile) elements
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(activeSupply)) {
            for (ProfileElement element : profile.getElements()){
                profileElementList.add(element);
            }
        }
        JsonElement profileElements = GenerateJsonElementService.generateProfileElements(profileElementList);
        result.add("elements", profileElements);

        // sideload tagholders
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(activeSupply)) {
            for (ProfileElement element : profile.getElements()){
                if (element.getClass().equals(ProfileElementTag.class)) {
                    profileElementTagList.add((ProfileElementTag) element);
                }
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

}
