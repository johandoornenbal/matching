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
import info.matchingservice.dom.Api.Viewmodels.ProfileViewModel;
import info.matchingservice.dom.Api.Viewmodels.TagHolderViewModel;
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

    @PUT
    @Path("/profiles/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response putProfilesServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            Profile profileToUpdate = api.matchProfileApiIdForOwner(instanceId);

            if (profileToUpdate == null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "profile not found or not authorized");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            String id1 = "name";
            String name;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                name = property.getString("");
            } catch (Exception e) {
                name = profileToUpdate.getName();
            }


            String id4 = "startDate";
            String startDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id4, new Object[0]);
                startDate = property.getString("");
            } catch (Exception e) {
                if (profileToUpdate.getStartDate()!=null) {
                    startDate = profileToUpdate.getStartDate().toString();
                }
            }


            String id5 = "endDate";
            String endDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id5, new Object[0]);
                endDate = property.getString("");
            } catch (Exception e) {
                if (profileToUpdate.getEndDate()!=null) {
                    endDate = profileToUpdate.getEndDate().toString();
                }
            }

            String id6 = "imageUrl";
            String imageUrl = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id6, new Object[0]);
                imageUrl = property.getString("");
            } catch (Exception e) {
                imageUrl = profileToUpdate.getImageUrl();
            }

            String id7 = "weight";
            Integer weight;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id7, new Object[0]);
                weight = property.getInt("");
            } catch (Exception e) {
                weight = profileToUpdate.getWeight();
            }

            profileToUpdate = api.updateProfile(instanceId, name, startDate, endDate, imageUrl, weight);

            JsonObject result = createProfileResult(profileToUpdate);
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();
        }
    }

    @DELETE
    @Path("/profiles/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response deleteProfilesServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            String id1 = "confirmDelete";
            Boolean confirmDelete;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                confirmDelete = property.getBoolean("");
            } catch (Exception e) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "property 'confirmDelete' is mandatory (and must be set to true).");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            Profile profileToDelete = api.matchProfileApiIdForOwner(instanceId);

            if (profileToDelete == null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "profile not found or not authorized");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            //TODO: logic should move to API or use wrapper (?)
            if (profileToDelete.validateDeleteProfile(confirmDelete)!=null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", profileToDelete.validateDeleteProfile(confirmDelete));
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            profileToDelete.deleteProfile(confirmDelete);

            JsonObject result = new JsonObject();
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();

        }
    }

    @POST
    @Path("/profiles/{instanceId}")
    public Response postProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }

    @POST
    @Path("/profiles")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response postProfileServices(InputStream object){

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            String errorMessage = "";
            boolean error = false;

            String id1 = "name";
            String name;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                name = property.getString("");
            } catch (Exception e) {
                name = null;
                errorMessage = errorMessage.concat(" property 'name' is mandatory");
                error = true;
            }

            String id2 = "demand";
            Integer demand;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id2, new Object[0]);
                demand = property.getInt("");
            } catch (Exception e) {
                demand = null;
            }

            String id3 = "supply";
            Integer supply;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id3, new Object[0]);
                supply = property.getInt("");
            } catch (Exception e) {
                supply = null;
            }


            String id4 = "startDate";
            String startDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id4, new Object[0]);
                startDate = property.getString("");
            } catch (Exception e) {
                // ignore
            }


            String id5 = "endDate";
            String endDate = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id5, new Object[0]);
                endDate = property.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id6 = "imageUrl";
            String imageUrl = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id6, new Object[0]);
                imageUrl = property.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id7 = "weight";
            Integer weight;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id7, new Object[0]);
                weight = property.getInt("");
            } catch (Exception e) {
                weight = 10;
            }

            //catch errors and return 400
            if (error){
                JsonObject result = new JsonObject();
                result.addProperty("error", errorMessage);
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            Person person = api.activePerson();

            Profile profile = api.createPersonProfile(name, weight, startDate, endDate, demand, supply, imageUrl, person);

            if (profile == null) {
                JsonObject result = new JsonObject();
                result.addProperty("error", "creation of new profile not succeeded; are you sure you have the rights and the right params?");
                result.addProperty("success", 0);
                return Response.status(400).entity(result.toString()).build();
            }

            JsonObject result = createProfileResult(profile);
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();
        }



    }


    @GET
    @PUT
    @DELETE
    @Path("/profiles")
    public Response getputdeleteProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }


}
