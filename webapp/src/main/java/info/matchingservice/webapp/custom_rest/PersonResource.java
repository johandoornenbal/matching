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
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.Assessment.ProfileFeedback;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Match.ProfileMatches;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v1")
public class PersonResource extends ResourceAbstract {


    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getActivePersonServices()  {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Person activePerson = persons.activePerson().get(0);

        return Response.status(200).entity(personRepresentation(activePerson).toString()).build();
    }

    @GET
    @Path("/people/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getPeopleServices(@PathParam("instanceId") String instanceId)  {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Person activePerson = persons.matchPersonApiId(instanceId);

        return Response.status(200).entity(personRepresentation(activePerson).toString()).build();
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

    @PUT
    @Path("/people/{instanceId}")
    public Response putPeopleNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the people resource is not allowed.", new Object[0]);
    }

    private JsonRepresentation personRepresentation(Person activePerson){

        JsonRepresentation all = JsonRepresentation.newMap();

        // activeperson
        JsonRepresentation activeperson = JsonRepresentation.newMap();
        activeperson.mapPut("id", Utils.toApiID(activePerson.getOID()));
        activeperson.mapPut("URI", Utils.toObjectURI(activePerson.getOID()));
        activeperson.mapPut("firstName", activePerson.getFirstName());
        activeperson.mapPut("lastName", activePerson.getLastName());
        activeperson.mapPut("middleName", activePerson.getMiddleName());
        activeperson.mapPut("birthDay", activePerson.getDateOfBirth().toString());
        activeperson.mapPut("pictureLink", activePerson.getPictureLink());
        activeperson.mapPut("roles", activePerson.getRoles());
        try {
            Email email = (Email) communicationChannels.findCommunicationChannelByPersonAndType(activePerson, CommunicationChannelType.EMAIL_MAIN).get(0);
            activeperson.mapPut("email", email.getEmail());
        } catch (Exception e) {
            activeperson.mapPut("email", "");
        }
        try {
            Address address = (Address) communicationChannels.findCommunicationChannelByPersonAndType(activePerson, CommunicationChannelType.ADDRESS_MAIN).get(0);
            activeperson.mapPut("address", address.getAddress());
            activeperson.mapPut("postalCode", address.getPostalCode());
            activeperson.mapPut("town", address.getTown());
        } catch (Exception e) {
            activeperson.mapPut("address", "");
            activeperson.mapPut("postalCode", "");
            activeperson.mapPut("town", "");
        }
        try {
            Phone phone = (Phone) communicationChannels.findCommunicationChannelByPersonAndType(activePerson, CommunicationChannelType.PHONE_MAIN).get(0);
            activeperson.mapPut("phone", phone.getPhoneNumber());
        } catch (Exception e) {
            activeperson.mapPut("phone", "");
        }


        // demands
        JsonRepresentation demandsAndProfilesAndElements = JsonRepresentation.newArray();
        for (Demand demand : activePerson.getCollectDemands()) {

            JsonRepresentation demandAndProfilesAndElementsMap = JsonRepresentation.newMap();
            JsonRepresentation profileAndElements = JsonRepresentation.newArray();

            for (Profile profile : demand.getCollectDemandProfiles()) {

                JsonRepresentation profileElements = JsonRepresentation.newArray();

                for (ProfileElement element : profile.getCollectProfileElements()) {
                    ProfileElementRepresentation rep = new ProfileElementRepresentation();
                    profileElements.arrayAdd(rep.ObjectRepresentation(element));
                }

                JsonRepresentation profileAndElementMap = JsonRepresentation.newMap();
                profileAndElementMap.mapPut("id", Utils.toApiID(profile.getOID()));
                profileAndElementMap.mapPut("URI", Utils.toObjectURI(profile.getOID()));
                profileAndElementMap.mapPut("description", profile.getProfileName());
                profileAndElementMap.mapPut("profileElements", profileElements);
                profileAndElements.arrayAdd(profileAndElementMap);

            }

            demandAndProfilesAndElementsMap.mapPut("id", Utils.toApiID(demand.getOID()));
            demandAndProfilesAndElementsMap.mapPut("URI", Utils.toObjectURI(demand.getOID()));
            demandAndProfilesAndElementsMap.mapPut("description", demand.getDemandDescription());
            demandAndProfilesAndElementsMap.mapPut("profiles", profileAndElements);
            demandsAndProfilesAndElements.arrayAdd(demandAndProfilesAndElementsMap);
        }
        activeperson.mapPut("demands", demandsAndProfilesAndElements);

        //supplies
        JsonRepresentation suppliesAndProfilesAndElements = JsonRepresentation.newArray();
        for (Supply supply : activePerson.getCollectSupplies()) {

            JsonRepresentation supplyAndProfilesAndElementsMap = JsonRepresentation.newMap();
            JsonRepresentation profileAndElements = JsonRepresentation.newArray();

            for (Profile profile : supply.getCollectSupplyProfiles()) {

                JsonRepresentation profileElements = JsonRepresentation.newArray();


                for (ProfileElement element : profile.getCollectProfileElements()) {
                    ProfileElementRepresentation rep = new ProfileElementRepresentation();
                    profileElements.arrayAdd(rep.ObjectRepresentation(element));
                }

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
                profileAndElementMap.mapPut("profileElements", profileElements);
                profileAndElements.arrayAdd(profileAndElementMap);

            }

            supplyAndProfilesAndElementsMap.mapPut("id", Utils.toApiID(supply.getOID()));
            supplyAndProfilesAndElementsMap.mapPut("URI", Utils.toObjectURI(supply.getOID()));
            supplyAndProfilesAndElementsMap.mapPut("description", supply.getSupplyDescription());
            supplyAndProfilesAndElementsMap.mapPut("profiles", profileAndElements);
            suppliesAndProfilesAndElements.arrayAdd(supplyAndProfilesAndElementsMap);
        }
        activeperson.mapPut("supplies", suppliesAndProfilesAndElements);

        //personal contacts
        JsonRepresentation personalContactsArray = JsonRepresentation.newArray();
        for (PersonalContact contact : activePerson.getCollectPersonalContacts()) {

            JsonRepresentation personalContactMap = JsonRepresentation.newMap();
            personalContactMap.mapPut("id", Utils.toApiID(contact.getOID()));
            personalContactMap.mapPut("URI", Utils.toObjectURI(contact.getOID()));
            personalContactMap.mapPut("contactId", Utils.toApiID(contact.getContactPerson().getOID()));
            personalContactMap.mapPut("contactURI", Utils.toObjectURI(contact.getContactPerson().getOID()));
            personalContactMap.mapPut("contactName", contact.getContactPerson().title());
            personalContactMap.mapPut("contactRoles", contact.getContactPerson().getRoles());
            personalContactMap.mapPut("trustlevel",contact.getTrustLevel().toString());
            personalContactsArray.arrayAdd(personalContactMap);

        }
        activeperson.mapPut("personalContacts", personalContactsArray);

        //assessments
        JsonRepresentation assessmentsArray = JsonRepresentation.newArray();
        for (Assessment assessment : activePerson.getCollectAssessmentsReceivedByActor()) {
            JsonRepresentation assessmentMap = JsonRepresentation.newMap();
            assessmentMap.mapPut("description" , assessment.getAssessmentDescription());
            try {
                ProfileFeedback feedback = (ProfileFeedback) assessment;
                assessmentMap.mapPut("feedback" , feedback.getFeedback());
            } catch (Exception e) {
                assessmentMap.mapPut("feedback" , "");
            }
            Person assessmentOwner = (Person) assessment.getAssessmentOwnerActor();
            assessmentMap.mapPut("assessmentOwnerId", Utils.toApiID(assessmentOwner.getOID()));
            assessmentMap.mapPut("assessmentOwnerURI", Utils.toObjectURI(assessmentOwner.getOID()));
            assessmentMap.mapPut("assessmentOwnerFullName", assessmentOwner.title());
            assessmentMap.mapPut("assessmentOwnerRoles", assessmentOwner.getRoles());
            assessmentsArray.arrayAdd(assessmentMap);
        }
        activeperson.mapPut("assessments", assessmentsArray);

        //profileMatches
        JsonRepresentation profileMatchesArray = JsonRepresentation.newArray();
        for (ProfileMatch match : profileMatches.collectProfileMatches(activePerson)) {
            JsonRepresentation profileMatch = JsonRepresentation.newMap();
            profileMatch.mapPut("id", Utils.toApiID(match.getOID()));
            profileMatch.mapPut("URI", Utils.toObjectURI(match.getOID()));
            profileMatch.mapPut("title", match.title());
            profileMatch.mapPut("demandProfileId", Utils.toApiID(match.getDemandProfile().getOID()));
            profileMatch.mapPut("demandProfileURI", Utils.toObjectURI(match.getDemandProfile().getOID()));
            profileMatch.mapPut("candidateStatus", match.getCandidateStatus().toString());
            profileMatchesArray.arrayAdd(profileMatch);
        }
        activeperson.mapPut("profileMatches", profileMatchesArray);

        all.mapPut("person",activeperson);

        return all;

    }
    private CommunicationChannels communicationChannels = IsisContext.getPersistenceSession().getServicesInjector().lookupService(CommunicationChannels.class);
    private ProfileMatches profileMatches = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileMatches.class);
}
