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

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Match.ProfileMatches;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        Person activePerson = persons.activePerson();

        return Response.status(200).entity(personRepresentation(activePerson).toString()).build();
    }

    @GET
    @Path("/people/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getPeopleServices(@PathParam("instanceId") Integer instanceId)  {

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
        activeperson.mapPut("pictureLink", activePerson.getImageUrl());
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
        for (Demand demand : activePerson.getDemands()) {
            DemandRepresentation rep = new DemandRepresentation();
            demandsAndProfilesAndElements.arrayAdd(rep.ObjectRepresentation(demand));
        }
        activeperson.mapPut("demands", demandsAndProfilesAndElements);

        //supplies
        JsonRepresentation suppliesAndProfilesAndElements = JsonRepresentation.newArray();
        for (Supply supply : activePerson.getSupplies()) {
            SupplyRepresentation rep = new SupplyRepresentation();
            suppliesAndProfilesAndElements.arrayAdd(rep.ObjectRepresentation(supply));
        }
        activeperson.mapPut("supplies", suppliesAndProfilesAndElements);

        //personal contacts
        JsonRepresentation personalContactsArray = JsonRepresentation.newArray();
        for (PersonalContact contact : activePerson.getPersonalContacts()) {

            JsonRepresentation personalContactMap = JsonRepresentation.newMap();
            personalContactMap.mapPut("id", Utils.toApiID(contact.getOID()));
            personalContactMap.mapPut("URI", Utils.toObjectURI(contact.getOID()));
            personalContactMap.mapPut("contactId", Utils.toApiID(contact.getContactPerson().getOID()));
            personalContactMap.mapPut("contactURI", Utils.toObjectURI(contact.getContactPerson().getOID()));
            personalContactMap.mapPut("contactName", contact.getContactPerson().title());
            personalContactMap.mapPut("contactRoles", contact.getContactPerson().getRoles());
            personalContactMap.mapPut("contactPictureLink", contact.getContactPerson().getImageUrl());
            personalContactMap.mapPut("trustlevel",contact.getTrustLevel().toString());
            personalContactsArray.arrayAdd(personalContactMap);

        }
        activeperson.mapPut("personalContacts", personalContactsArray);

        //assessments received by active person
        JsonRepresentation assessmentsReceivedArray = JsonRepresentation.newArray();
        for (Assessment assessment : activePerson.getAssessmentsReceived()) {
            AssessmentRepresentation rep = new AssessmentRepresentation();
            assessmentsReceivedArray.arrayAdd(rep.ObjectRepresentation(assessment));
        }
        activeperson.mapPut("assessmentsReceived", assessmentsReceivedArray);

        //assessments given by active person
        JsonRepresentation assessmentsGivenArray = JsonRepresentation.newArray();
        for (Assessment assessment : activePerson.getAssessmentsGiven()) {
            AssessmentRepresentation rep = new AssessmentRepresentation();
            assessmentsGivenArray.arrayAdd(rep.ObjectRepresentation(assessment));
        }
        activeperson.mapPut("assessmentsGiven", assessmentsGivenArray);

        //ProfileMatches
        JsonRepresentation profileMatchesArray = JsonRepresentation.newArray();
        for (ProfileMatch match : profileMatches.collectProfileMatches(activePerson)) {
            ProfileMatchRepresentation rep = new ProfileMatchRepresentation();
            profileMatchesArray.arrayAdd(rep.ObjectRepresentation(match));
        }
        activeperson.mapPut("profileMatches", profileMatchesArray);

        all.mapPut("person",activeperson);

        return all;

    }
    private CommunicationChannels communicationChannels = IsisContext.getPersistenceSession().getServicesInjector().lookupService(CommunicationChannels.class);
    private ProfileMatches profileMatches = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileMatches.class);
}
