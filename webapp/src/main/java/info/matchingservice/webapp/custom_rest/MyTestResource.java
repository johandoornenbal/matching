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

import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.isis.applib.services.userreg.UserDetails;
import org.apache.isis.applib.services.userreg.UserRegistrationService;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.DomainServiceResource;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import org.isisaddons.module.security.dom.user.ApplicationUsers;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.AppUserRegistrationService;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/register")
public class MyTestResource extends ResourceAbstract implements DomainServiceResource {

    @Override
    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response services() {
        return Response.status(200).entity("{\"someJson\" : \"Hello\"}").build();
    }


    @GET
    @Path("/test/{logon}")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response object(@PathParam("logon") String logon) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(logon);
        userDetails.setPassword(logon);
        userDetails.setConfirmPassword(logon);
        userDetails.setEmailAddress(logon + "@example.com");
        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
        if (persons.findPersonUnique(logon)!= null){
            return Response.status(200).entity("user: " + persons.findPersonUnique(logon).getOwnedBy() + " already registered and cannot be registered again").build();
        }
        final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
        if (applicationUsers.findUserByUsername(logon)!=null) {
            return Response.status(200).entity("user: " + applicationUsers.findUserByUsername(logon).getUsername() + " already registered but has no Person record yet.").build();
        }
        final UserRegistrationService appUserRegistrationService =
                IsisContext.getPersistenceSession().getServicesInjector().lookupService(AppUserRegistrationService.class);
        appUserRegistrationService.registerUser(userDetails);
        return Response.status(200).entity("user: "  + logon + " registered").build();
    }


    @Override public Response deleteServicesNotAllowed() {
        return null;
    }

    @Override public Response putServicesNotAllowed() {
        return null;
    }

    @Override public Response postServicesNotAllowed() {
        return null;
    }

    @Override public Response service(final String s) {
        return null;
    }

    @Override public Response deleteServiceNotAllowed(final String s) {
        return null;
    }

    @Override public Response putServiceNotAllowed(final String s) {
        return null;
    }

    @Override public Response postServiceNotAllowed(final String s) {
        return null;
    }

    @Override public Response actionPrompt(final String s, final String s1) {
        return null;
    }

    @Override public Response deleteActionPromptNotAllowed(final String s, final String s1) {
        return null;
    }

    @Override public Response putActionPromptNotAllowed(final String s, final String s1) {
        return null;
    }

    @Override public Response postActionPromptNotAllowed(final String s, final String s1) {
        return null;
    }

    @Override public Response invokeActionQueryOnly(final String s, final String s1, final String s2) {
        return null;
    }

    @Override public Response invokeActionIdempotent(final String s, final String s1, final InputStream inputStream) {
        return null;
    }

    @Override public Response invokeAction(final String s, final String s1, final InputStream inputStream) {
        return null;
    }

    @Override public Response deleteInvokeActionNotAllowed(final String s, final String s1) {
        return null;
    }
}
