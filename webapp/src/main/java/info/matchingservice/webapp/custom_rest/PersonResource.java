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
import info.matchingservice.dom.Assessment.*;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
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
public class PersonResource extends ResourceAbstract {

    private Gson gson = new Gson();
    private Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

    /************************** ROOT *************************************/

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getActivePersonServices() {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Person activePerson = persons.activePerson();
        if (activePerson == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found. Most likely cause: for this login no Person object created.\"}";
            return Response.status(400).entity(error).build();
        }

        JsonObject result = createPersonResult(activePerson.getIdAsInt());

        if (result == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found. Most likely cause: for this login no Person object created.\"}";
            return Response.status(400).entity(error).build();
        }

        return Response.status(200).entity(result.toString()).build();
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

    /************************** persons/{id} *************************************/

    @GET
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getPersonServices(@PathParam("instanceId") Integer instanceId) {

        JsonObject result = createPersonResult(instanceId);
        if (result == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found\"}";
            return Response.status(400).entity(error).build();
        }
        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response putPersonServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        Person chosenPerson = api.findPersonById(instanceId);
        if (chosenPerson == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found\"}";
            return Response.status(400).entity(error).build();
        }

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

            String id6 = "mainEmail";
            String mainEmail = null;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id6, new Object[0]);
                mainEmail = propertyImageUrl.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id7 = "mainPhone";
            String mainPhone = null;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id7, new Object[0]);
                mainPhone = propertyImageUrl.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id8 = "mainAddress";
            String mainAddress = null;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id8, new Object[0]);
                mainAddress = propertyImageUrl.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id9 = "mainPostalCode";
            String mainPostalCode = null;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id9, new Object[0]);
                mainPostalCode = propertyImageUrl.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id10 = "mainTown";
            String mainTown = null;
            try {
                JsonRepresentation propertyImageUrl = argRepr.getRepresentation(id10, new Object[0]);
                mainTown = propertyImageUrl.getString("");
            } catch (Exception e) {
                // ignore
            }

            // update action through api method!
            api.updatePerson(
                    chosenPerson,
                    firstName,
                    middleName,
                    lastName,
                    dateOfBirth,
                    imageUrl,
                    mainEmail,
                    mainPhone,
                    mainAddress,
                    mainPostalCode,
                    mainTown);

            JsonObject result = createPersonResult(instanceId);
            JsonElement successElement = gson.toJsonTree(new Integer(1));
            result.add("success", successElement);

            return Response.status(200).entity(result.toString()).build();
        }
    }

    @DELETE
    @Path("/persons/{instanceId}")
    public Response deletePersonsIdNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed", new Object[0]);
    }

    @POST
    @Path("/persons/{instanceId}")
    public Response postPersonsIdNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed", new Object[0]);
    }




    /************************** Persons *************************************/



    @GET
    @Path("/persons")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getAllPersonsServices() {

        // persons
        List<PersonViewModel> personViewModels = new ArrayList<>();
        for (Person person : api.allActivePersons()) {
            PersonViewModel personViewModel = new PersonViewModel(person, api);
            personViewModels.add(personViewModel);
        }
        JsonElement personRepresentation = gson.toJsonTree(personViewModels);

        // build result
        JsonObject result = new JsonObject();
        result.add("persons", personRepresentation);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }


    @PUT
    @Path("/persons")
    public Response putPersonsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }

    @DELETE
    @Path("/persons")
    public Response deletePersonsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }

    @POST
    @Path("/persons")
    public Response postPersonsNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Not allowed.", new Object[0]);
    }


    /************************** persons/{id}/actions *************************************/


    @PUT
    @POST
    @Path("/persons/{instanceId}/actions/addAsPersonalContact")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response addAsPersonalContactServices(@PathParam("instanceId") Integer instanceId) {

        Person chosenPerson = api.findPersonById(instanceId);
        if (chosenPerson == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found\"}";
            return Response.status(400).entity(error).build();
        }

        PersonalContact personalContact = api.findOrCreatePersonalContact(chosenPerson);
        if (personalContact==null) {
            String error = "{\"success\" : 0 , \"error\" : \"Cannot contact yourself\"}";
            return Response.status(400).entity(error).build();
        }

        PersonalContactViewModel personalContactViewModel = new PersonalContactViewModel(personalContact);

        JsonObject result = new JsonObject();
        JsonElement personalContactElement = gson.toJsonTree(personalContactViewModel);
        result.add("personalContact", personalContactElement);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @POST
    @Path("/persons/{instanceId}/actions/removeAsPersonalContact")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response removeAsPersonalContactServices(@PathParam("instanceId") Integer instanceId) {

        Person chosenPerson = api.findPersonById(instanceId);
        if (chosenPerson == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found\"}";
            return Response.status(400).entity(error).build();
        }

        Person personResult = api.removeAsPersonalContact(chosenPerson);

        PersonViewModel personViewModel = new PersonViewModel(personResult, api);

        JsonObject result = new JsonObject();
        JsonElement personalContactElement = gson.toJsonTree(personViewModel);
        result.add("person", personalContactElement);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }

    @POST
    @PUT
    @Path("/persons/{instanceId}/actions/createSupplyAndProfile")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response createSupplyAndProfileServices(@PathParam("instanceId") Integer instanceId) {

        Person chosenPerson = api.findPersonById(instanceId);
        if (chosenPerson == null){
            String error = "{\"success\" : 0 , \"error\" : \"person not found\"}";
            return Response.status(400).entity(error).build();
        }

        Profile profileResult = api.createSupplyAndProfile(chosenPerson);
        if (profileResult==null){
            String error = "{\"success\" : 0 , \"error\" : \"supply and profile could not be created\"}";
            return Response.status(400).entity(error).build();
        }

        ProfileViewModel profileViewModel = new ProfileViewModel(profileResult);

        JsonObject result = new JsonObject();
        JsonElement profileElement = gson.toJsonTree(profileViewModel);
        result.add("profile", profileElement);
        result.addProperty("success", 1);

        return Response.status(200).entity(result.toString()).build();
    }


    // TODO: move to demands as POST action
    @POST
    @Path("/persons/{instanceId}/actions/createPersonDemand")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response createPersonsDemandServices(@PathParam("instanceId") Integer instanceId, InputStream object) {

        Person chosenPerson = api.findPersonById(instanceId);
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

            String id6 = "imageUrl";
            String imageUrl = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id6, new Object[0]);
                imageUrl = property.getString("");
            } catch (Exception e) {
                //ignore
            }

            Demand demand = api.createPersonDemand(chosenPerson, description, summary, story, startDate, endDate, imageUrl);

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

    /************************** apidoc *************************************/

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

    /************************** Helpers *************************************/

    /**
     *
     * @param instanceId
     * @return  A JsonObject containing a person representation with sideloaded collections
     *
     * NOTE: In order to preserve or guard business logic all data should be retrieved via Api
     */
    private JsonObject createPersonResult(final Integer instanceId) {

        JsonObject result = new JsonObject();

        // person
        Person chosenPerson = api.findPersonById(instanceId);
        if (chosenPerson == null) {
            return null;
        }
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

        //sideload assessments
        result.add("assessments", sideLoadAssessments(chosenPerson));

        //sideload assessments
        result.add("communicationChannels", sideLoadCommunicationChannels(chosenPerson));

        //sideload profileMatches
        result.add("profileMatches", sideLoadProfileMatches(chosenPerson));

        //add success
        result.addProperty("success", 1);

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
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile));
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile));
            }
        }
        return gson.toJsonTree(profileViewModels);
    }

    private JsonElement sideLoadProfileElements(final Person person){

        // collect the elements
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    profileElementList.add(element);
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    profileElementList.add(element);
                }
            }
        }

        return GenerateJsonElementService.generateProfileElements(profileElementList);
    }

    private JsonElement sideLoadTagHolders(final Person person){

        // collect the Tag elements
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getCollectProfileElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
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

    private JsonElement sideLoadAssessments(final Person person){
        //collect assessments
        List<Assessment> assessments = api.getAllAssessments(person);
        List<AssessmentViewModel> assessmentViewModels = new ArrayList<>();
        for (Assessment assessment : assessments){
            if (assessment.getClass().equals(DemandFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((DemandFeedback)assessment));
            }
            if (assessment.getClass().equals(SupplyFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((SupplyFeedback)assessment));
            }
            if (assessment.getClass().equals(ProfileFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((ProfileFeedback)assessment));
            }
            if (assessment.getClass().equals(ProfileMatchAssessment.class)) {
                assessmentViewModels.add(new AssessmentViewModel((ProfileMatchAssessment) assessment));
            }
        }
        return gson.toJsonTree(assessmentViewModels);
    }

    private JsonElement sideLoadCommunicationChannels(final Person person){
        List<CommunicationChannelViewModel> communicationChannelViewModels = new ArrayList<>();
        for (CommunicationChannel communicationChannel : api.getCommunicationchannelsForPerson(person)){
            if (communicationChannel.getClass().equals(Email.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Email) communicationChannel));
            }
            if (communicationChannel.getClass().equals(Address.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Address) communicationChannel));
            }
            if (communicationChannel.getClass().equals(Phone.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Phone) communicationChannel));
            }
        }
        return gson.toJsonTree(communicationChannelViewModels);
    }

    private JsonElement sideLoadProfileMatches(final Person person){
        List<ProfileMatchViewModel> profileMatchViewModels = new ArrayList<>();
        for (ProfileMatch match : api.getProfileMatchesForPerson(person)){
            profileMatchViewModels.add(new ProfileMatchViewModel(match));
        }
        return gson.toJsonTree(profileMatchViewModels);
    }

}
