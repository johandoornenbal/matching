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

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.AppUserRegistrationService;
import info.matchingservice.dom.Howdoido.Api;
import info.matchingservice.dom.IsisPropertiesLookUpService;
import info.matchingservice.dom.TestFacebookObjects.FbTokens;
import info.matchingservice.dom.TestLinkedInObjects.LinkedInTokens;
import org.apache.isis.applib.services.userreg.UserDetails;
import org.apache.isis.applib.services.userreg.UserRegistrationService;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2/action/")
public class UserRegistrationResource extends ResourceAbstract {
    
    private static Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static Pattern PASSWORD_REGEX = Pattern.compile("^([^\\s]+)");
//    private static Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response object(InputStream object) {
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {


            List<String> errors = new ArrayList<>();

            String id1 = "email";
            String email;
            try {
                JsonRepresentation propertyEmail = argRepr.getRepresentation(id1, new Object[0]);
                email = propertyEmail.getString("");
            } catch (Exception e) {
                email=null;
                errors.add("property 'email' is mandatory");
            }

            String id2 = "password";
            String password;
            try {
                JsonRepresentation propertyPassword = argRepr.getRepresentation(id2, new Object[0]);
                password = propertyPassword.getString("");
            } catch (Exception e) {
                password=null;
                errors.add("property 'password' is mandatory");
            }

            String id3 = "passwordConfirm";
            String passwordConfirm;
            try {
                JsonRepresentation propertyPasswordConfirm = argRepr.getRepresentation(id3, new Object[0]);
                passwordConfirm = propertyPasswordConfirm.getString("");
            } catch (Exception e) {
                passwordConfirm=null;
                errors.add("property 'passwordConfirm' is mandatory");
            }

            //passwords should match
            if (!password.equals(passwordConfirm)) {
                errors.add(" passwords not matching ");
            }

            //password should be conform REGEX
            Matcher matcher = PASSWORD_REGEX.matcher(password);
            if (!matcher.matches()) {
                errors.add(" password : NO_BLANKS_IN_PASSWORD ");
            }

            //email should be conform REGEX
            matcher = EMAIL_REGEX.matcher(email);
            if (!matcher.matches()) {
                errors.add(" email : NO_VALID_EMAIL_ADDRESS ");
            }

            if (errors.size() > 0) {
                JsonObject result = new JsonObject();
                result.addProperty("success", 0);
                result.add("error", errorString);
                return Response.status(400).entity(result.toString()).build();
            }

            //Check for existing username / email
            final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
            if (persons.activePerson(userName) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat(" userName : ALREADY_REGISTERED_AND_HAS_PERSONRECORD ");
                error = true;
            }
            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
            if (applicationUsers.findUserByUsername(userName) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat(" userName : ALREADY_REGISTERED_NO_PERSONRECORD ");
                error = true;
            }
            if (applicationUsers.findUserByEmail(email) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat(" email : ALREADY_REGISTERED_UNDER_OTHER_USERNAME ");
                error = true;
            }

            if (error) {
                JsonObject result = new JsonObject();
                result.addProperty("success", 0);
                result.addProperty("error", errorString);
                return Response.status(400).entity(result.toString()).build();
            }

            //Register user

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(userName);
            userDetails.setPassword(password);
            userDetails.setConfirmPassword(passwordConfirm);
            userDetails.setEmailAddress(email);

            final UserRegistrationService appUserRegistrationService =
                    IsisContext.getPersistenceSession().getServicesInjector().lookupService(AppUserRegistrationService.class);
            appUserRegistrationService.registerUser(userDetails);

            // Check if the user is registered correctly and enabled
            if (applicationUsers.findUserByUsername(userName) != null
                    &&
                    applicationUsers.findUserByUsername(userName).getStatus().equals(ApplicationUserStatus.ENABLED)
                    ) {

                return Response.status(200).entity("{\"success\" : 1}").build();

            } else {

                errorString = errorString.concat(" email  :  ALREADY_REGISTERED_UNDER_OTHER_USERNAME ");
                JsonObject result = new JsonObject();
                result.addProperty("success", 0);
                result.addProperty("error", errorString);
                return Response.status(400).entity(result.toString()).build();
            }

        }
    }

    @POST
    @Path("/hdid")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response objectHdid(InputStream object) {
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {
            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});
        } else {

            String id1 = "email";
            JsonRepresentation propertyEmail = argRepr.getRepresentation(id1, new Object[0]);
            String email = propertyEmail.getString("");

            //input validation

            boolean error = false;
            String errorString;
            errorString = "";

            //email should be conform REGEX
            Matcher matcher = EMAIL_REGEX.matcher(email);
            if (!matcher.matches()) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"email\" : \"NO_VALID_EMAIL_ADDRESS\"");
                error = true;
            }

            if (error) {
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"error\" : ");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}");
                return Response.status(200).entity(responseMessage).build();
            }

            //Check for existing username / email
            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);

            if (applicationUsers.findUserByEmail(email) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"email\" : \"ALREADY_REGISTERED_UNDER_OTHER_USERNAME\"");
                error = true;
            }

            if (error) {
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"error\" : ");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}");
                return Response.status(200).entity(responseMessage).build();
            }

            //Register user

            final Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

            api.findOrRegisterBasicUser(email);

            // Check if the user is registered correctly and enabled
            if (applicationUsers.findUserByUsername(email) != null
                    &&
                    applicationUsers.findUserByUsername(email).getStatus().equals(ApplicationUserStatus.ENABLED)
                    ) {

                return Response.status(200).entity("{\"success\" : 1}").build();

            } else {

                errorString = errorString.concat("\"email\" : \"ALREADY_REGISTERED_UNDER_OTHER_USERNAME\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"unkown error: user with username '" + userName + "' NOT registered\"}").build();
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"error\" : ");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}");
                return Response.status(200).entity(responseMessage).build();
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

    @GET
    @Path("/oauth/LinkedIn")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getServices(@QueryParam("code") String code) {
        System.out.println("code = " + code);

        //Get the details from isis.properties
        final IsisPropertiesLookUpService isisPropertiesLookUpService =
                IsisContext.getPersistenceSession().getServicesInjector().lookupService(IsisPropertiesLookUpService.class);

        final String linkedInClientId = isisPropertiesLookUpService.linkedInClientId();
        final String linkedInRedirectUri = isisPropertiesLookUpService.linkedInRedirectUri();
        final String linkedInClientSecret = isisPropertiesLookUpService .linkedInClientSecret();

        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.LINKEDIN)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(linkedInClientId)
                    .setClientSecret(linkedInClientSecret)
                    .setRedirectURI(linkedInRedirectUri)
                    .setCode(code)
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

            System.out.println(
                    "Access LinkedInToken: " + oAuthResponse.getAccessToken() + ", Expires in: " + oAuthResponse
                            .getExpiresIn());

            final LinkedInTokens linkedInTokensService =
                    IsisContext.getPersistenceSession().getServicesInjector().lookupService(LinkedInTokens.class);

            linkedInTokensService.create(oAuthResponse.getAccessToken());
            linkedInTokensService.createLinkedInProfile(oAuthResponse.getAccessToken());

        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity("<h1>You can close this window</h1>").build();
    }

    @GET
    @Path("/oauth/fb")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getFbServices(@QueryParam("code") String code) {
        System.out.println("code = " + code);

        //Get the details from isis.properties
        final IsisPropertiesLookUpService isisPropertiesLookUpService =
                IsisContext.getPersistenceSession().getServicesInjector().lookupService(IsisPropertiesLookUpService.class);

        final String fbClientId = isisPropertiesLookUpService.FbClientId();
        final String fbRedirectUri = isisPropertiesLookUpService.FbRedirectUri();
        final String fbClientSecret = isisPropertiesLookUpService .FbClientSecret();

        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.FACEBOOK)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(fbClientId)
                    .setClientSecret(fbClientSecret)
                    .setRedirectURI(fbRedirectUri)
                    .setCode(code)
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Own response class is an easy way to deal with oauth providers that introduce modifications to
            //OAuth specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);

            System.out.println(
                    "Access FacebookToken: " + oAuthResponse.getAccessToken() + ", Expires in: " + oAuthResponse
                            .getExpiresIn());

            final FbTokens fbTokensService =
                    IsisContext.getPersistenceSession().getServicesInjector().lookupService(FbTokens.class);

            fbTokensService.create(oAuthResponse.getAccessToken());
            fbTokensService.createFbProfile(oAuthResponse.getAccessToken());

        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity("<h1>You can close this window</h1>").build();
    }


}
