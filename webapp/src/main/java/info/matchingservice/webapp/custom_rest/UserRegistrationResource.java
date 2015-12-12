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
import info.matchingservice.dom.IsisPropertiesLookUpService;
import info.matchingservice.dom.TestFacebookObjects.FbTokens;
import info.matchingservice.dom.TestLinkedInObjects.LinkedInTokens;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.isis.applib.annotation.Programmatic;
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
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/v2/actions/register")
public class UserRegistrationResource extends ResourceAbstract {

    private static Pattern PASSWORD_REGEX = Pattern.compile("^([^\\s]+)");
//    private static Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private static Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$");
    private static Pattern PHONE_REGEX = Pattern.compile("(^(((0)[1-9]{2}[0-9][-| ]?[1-9]( ?[0-9]){5})|((\\+31|0|0031)[-| ]?[1-9][0-9][1-9]( ?[0-9]){6}))$)"
            + "|(^(((\\\\+31|0|0031)6){1}[-| ]?[1-9]{1}( ?[0-9]){7})$)");


    private Gson gson = new Gson();
    private info.matchingservice.dom.Api.Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(info.matchingservice.dom.Api.Api.class);



    /**sends a post to the email server so the admin gets notified when a new user is registered
     *
     * @param postObject
     * @return
     */
    private boolean sendNewUserEmail(final String firstName, final String middleName, final String lastName,
                                     final String email, final String subject){

        final String mailHost = "dev.xtalus.nl";
        final String mailEndpoint = "/api/mail/confirm/activation";
        //final String mailEndpoint = "localhost";
        // create data

        JsonObject data = new JsonObject();
        data.addProperty("firstname", firstName);
        data.addProperty("middlename", middleName);
        data.addProperty("lastname", lastName);
        data.addProperty("email", email);
        data.addProperty("subject", subject);

        JsonObject jsonBody = new JsonObject();
        jsonBody.add("data", data);

        System.out.println(" DATA : " + jsonBody.toString());

        // setup client
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost host = new HttpHost(mailHost);
        HttpPost request = new HttpPost(mailEndpoint);


        try {
            StringEntity body = new StringEntity(jsonBody.toString(), ContentType.APPLICATION_JSON);

            System.out.println(" BODY : " + body.toString());
            request.setEntity(body);
            httpClient.execute(host, request);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;


        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }



        return true;

    }


