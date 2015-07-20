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

import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v1")
public class ProfileResource extends ResourceAbstract {

    @GET
    @Path("/demandprofiles/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getProfileServices(@PathParam("instanceId") String instanceId)  {

        final Profiles profiles = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Profiles.class);

        Profile activeProfile = profiles.matchProfileApiId(instanceId);

        return Response.status(200).entity(profileRepresentation(activeProfile).toString()).build();
    }


    @DELETE
    @Path("/demandprofiles/{instanceId}")
    public Response deleteProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the demandprofiles resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/demandprofiles/{instanceId}")
    public Response postProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the demandprofiles resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/demandprofiles/{instanceId}")
    public Response putProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the demandprofiles resource is not allowed.", new Object[0]);
    }


    @GET
    @Path("/supplyprofiles/{instanceId}")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getSupplyProfileServices(@PathParam("instanceId") String instanceId)  {

        final Profiles profiles = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Profiles.class);

        Profile activeProfile = profiles.matchProfileApiId(instanceId);

        return Response.status(200).entity(profileRepresentation(activeProfile).toString()).build();
    }


    @DELETE
    @Path("/supplyprofiles/{instanceId}")
    public Response deleteSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the supplyprofiles resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/supplyprofiles/{instanceId}")
    public Response postSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the supplyprofiles resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/supplyprofiles/{instanceId}")
    public Response putSupplyProfilesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the supplyprofiles resource is not allowed.", new Object[0]);
    }


    private JsonRepresentation profileRepresentation(Profile activeProfile){

        JsonRepresentation all = JsonRepresentation.newMap();

        ProfileRepresentation rep = new ProfileRepresentation();

        if (activeProfile.getDemandOrSupply() == DemandOrSupply.DEMAND) {
            all.mapPut("demandprofile", rep.ObjectRepresentation(activeProfile));
        } else {
            all.mapPut("supplyprofile", rep.ObjectRepresentation(activeProfile));
        }

        return all;

    }

}
