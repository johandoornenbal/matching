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

import info.matchingservice.dom.Utils.Utils;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Demands;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v1")
public class FindResource extends ResourceAbstract {

    @GET
    @Path("/find/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getFindServices(@PathParam("instanceId") String search)  {

        return Response.status(200).entity(findRepresentation(search).toString()).build();
    }

    @GET
    @Path("/find/")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getEmptyService()  {

        return Response.status(200).build();
    }


    @DELETE
    @Path("/find/{instanceId}")
    public Response deleteSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the supplies resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/find/{instanceId}")
    public Response postSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the supplies resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/find/{instanceId}")
    public Response putSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the supplies resource is not allowed.", new Object[0]);
    }

    private JsonRepresentation findRepresentation(String search){

        JsonRepresentation all = JsonRepresentation.newMap();
        JsonRepresentation resultOfType = JsonRepresentation.newMap();

        //findPersons
        JsonRepresentation personArray = JsonRepresentation.newArray();

        for ( Person person : persons.findPersonsByName(search)) {

            JsonRepresentation personMap = JsonRepresentation.newMap();

            personMap.mapPut("id", Utils.toApiID(person.getOID()));
            personMap.mapPut("URI", Utils.toObjectURI(person.getOID()));
            personMap.mapPut("fullName", person.title());
            personMap.mapPut("pictureLink", person.getImageUrl());
            personArray.arrayAdd(personMap);
        }

        resultOfType.mapPut("Person",personArray);

        //findDemands
        JsonRepresentation demandArray = JsonRepresentation.newArray();

        for (Demand demand : demands.findDemandByDescription(search)) {

            JsonRepresentation demandMap = JsonRepresentation.newMap();

            demandMap.mapPut("id", Utils.toApiID(demand.getOID()));
            demandMap.mapPut("URI", Utils.toObjectURI(demand.getOID()));
            demandMap.mapPut("description", demand.getDescription());
            demandArray.arrayAdd(demandMap);
        }

        resultOfType.mapPut("Demand",demandArray);

        all.mapPut("results",resultOfType);

        return all;



    }

    final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
    final Demands demands = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Demands.class);
}
