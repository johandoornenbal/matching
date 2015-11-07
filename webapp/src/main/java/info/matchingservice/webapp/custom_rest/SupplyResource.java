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

import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
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
public class SupplyResource extends ResourceAbstract {

    @GET
    @Path("/supplies/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getSupplyServices(@PathParam("instanceId") Integer instanceId)  {

        final Supplies supplies = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Supplies.class);

        Supply activeSupply = supplies.matchSupplyApiId(instanceId);

        return Response.status(200).entity(supplyRepresentation(activeSupply).toString()).build();
    }


    @DELETE
    @Path("/supplies/{instanceId}")
    public Response deleteSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the supplies resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/supplies/{instanceId}")
    public Response postSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the supplies resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/supplies/{instanceId}")
    public Response putSuppliesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the supplies resource is not allowed.", new Object[0]);
    }

    private JsonRepresentation supplyRepresentation(Supply activeSupply){

        JsonRepresentation all = JsonRepresentation.newMap();
        SupplyRepresentation rep = new SupplyRepresentation();
        all.mapPut("supply",rep.ObjectRepresentation(activeSupply));

        return all;

    }

}
