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

package info.matchingservice.webapp.custom_rest.v2;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.webapp.custom_rest.ErrorMessages;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;
import org.isisaddons.module.security.dom.password.PasswordEncryptionService;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2/actions/login")
public class UserAuthentificationResource extends ResourceAbstract {

    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
//    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(info.matchingservice.dom.Api.Api.class);

    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");




    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(InputStream inputStream) {


        String objectStr = Util.asStringUtf8(inputStream);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if (!argRepr.isMap()) {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});
        }

        TreeMap<String, String> errors = new TreeMap<>();
        String email = "", password = "";

        System.out.println(argRepr);

        // GET PARAMETERS
        try {
            email = getParameterValue("email", argRepr).toLowerCase();
        } catch (Exception e) {
            errors.put("email", "required'");
        }
        try {
            password = getParameterValue("password", argRepr);
        } catch (Exception e) {
            errors.put("password", "required'");
        }

        if (errors.size() > 0) {
            return ErrorMessages.getError400Response(errors);
        }


        // email regex
//        Matcher matcher = EMAIL_REGEX.matcher(email);
//        if (!matcher.matches()) {
//            errors.put("email", "invalid:chars");
//        }
//
//        if (errors.size() > 0) {
//            return ErrorMessages.getError400Response(errors);
//        }

        final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
        ApplicationUser applicationUser = applicationUsers.findUserByUsername(email);
        if (applicationUser == null) {
            errors.put("username", "not found");
            return ErrorMessages.getError400Response(errors);

        }
        PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionServiceUsingJBcrypt() {
        };

        if (!passwordEncryptionService.matches(password, applicationUser.getEncryptedPassword())) {
            errors.put("message", "not:match.credentials");
            return ErrorMessages.getError400Response(errors);
        }


        Person person = api.findPersonUnique(email);
        if (person == null) {

            //TODO person doesnt exist ??
            errors.put("server", "cant find person in api");
            return ErrorMessages.getError400Response(errors);
        }

        if (!person.getActivated()) {

            //user not activated yet
            errors.put("message", "not:active");
            return ErrorMessages.getError400Response(errors);
        }
        //LOGIN is ok. build response


        int id = person.getIdAsInt();





        JsonObject result = new JsonObject();


        JsonObject app = new JsonObject();
        app.addProperty("id", 0);
        app.addProperty("success", 1);
        app.addProperty("personId", person.getIdAsInt());
        app.add("errors", new Gson().toJsonTree(errors));

        result.add("application", app);


        System.out.println(result.toString());

        return Response.status(200).entity(result.toString()).build();


    }


    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the services resource is not allowed.", new Object[0]);
    }



    /**
     * returns the parameter for the given name
     * if the parameter is not present throws an exception
     *
     * @param parameterName
     * @param argumentMap
     * @return
     */
    @Programmatic
    private String getParameterValue(final String parameterName, JsonRepresentation argumentMap) throws Exception {
        assert parameterName != null;
        assert argumentMap != null;
        String value;
        try {
            JsonRepresentation property = argumentMap.getRepresentation(parameterName);
            value = property.getString("");
        } catch (Exception e) {
            throw new Exception("cant get value with value name");
        }
        return value;
    }

}
