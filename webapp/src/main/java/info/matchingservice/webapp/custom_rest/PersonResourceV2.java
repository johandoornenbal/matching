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
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.ActivePersonViewModel;
import info.matchingservice.dom.Api.Viewmodels.DemandViewModel;
import info.matchingservice.dom.Api.Viewmodels.PersonViewModel;
import info.matchingservice.dom.Api.Viewmodels.SupplyViewModel;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatches;
import org.apache.isis.applib.Identifier;
import org.apache.isis.core.runtime.system.context.IsisContext;
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
public class PersonResourceV2 extends ResourceAbstract {


    private CommunicationChannels communicationChannels = IsisContext.getPersistenceSession().getServicesInjector().lookupService(CommunicationChannels.class);
    private Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
    private ProfileMatches profileMatches = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ProfileMatches.class);

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getActivePersonServices() {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Person activePerson = persons.activePerson();
        String apiNotes ="This is a deepnested presentation of activePerson (Object person connected to currentUser). Property 'profile' is added for convenience in the root. It is the active person's Supply of type PERSON_DEMANDSUPPLY which should be unique.";

        ActivePersonViewModel activePersonViewModel = new ActivePersonViewModel(activePerson, communicationChannels, apiNotes);

        Gson gson = new Gson();
        JsonElement personRepresentation = gson.toJsonTree(activePersonViewModel);
        JsonObject result = new JsonObject();
        result.add("person", personRepresentation);

        return Response.status(200).entity(result.toString()).build();
    }

    @GET
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getPersonServices(@PathParam("instanceId") Integer instanceId) {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        // person
        Person chosenPerson = persons.matchPersonApiId(instanceId);
        PersonViewModel personViewModel = new PersonViewModel(chosenPerson, communicationChannels);
        JsonElement personRepresentation = gson.toJsonTree(personViewModel);
        result.add("person", personRepresentation);

        // sideload supplies with implementation of trusted circles
        //TODO: centralize implementation trusted circles
        if(!chosenPerson.hideSupplies()) {
            List<SupplyViewModel> supplyViewmodels = new ArrayList<>();
            for (Supply supply : chosenPerson.getSupplies()) {
                supplyViewmodels.add(new SupplyViewModel(supply));
            }
            JsonElement suppliesRepresentation = gson.toJsonTree(supplyViewmodels);
            result.add("supplies", suppliesRepresentation);
        }

        // sideload demands with implementation of trusted circles
        //TODO: centralize implementation trusted circles
        if(!chosenPerson.hideDemands()) {
            List<DemandViewModel> demandViewmodels = new ArrayList<>();
            for (Demand demand : chosenPerson.getDemands()) {
                demandViewmodels.add(new DemandViewModel(demand));
            }
            JsonElement demandsRepresentation = gson.toJsonTree(demandViewmodels);
            result.add("demands", demandsRepresentation);
        }

        return Response.status(200).entity(result.toString()).build();
    }

    @PUT
    @Path("/persons/{instanceId}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response putPersonServices(@PathParam("instanceId") Integer instanceId) {


        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
        final Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
        Person chosenPerson = persons.matchPersonApiId(instanceId);

        // apply business logic
        if (chosenPerson.disabled(Identifier.Type.ACTION)!=null){
            String disabledMsg = chosenPerson.disabled(Identifier.Type.ACTION);
            String error = "{\"success\" : 0 , \"error\" : \"";
            error = error.concat(disabledMsg);
            error = error.concat("\"}");
            return Response.status(401).entity(error).build();
        }

        chosenPerson = api.updatePerson(chosenPerson,"firstName", "middleName", "lastName", "2000-12-31", "pictureLink");

        Gson gson = new Gson();
        JsonObject result = new JsonObject();

        PersonViewModel personViewModel = new PersonViewModel(chosenPerson, communicationChannels);
        JsonElement personRepresentation = gson.toJsonTree(personViewModel);
        result.add("person", personRepresentation);

        // sideload supplies with implementation of trusted circles
        //TODO: centralize implementation trusted circles
        if(!chosenPerson.hideSupplies()) {
            List<SupplyViewModel> supplyViewmodels = new ArrayList<>();
            for (Supply supply : chosenPerson.getSupplies()) {
                supplyViewmodels.add(new SupplyViewModel(supply));
            }
            JsonElement suppliesRepresentation = gson.toJsonTree(supplyViewmodels);
            result.add("supplies", suppliesRepresentation);
        }

        // sideload demands with implementation of trusted circles
        //TODO: centralize implementation trusted circles
        if(!chosenPerson.hideDemands()) {
            List<DemandViewModel> demandViewmodels = new ArrayList<>();
            for (Demand demand : chosenPerson.getDemands()) {
                demandViewmodels.add(new DemandViewModel(demand));
            }
            JsonElement demandsRepresentation = gson.toJsonTree(demandViewmodels);
            result.add("demands", demandsRepresentation);
        }

        return Response.status(200).entity(result.toString()).build();
    }

    @GET
    @Path("/persons")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR})
    public Response getAllPersonsServices() {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);

        Gson gson = new Gson();

        // persons
        List<PersonViewModel> personViewModels = new ArrayList<>();
        for (Person person : persons.allPersons()) {
            PersonViewModel personViewModel = new PersonViewModel(person, communicationChannels);
            personViewModels.add(personViewModel);
        }
        JsonElement personRepresentation = gson.toJsonTree(personViewModels);

        // build result
        JsonObject result = new JsonObject();
        result.add("persons", personRepresentation);

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

}
