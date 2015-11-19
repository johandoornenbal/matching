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
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.*;
import info.matchingservice.dom.Assessment.*;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
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
import java.util.regex.Pattern;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2")
public class LoginResource extends ResourceAbstract {

    private Gson gson = new Gson();
    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

    private static Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static Pattern PASSWORD_REGEX = Pattern.compile("^([^\\s]+)");
//    private static Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");

    @POST
    @PUT
    @GET
    @Path("/action/login")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response loginServices(InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            Person activePerson = api.activePerson();

            JsonObject result = new JsonObject();


            JsonObject application = new JsonObject();
            application.addProperty("activePerson", activePerson.getIdAsInt());
            application.addProperty("success", 1);

            result.add("application", application);
//            result.add("persons", createPersonResult(activePerson.getIdAsInt()));




            return Response.status(200).entity(result.toString()).build();

        }
    }

    public JsonObject createPersonResult(final Integer instanceId) {

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
                for (ProfileElement element : profile.getElements()){
                    profileElementList.add(element);
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
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
                for (ProfileElement element : profile.getElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
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
