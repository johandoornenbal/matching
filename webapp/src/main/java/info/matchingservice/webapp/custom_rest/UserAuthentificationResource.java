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

import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;
import org.isisaddons.module.security.dom.password.PasswordEncryptionService;
import org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2/actions/login")
public class UserAuthentificationResource extends ResourceAbstract {

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response object(InputStream object) {
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});
        } else {

            String id = "username";
            JsonRepresentation propertyUsername = argRepr.getRepresentation(id, new Object[0]);
            String userName = propertyUsername.getString("");

            String id2 = "password";
            JsonRepresentation propertyPassword = argRepr.getRepresentation(id2, new Object[0]);
            String passWord = propertyPassword.getString("");

            //input validation

            boolean error = false;
            String errorString = new String();
            errorString = "";

            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
            if (applicationUsers.findUserByUsername(userName) == null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"error\" : \"USERNAME NOT FOUND\"");
                error = true;
            }

            if (applicationUsers.findUserByUsername(userName) != null) {
                PasswordEncryptionService passwordEncryptionService = new PasswordEncryptionServiceUsingJBcrypt() {
                };
                if (!passwordEncryptionService.matches(passWord, applicationUsers.findUserByUsername(userName).getEncryptedPassword())) {
                    errorString = errorString.concat("\"error\" : \"WRONG USERNAME PASSWORD COMBINATION\"");
                    error = true;
                }
            }

            if (error) {
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"errors\" : {");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}}");
                return Response.status(200).entity(responseMessage).build();
            } else {
                return Response.status(200).entity("{\"success\" : 1}").build();
            }



        }
    }


    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the services resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/")
    public Response postServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the services resource is not allowed.", new Object[0]);
    }

    @GET
    @Path("/")
    public Response getServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the services resource is not allowed.", new Object[0]);
    }

}
