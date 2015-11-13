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
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.*;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatches;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
import org.apache.isis.applib.Identifier;
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
public class PersonResourceV2 extends ResourceAbstract {

    private Gson gson = new Gson();
    private CommunicationChannels communicationChannels = IsisContext.getPersistenceSession().getServicesInjector().lookupService(CommunicationChannels.class);
    private Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
    private ProfileMatches profileMatches = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileMatches.class);

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getActivePersonServices() {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Person activePerson = persons.activePerson();
        String apiNotes ="This is a deepnested presentation of activePerson (Object person connected to currentUser). Property 'profile' is added for convenience in the root. It is the active person's Supply of type PERSON_DEMANDSUPPLY which should be unique.";

        ActivePersonViewModel activePersonViewModel = new ActivePersonViewModel(activePerson, communicationChannels, apiNotes);

        JsonElement personRepresentation = gson.toJsonTree(activePersonViewModel);
        JsonObject result = new JsonObject();
        result.add("activePerson", personRepresentation);

        return Response.status(200).entity(result.toString()).build();
    }

    @GET
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getPersonServices(@PathParam("instanceId") Integer instanceId) {

        JsonObject result = createPersonResult(instanceId);
        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response putPersonServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        Person chosenPerson = persons.matchPersonApiId(instanceId);

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            // apply business logic: only owner can modify
            if (chosenPerson.disabled(Identifier.Type.ACTION) != null) {
                String disabledMsg = chosenPerson.disabled(Identifier.Type.ACTION);
                String error = "{\"success\" : 0 , \"error\" : \"";
                error = error.concat(disabledMsg);
                error = error.concat("\"}");
                return Response.status(401).entity(error).build();
            }

            // try and see if properties are present; if not replace by original
            String id = "firstName";
            String firstName;
            try {
            JsonRepresentation propertyFirstName = argRepr.getRepresentation(id, new Object[0]);
            firstName = propertyFirstName.getString("");
            } catch (Exception e) {
                firstName = chosenPerson.getFirstName();
            }

            String id2 = "middleName";
            String middleName;
            try {
                JsonRepresentation propertyMiddleName = argRepr.getRepresentation(id2, new Object[0]);
                middleName = propertyMiddleName.getString("");
            } catch (Exception e) {
                middleName = chosenPerson.getMiddleName();
            }

            String id3 = "lastName";
            String lastName;
            try {
                JsonRepresentation propertyLastName = argRepr.getRepresentation(id3, new Object[0]);
                lastName = propertyLastName.getString("");
            } catch (Exception e) {
                lastName = chosenPerson.getLastName();
            }

            String id4 = "dateOfBirth";
            String dateOfBirth;
            try {
                JsonRepresentation propertyDateOfBirth = argRepr.getRepresentation(id4, new Object[0]);
                dateOfBirth = propertyDateOfBirth.getString("");
            } catch (Exception e) {
                dateOfBirth = chosenPerson.getDateOfBirth().toString();
            }

            String id5 = "imageUrl";
            String imageUrl;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id5, new Object[0]);
                imageUrl = propertyImageUrl.getString("");
            } catch (Exception e) {
                imageUrl = chosenPerson.getImageUrl();
            }

            // update action through api method!
            api.updatePerson(chosenPerson, firstName, middleName, lastName, dateOfBirth, imageUrl);

            JsonObject result = createPersonResult(instanceId);
            JsonElement successElement = gson.toJsonTree(new Integer(1));
            result.add("success", successElement);

            return Response.status(200).entity(result.toString()).build();
        }
    }

    @GET
    @Path("/persons")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getAllPersonsServices() {

        // persons
        List<PersonViewModel> personViewModels = new ArrayList<>();
        for (Person person : persons.allPersons()) {
            PersonViewModel personViewModel = new PersonViewModel(person, api);
            personViewModels.add(personViewModel);
        }
        JsonElement personRepresentation = gson.toJsonTree(personViewModels);

        // build result
        JsonObject result = new JsonObject();
        result.add("persons", personRepresentation);

        return Response.status(200).entity(result.toString()).build();
    }

    @POST
    @Path("/persons/{instanceId}/actions/createPersonDemand")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response createPersonsDemandServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        Person chosenPerson = persons.matchPersonApiId(instanceId);
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);

        if(!argRepr.isMap())
        {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            // apply business logic: only owner can modify
            if (chosenPerson.disabled(Identifier.Type.ACTION) != null) {
                String disabledMsg = chosenPerson.disabled(Identifier.Type.ACTION);
                String error = "{\"success\" : 0 , \"error\" : \"";
                error = error.concat(disabledMsg);
                error = error.concat("\"}");
                return Response.status(401).entity(error).build();
            }

            // try and see if properties are present; if not replace by original
            String id = "description";
            String description;
            try {
                JsonRepresentation propertyDescription = argRepr.getRepresentation(id, new Object[0]);
                description = propertyDescription.getString("");
            } catch (Exception e) {
                String error = "{\"success\" : 0 , \"error\" : \"parameter 'description' is required\"}";
                return Response.status(400).entity(error).build();
            }

            String id2 = "summary";
            String summary = null;
            try {
                JsonRepresentation propertySummary = argRepr.getRepresentation(id2, new Object[0]);
                summary = propertySummary.getString("");
            } catch (Exception e) {
                //
            }

            String id3 = "story";
            String story = null;
            try {
                JsonRepresentation propertyStory = argRepr.getRepresentation(id3, new Object[0]);
                story = propertyStory.getString("");
            } catch (Exception e) {
                //
            }

            String id4 = "startDate";
            String startDate = null;
            try {
                JsonRepresentation propertyStartDate = argRepr.getRepresentation(id4, new Object[0]);
                startDate = propertyStartDate.getString("");
            } catch (Exception e) {
                //
            }

            String id5 = "endDate";
            String endDate = null;
            try {
                JsonRepresentation propertyEndDate = argRepr.getRepresentation(id5, new Object[0]);
                endDate = propertyEndDate.getString("");
            } catch (Exception e) {
                //
            }

            Demand demand = api.createPersonDemand(chosenPerson, description, summary, story, startDate, endDate);

            if (demand==null){
                String error = "{\"success\" : 0 , \"error\" : \"not able to create demand - please check parameters\"}";
                return Response.status(400).entity(error).build();
            }

            JsonObject result = new JsonObject();

            DemandViewModel demandViewModel = new DemandViewModel(demand, api);
            JsonElement demandResult = gson.toJsonTree(demandViewModel);
            result.add("demand", demandResult);

            JsonElement successElement = gson.toJsonTree(new Integer(1));
            result.add("success", successElement);

            return Response.status(200).entity(result.toString()).build();
        }
    }

    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the v1 resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/")
    public Response postServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the v1 resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/")
    public Response putServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the v1 resource is not allowed.", new Object[0]);
    }

    @DELETE
    @Path("/people/{instanceId}")
    public Response deletePeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the people resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/people/{instanceId}")
    public Response postPeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the people resource is not allowed.", new Object[0]);
    }

    @GET
    @Path("/apidoc")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getApiDocServices() {

        String apiDocLine1;
        String apiDocLine2;
        String apiDocLine3;
        String apiDocLine4;
        JsonObject result = new JsonObject();

        apiDocLine1 = "Custom Rest Api documentation";
        JsonElement line1 = gson.toJsonTree(apiDocLine1);
        result.add("TITLE",line1);

        apiDocLine2 = "GET '/', GET '/persons', GET '/persons/{id}', GET '/demands/{id}' , POST '/persons/{id}/actions/createPersonDemand', PUT '/persons/{id}'";
        JsonElement line2 = gson.toJsonTree(apiDocLine2);
        result.add("EndPoints",line2);

        apiDocLine3 = "IDEMPOTENT; Properties that can be updated: firstName, lastName, middleName, dateOfBirth, imageUrl";
        JsonElement line3 = gson.toJsonTree(apiDocLine3);
        result.add("PUT '/persons/{id}'",line3);

        apiDocLine4 = "NON-IDEMPOTENT; Payload: description (Mandatory), summary, story, startDate, endDate";
        JsonElement line4 = gson.toJsonTree(apiDocLine4);
        result.add("POST '/persons/{id}/actions/createPersonDemand'",line4);

        return Response.status(200).entity(result.toString()).build();

    }


    /**
     *
     * @param instanceId
     * @return  A JsonObject containing a person representation with sideloaded collections
     */
    private JsonObject createPersonResult(final Integer instanceId) {

        JsonObject result = new JsonObject();

        // person
        Person chosenPerson = persons.matchPersonApiId(instanceId);
        PersonViewModel personViewModel = new PersonViewModel(chosenPerson, api);
        JsonElement personRepresentation = gson.toJsonTree(personViewModel);
        result.add("person", personRepresentation);

        // sideload supplies
        result.add("supplies", sideLoadSupplies(chosenPerson));

        // sideload demands
        result.add("demands", sideLoadDemands(chosenPerson));

        //sideload profiles
        result.add("profiles", sideLoadProfiles(chosenPerson));

        //sideload elements
        result.add("elements", sideLoadProfileElements(chosenPerson));

        //sideload tagholders
        result.add("tagHolders", sideLoadTagHolders(chosenPerson));

        // sideload personal contacts
        result.add("personalContacts", sideLoadPersonalContacts(chosenPerson));

        return result;
    }

    private JsonElement sideLoadSupplies(final Person person){

        List<SupplyViewModel> supplyViewmodels = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)) {
            supplyViewmodels.add(new SupplyViewModel(supply));
        }
        return gson.toJsonTree(supplyViewmodels);
    }

    private JsonElement sideLoadDemands(final Person person){

        List<DemandViewModel> demandViewmodels = new ArrayList<>();
        for (Demand demand : api.getDemandsForPerson(person)){
            demandViewmodels.add(new DemandViewModel(demand, api));
        }
        return gson.toJsonTree(demandViewmodels);
    }

    private JsonElement sideLoadProfiles(final Person person){

        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Supply supply : person.getSupplies()){
            for (Profile profile : supply.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile));
            }
        }
        for (Demand demand : person.getDemands()){
            for (Profile profile : demand.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile));
            }
        }
        return gson.toJsonTree(profileViewModels);
    }

    private JsonElement sideLoadProfileElements(final Person person){

        // collect the elements
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Supply supply : person.getSupplies()){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    profileElementList.add(element);
                }
            }
        }
        for (Demand demand : person.getDemands()){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    profileElementList.add(element);
                }
            }
        }
        // generate viewmodels
        List<ProfileElementViewModel> profileElementViewModels = new ArrayList<>();
        for (ProfileElement element : profileElementList){

            //profile element tag
            if (element.getClass().equals(ProfileElementTag.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTag) element);
                profileElementViewModels.add(model);
            }

            //required profile element role
            if (element.getClass().equals(RequiredProfileElementRole.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((RequiredProfileElementRole) element);
                profileElementViewModels.add(model);
            }

            //profile element location
            if (element.getClass().equals(ProfileElementLocation.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementLocation) element);
                profileElementViewModels.add(model);
            }

            //profile element boolean
            if (element.getClass().equals(ProfileElementBoolean.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementBoolean) element);
                profileElementViewModels.add(model);
            }

            //profile element dropdown
            if (element.getClass().equals(ProfileElementDropDown.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementDropDown) element);
                profileElementViewModels.add(model);
            }

            //profile element numeric
            if (element.getClass().equals(ProfileElementNumeric.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementNumeric) element);
                profileElementViewModels.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementText.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementText) element);
                profileElementViewModels.add(model);
            }

            //profile element time period
            if (element.getClass().equals(ProfileElementTimePeriod.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTimePeriod) element);
                profileElementViewModels.add(model);
            }

            //profile element use predicate
            if (element.getClass().equals(ProfileElementUsePredicate.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementUsePredicate) element);
                profileElementViewModels.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementChoice.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel(element);
                profileElementViewModels.add(model);
            }

        }

        return gson.toJsonTree(profileElementViewModels);
    }

    private JsonElement sideLoadTagHolders(final Person person){

        // collect the Tag elements
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Supply supply : person.getSupplies()){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }
        for (Demand demand : person.getDemands()){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }

        //generate viewmodels
        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList){
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        return gson.toJsonTree(tagHolderViewModels);
    }

    private JsonElement sideLoadPersonalContacts(final Person person){
        List<PersonalContactViewModel> personalContactViewmodels = new ArrayList<>();
        for (PersonalContact contact : api.getPersonalContacts(person)){
            personalContactViewmodels.add(new PersonalContactViewModel(contact));
        }
        return gson.toJsonTree(personalContactViewmodels);
    }

}