    /**registers a user
     * input
     *firstName
     *middleName
     *lastName
     *email
     * phone
     *postal
     * city
     * entity
     * password
     * passwordConfirm
     *
     *http://docs.xtalus.apiary.io/#reference/0/actions/registration
     *
     * @param object
     * @return
     */
    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response registerUser(InputStream object) {
        String objectStr = Util.asStringUtf8(object);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if(!argRepr.isMap()) {

            throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.BAD_REQUEST, "Body is not a map; got %s", new Object[]{argRepr});

        } else {

            String firstName= "", middleName = "", lastName= "", email= null, phone= "", postal= "", city= "", entity= "", password= "a", passwordConfirm= "";

            TreeMap<String, String> errors = new TreeMap<>();

            // GET PARAMETERS
            try {
                firstName = getParameterValue("firstName", argRepr);
            } catch (Exception e) {
                errors.put("firstname" ,"required'");
            }

            try {
                middleName = getParameterValue("middleName", argRepr);
            } catch (Exception e) {
                //ignore
            }
            try {
                lastName = getParameterValue("lastName", argRepr);
            } catch (Exception e) {
                errors.put("lastName", "required");
            }
            try {
                email  = getParameterValue("email", argRepr);
            } catch (Exception e) {
                errors.put("email", "required");
            }

            try {
                postal = getParameterValue("postal", argRepr);
            } catch (Exception e) {
                errors.put("postal", "required");
            }
            try {
                city = getParameterValue("city", argRepr);
            } catch (Exception e) {
                errors.put("city", "required");
            }

            try {
                entity = getParameterValue("entity", argRepr);
            } catch (Exception e) {
                errors.put("entity", "required");
            }
            try {
                password = getParameterValue("password", argRepr);
            } catch (Exception e) {
                errors.put("password", "required");
            }
            try {
                passwordConfirm = getParameterValue("passwordConfirm", argRepr);
            } catch (Exception e) {
                errors.put("passwordConfirm", "required");
            }

            try {
                phone = getParameterValue("phone", argRepr);
            } catch (Exception e) {
                errors.put("phone" , "required");
            }



            if (errors.size()>0) {
                return ErrorMessages.getError400Response(errors);
            }


            // CHECK PARAMETERS

            //passwords should match
            if (!password.equals(passwordConfirm)) {
                errors.put("password", "match");
            }

            if(password.length() < 10){
                errors.put("password", "invalid:length.min");
            }

            //password should be conform REGEX
            Matcher matcher = PASSWORD_REGEX.matcher(password);
            if (!matcher.matches()) {
                errors.put("password", "invalid:regex");
            }

            //email should be conform REGEX
            matcher = EMAIL_REGEX.matcher(email);
            if (!matcher.matches()) {
                errors.put("email", "invalid:format");
            }

            //phone should be conform REGEX
            matcher = PHONE_REGEX.matcher(phone);
            if (!matcher.matches()) {
                errors.put("phone", "invalid:format");
            }


            //firstName and lastName not an empty string
            if (firstName.length()<2){
                errors.put("firstName", "invalid:length");
            }

            if (lastName.length()<2){
                errors.put("lastname", "invalid:length");
            }





            final PersonRoleType roleType;
            switch (entity)  {

                case "STUDENT": roleType = PersonRoleType.STUDENT;
                    break;

                case "ZPER": roleType = PersonRoleType.PROFESSIONAL;
                    break;

                case "MKBER": roleType = PersonRoleType.PRINCIPAL;
                    break;

                default:    roleType = null;
            }

            if (roleType==null){
                errors.put("entity", "invalid:type choose: 'STUDENT', 'ZPER' or 'MKBER'");
            }

            if (errors.size()>0) {
                return ErrorMessages.getError400Response(errors);
            }


            // CHECK EXISTING USER

            //mainEmail becomes userName
            final String userName = email;

            // Check for existing username / email
            final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
            if (persons.activePerson(userName) != null) {
//                errors.put("user", "username already registered and person object already made");
                errors.put("user", "exists");
            }

            final ApplicationUsers applicationUsers = IsisContext.getPersistenceSession().getServicesInjector().lookupService(ApplicationUsers.class);
            if (applicationUsers.findUserByUsername(userName) != null) {
                errors.put("user", "exists");
                //errors.add("username already registered; no person object made");
            }

            if (applicationUsers.findUserByEmail(email) != null) {
                errors.put("email", "exists");
            }

            if (errors.size()>0) {
                return ErrorMessages.getError400Response(errors);
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

            ApplicationUser registeredUser = applicationUsers.findUserByUsername(userName);
            if (registeredUser == null || registeredUser.getStatus() == ApplicationUserStatus.DISABLED ){
                errors.put("server", "registration failure");
                return ErrorMessages.getError400Response(errors);
            }


            final String fakeDateOfBirth = "2000-1-01";
            final String standardImageUrl = "#";
            final String fakeAddress = "Adress";
            // create user
            Person newPerson = null;
            try {
                newPerson = api.createNewPerson(
                        firstName,
                        middleName,
                        lastName,
                        fakeDateOfBirth,
                        standardImageUrl,
                        roleType,
                        userName,
                        fakeAddress,
                        postal,
                        city,
                        phone
                );

                // admin needs to do this
                newPerson.setActivated(false);
            } catch (Exception e) {
                    errors.put("server", "failure creating person");
                    return ErrorMessages.getError400Response(errors);
            }


            //SEND MAIL TO ADMIN
            //TODO async

            if(!sendNewUserEmail(firstName, middleName, lastName, email, "account geactiveerd")){
                errors.put("admin", "email not send to admin");
                return ErrorMessages.getError400Response(errors);
                //TODO EN WAT NU ?
            }



            JsonObject result = PersonResource.createPersonResult(newPerson.getIdAsInt(), api, gson);
            result.addProperty("success", 1);

            return Response.status(200).entity(result.toString()).build();

        }
    }


    /**returns the parameter for the given name
     * if the parameter is not present throws an exception
     *
     * @param parameterName
     * @param argumentMap
     * @return
     */
    @Programmatic
    private String getParameterValue(final String parameterName, JsonRepresentation argumentMap) throws Exception {
        assert parameterName != null;
        assert  argumentMap != null;
        String value;
        try {
            JsonRepresentation property = argumentMap.getRepresentation(parameterName);
            value = property.getString("");
        }catch (Exception e){
            throw new Exception("cant get value with value name");
        }
        return value;


    }


    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the services resource is not allowed.", new Object[0]);
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
