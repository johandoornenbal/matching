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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.TreeMap;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2/actions/activeperson")
public class ActivePersonResource extends ResourceAbstract {

    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
//    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(info.matchingservice.dom.Api.Api.class);

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response loginServices(InputStream object) {

        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        }
        Person activePerson = api.activePerson();
        JsonObject result = new JsonObject();

        if(activePerson ==null){
            result.addProperty("success", 0);
            return Response.status(200).entity(result.toString()).build();

        }
        result.addProperty("id", activePerson.getIdAsInt());
        result.addProperty("success", 1);
        return Response.status(200).entity(result.toString()).build();


    }




    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the services resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/")
    public Response createServicesNotAllowed() {
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
