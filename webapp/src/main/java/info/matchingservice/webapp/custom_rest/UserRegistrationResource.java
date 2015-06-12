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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import org.apache.isis.applib.services.userreg.UserDetails;
import org.apache.isis.applib.services.userreg.UserRegistrationService;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.AppUserRegistrationService;
import info.matchingservice.dom.IsisPropertiesLookUpService;
import info.matchingservice.dom.TestFacebookObjects.FbTokens;
import info.matchingservice.dom.TestLinkedInObjects.LinkedInTokens;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/register")
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

            String id = "username";
            JsonRepresentation propertyUsername = argRepr.getRepresentation(id, new Object[0]);
            String userName = propertyUsername.getString("");

            String id2 = "password";
            JsonRepresentation propertyPassword = argRepr.getRepresentation(id2, new Object[0]);
            String passWord = propertyPassword.getString("");

            String id3 = "passwordConfirm";
            JsonRepresentation propertyPasswordConfirm = argRepr.getRepresentation(id3, new Object[0]);
            String passWordConfirm = propertyPasswordConfirm.getString("");

            String id4 = "email";
            JsonRepresentation propertyEmail = argRepr.getRepresentation(id4, new Object[0]);
            String email = propertyEmail.getString("");

            //input validation

            boolean error = false;
            String errorString = new String();
            errorString = "";
            //username should be conform REGEX
            Matcher matcher = USERNAME_REGEX.matcher(userName);
            if (!matcher.matches()) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"username\" : \"ONE_AND_WORD_LETTERS_NUMBERS_ONLY\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"username '" + userName + "' NOT allowed. Use one word using letters and numbers only.\"}").build();
            }

            //passwords should match
            if (!passWord.equals(passWordConfirm)) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"password\" : \"PASSWORD_NOT_MATCHING\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"passwords do not match\"}").build();
            }

            //password should be conform REGEX
            matcher = PASSWORD_REGEX.matcher(passWord);
            if (!matcher.matches()) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"password\" : \"NO_BLANKS_IN_PASSWORD\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"password NOT allowed. Use one word instead.\"}").build();
            }

            //email should be conform REGEX
            matcher = EMAIL_REGEX.matcher(email);
            if (!matcher.matches()) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"email\" : \"NO_VALID_EMAIL_ADDRESS\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"not a valid email address\"}").build();
            }

            if (error) {
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"errors\" : {");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}}");
                return Response.status(200).entity(responseMessage).build();
            }

            //Check for existing username / email
            final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
            if (persons.findPersonUnique(userName) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"username\" : \"ALREADY_REGISTERED_AND_HAS_PERSONRECORD\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"user with username '" + persons.findPersonUnique(userName).getOwnedBy() + "' already registered and known in Matching App\"}").build();
            }
            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
            if (applicationUsers.findUserByUsername(userName) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"username\" : \"ALREADY_REGISTERED_NO_PERSONRECORD\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"user with username '" + applicationUsers.findUserByUsername(userName).getUsername() + "' already registered but has no Person record yet\"}").build();
            }
            if (applicationUsers.findUserByEmail(email) != null) {
                if (error) {
                    errorString = errorString.concat(", ");
                }
                errorString = errorString.concat("\"email\" : \"ALREADY_REGISTERED_UNDER_OTHER_USERNAME\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"emailaddres '" + applicationUsers.findUserByEmail(email).getEmailAddress() + "' already registered under another username\"}").build();
            }

            if (error) {
                String responseMessage = new String();
                responseMessage = responseMessage.concat("{\"success\" : 0, \"errors\" : {");
                responseMessage = responseMessage.concat(errorString);
                responseMessage = responseMessage.concat("}}");
                return Response.status(200).entity(responseMessage).build();
            }

            //Register user

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(userName);
            userDetails.setPassword(passWord);
            userDetails.setConfirmPassword(passWordConfirm);
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

                errorString = errorString.concat("\"email\" : \"ALREADY_REGISTERED_UNDER_OTHER_USERNAME\"");
                error = true;
//                return Response.status(200).entity("{\"success\" : 0, \"message\" : \"unkown error: user with username '" + userName + "' NOT registered\"}").build();
                    String responseMessage = new String();
                    responseMessage = responseMessage.concat("{\"success\" : 0, \"errors\" : {");
                    responseMessage = responseMessage.concat(errorString);
                    responseMessage = responseMessage.concat("}}");
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
