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
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.AppUserRegistrationService;
import info.matchingservice.dom.Howdoido.Api;
import info.matchingservice.dom.IsisPropertiesLookUpService;
import info.matchingservice.dom.TestFacebookObjects.FbTokens;
import info.matchingservice.dom.TestLinkedInObjects.LinkedInTokens;
import info.matchingservice.dom.Utils.Utils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/register")
public class UserRegistrationResource extends ResourceAbstract {

//    private static Pattern USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]+$");
    private static Pattern PASSWORD_REGEX = Pattern.compile("^([^\\s]+)");
//    private static Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");


    private Gson gson = new Gson();
    private info.matchingservice.dom.Api.Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(info.matchingservice.dom.Api.Api.class);
    private List<String> errors = new ArrayList<>();

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response object(InputStream object) {
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            String id1 = "mainEmail";
            String mainEmail = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id1, new Object[0]);
                mainEmail = property.getString("");
            } catch (Exception e) {
                errors.add("property 'mainEmail' is mandatory");
            }

            String id2 = "password";
            String password = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id2, new Object[0]);
                password = property.getString("");
            } catch (Exception e) {
                errors.add("property 'password' is mandatory");
            }

            String id3 = "passwordConfirm";
            String passwordConfirm = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id3, new Object[0]);
                passwordConfirm = property.getString("");
            } catch (Exception e) {
                errors.add("property 'passwordConfirm' is mandatory");
            }

            String id4 = "firstName";
            String firstName ="";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id4, new Object[0]);
                firstName = property.getString("");
            } catch (Exception e) {
                errors.add("property 'firstName' is mandatory");
            }

            String id5 = "middleName";
            String middleName = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id5, new Object[0]);
                middleName = property.getString("");
            } catch (Exception e) {
                //ignore
            }

            String id6 = "lastName";
            String lastName="";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id6, new Object[0]);
                lastName = property.getString("");
            } catch (Exception e) {
                errors.add("property 'lastName' is mandatory");
            }

            String id7 = "dateOfBirth";
            String dateOfBirth = "";
            try {
                JsonRepresentation property = argRepr.getRepresentation(id7, new Object[0]);
                dateOfBirth = property.getString("");
            } catch (Exception e) {
                errors.add("property 'dateOfBirth' is mandatory");
            }

            String id8 = "imageUrl";
            String imageUrl = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id8, new Object[0]);
                imageUrl = property.getString("");
            } catch (Exception e) {
                // ignore
            }

            String id9 = "mainPhone";
            String mainPhone = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id9, new Object[0]);
                mainPhone = property.getString("");
            } catch (Exception e) {
                errors.add("property 'mainPhone' is mandatory");
            }

            String id10 = "mainAddress";
            String mainAddress = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id10, new Object[0]);
                mainAddress = property.getString("");
            } catch (Exception e) {
                errors.add("property 'mainAddress' is mandatory");
            }

            String id11 = "mainPostalCode";
            String mainPostalCode = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id11, new Object[0]);
                mainPostalCode = property.getString("");
            } catch (Exception e) {
                errors.add("property 'mainPostalCode' is mandatory");
            }

            String id12 = "mainTown";
            String mainTown = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id12, new Object[0]);
                mainTown = property.getString("");
            } catch (Exception e) {
                errors.add("property 'mainTown' is mandatory");
            }

            String id13 = "role";
            String role = null;
            try {
                JsonRepresentation property = argRepr.getRepresentation(id13, new Object[0]);
                role = property.getString("");
            } catch (Exception e) {
                errors.add("property 'role' is mandatory");
            }

            //passwords should match
            if (!password.equals(passwordConfirm)) {
                errors.add("passwords should match");
            }

            //password should be conform REGEX
            Matcher matcher = PASSWORD_REGEX.matcher(password);
            if (!matcher.matches()) {
                errors.add("password");
            }

            //email should be conform REGEX
            matcher = EMAIL_REGEX.matcher(mainEmail);
            if (!matcher.matches()) {
                errors.add("no valid email");
            }

            //firstName and lastName not an empty string
            if (firstName.length()<2){
                errors.add("no valid firstName");
            }

            if (lastName.length()<2){
                errors.add("no valid lastName");
            }

            //date should be valid
            if (!Utils.isValidDate(dateOfBirth)) {
                errors.add("no valid date");
            }

            final PersonRoleType roleType;
            switch (role)  {

                case "STUDENT": roleType = PersonRoleType.STUDENT;
                    break;

                case "PRINCIPAL": roleType = PersonRoleType.PRINCIPAL;
                    break;

                case "PROFESSIONAL": roleType = PersonRoleType.PROFESSIONAL;
                    break;

                default:    roleType = null;
            }

            if (roleType==null){
                errors.add("no valid roletype: choose 'STUDENT', 'PROFESSIONAL' or 'PRINCIPAL'");
            }

            if (errors.size()>0) {
                return ErrorMessages.getError400Response(errors);
            }


            //mainEmail becomes userName
            final String userName = mainEmail;

            // Check for existing username / email
            final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
            if (persons.activePerson(userName) != null) {
                errors.add("username already registered and person object already made");
            }

            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
            if (applicationUsers.findUserByUsername(userName) != null) {
                errors.add("username already registered; no person object made");
            }

            if (applicationUsers.findUserByEmail(mainEmail) != null) {
                errors.add("email already registered");
            }

            if (errors.size()>0) {
                return ErrorMessages.getError400Response(errors);
            }

            //Register user

            UserDetails userDetails = new UserDetails();
            userDetails.setUsername(userName);
            userDetails.setPassword(password);
            userDetails.setConfirmPassword(passwordConfirm);
            userDetails.setEmailAddress(mainEmail);

            final UserRegistrationService appUserRegistrationService =
                    IsisContext.getPersistenceSession().getServicesInjector().lookupService(AppUserRegistrationService.class);
            appUserRegistrationService.registerUser(userDetails);

            // Check if the user is registered correctly and enabled
            if (applicationUsers.findUserByUsername(userName) != null
                    &&
                    applicationUsers.findUserByUsername(userName).getStatus().equals(ApplicationUserStatus.ENABLED)
                    )
            {
                //OK
            } else {
                errors.add("registration failure");
                return ErrorMessages.getError400Response(errors);
            }

            Person newPerson = null;
            try {
                newPerson = api.createNewPerson(
                        firstName,
                        middleName,
                        lastName,
                        dateOfBirth,
                        imageUrl,
                        roleType,
                        userName,
                        mainAddress,
                        mainPostalCode,
                        mainTown,
                        mainPhone
                );
            } catch (Exception e) {
                    errors.add("failure creating Person object");
                    errors.add(e.getMessage());
                    return ErrorMessages.getError400Response(errors);
            }

            JsonObject result = PersonResource.createPersonResult(newPerson.getIdAsInt(), api, gson);
            result.addProperty("success", 1);
            return Response.status(200).entity(result.toString()).build();

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
