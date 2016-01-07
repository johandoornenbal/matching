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
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.PersonViewModel;
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
    private List<String> errors = new ArrayList<>();

    private static Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static Pattern PASSWORD_REGEX = Pattern.compile("^([^\\s]+)");
//    private static Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");

    @POST
    @PUT
    @GET
    @Path("/actions/login")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response loginServices(InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            Person activePerson = api.activePerson();
            if (activePerson == null){
                errors.add("person not found. Most likely cause: for this login no Person object created.");
                return ErrorMessages.getError400Response(errors);
            }

            JsonObject result = new JsonObject();

            JsonObject application = new JsonObject();
            application.addProperty("activePerson", activePerson.getIdAsInt());
            application.addProperty("username", activePerson.getOwnedBy());
            application.addProperty("success", 1);
            application.addProperty("aplicationId", 0);
            List<Integer> personsArray = new ArrayList<>();
            personsArray.add(activePerson.getIdAsInt());
            application.add("persons", gson.toJsonTree(personsArray));

            result.add("application", application);

            List<PersonViewModel> personViewModels = new ArrayList<>();
            PersonViewModel activePersonViewmodel =  new PersonViewModel(activePerson, api);
            personViewModels.add(activePersonViewmodel);
            JsonElement personRepresentation = gson.toJsonTree(personViewModels);
            result.add("persons", personRepresentation);

            // sideloads
            result.add("supplies", Sideloads.supplies(activePerson, api, gson));
            result.add("demands", Sideloads.demands(activePerson, api, gson));
            result.add("profiles", Sideloads.profiles(activePerson, api, gson));
            result.add("elements", Sideloads.profileElements(activePerson, api, gson));
            result.add("tagHolders", Sideloads.tagHolders(activePerson, api, gson));
            result.add("personalContacts", Sideloads.PersonalContacts(activePerson, api, gson));
            result.add("assessments", Sideloads.assessments(activePerson, api, gson));
            result.add("communicationChannels", Sideloads.communicationChannels(activePerson, api, gson));
            result.add("profileMatches", Sideloads.profileMatches(activePerson, api, gson));

            return Response.status(200).entity(result.toString()).build();

        }
    }

}
