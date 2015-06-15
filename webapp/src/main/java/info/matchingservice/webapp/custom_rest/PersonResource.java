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

import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.RestfulMediaType;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

/**
 * Created by jodo on 15/05/15.
 */
@Path("/activeperson")
public class PersonResource extends ResourceAbstract {

    @DELETE
    @Path("/")
    public Response deleteServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Deleting the activeperson resource is not allowed.", new Object[0]);
    }

    @POST
    @Path("/")
    public Response postServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Posting to the activeperson resource is not allowed.", new Object[0]);
    }

    @PUT
    @Path("/")
    public Response putServicesNotAllowed() {
        throw RestfulObjectsApplicationException.createWithMessage(RestfulResponse.HttpStatusCode.METHOD_NOT_ALLOWED, "Putting to the activeperson resource is not allowed.", new Object[0]);
    }

    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, RestfulMediaType.APPLICATION_JSON_OBJECT, RestfulMediaType.APPLICATION_JSON_ERROR })
    public Response getPersonServices()  {

        final Persons persons = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Persons.class);
        Person activePerson = persons.activePerson().get(0);

        Gson gson = new Gson();

        gson.toJson(activePerson.getFirstName());

        JsonRepresentation all = JsonRepresentation.newMap();

        // activeperson
        JsonRepresentation activeperson = JsonRepresentation.newMap();
        activeperson.mapPut("id", activePerson.getUniqueItemId().toString());
        activeperson.mapPut("URI", toObjectURI(activePerson.getOID()));
        activeperson.mapPut("firstName", activePerson.getFirstName());
        activeperson.mapPut("lastName", activePerson.getLastName());
        activeperson.mapPut("middleName", activePerson.getMiddleName());
        all.mapPut("activePerson",activeperson);

        // demands
        JsonRepresentation demandsAndProfilesAndElements = JsonRepresentation.newArray();
        for (Demand demand : activePerson.getCollectDemands()) {

            JsonRepresentation demandAndProfilesAndElementsMap = JsonRepresentation.newMap();
            JsonRepresentation profileAndElements = JsonRepresentation.newArray();

            for (Profile profile : demand.getCollectDemandProfiles()) {

                JsonRepresentation profileElements = JsonRepresentation.newArray();


                for (ProfileElement element : profile.getCollectProfileElements()) {
                    JsonRepresentation profileElementMap = JsonRepresentation.newMap();
                    profileElementMap.mapPut("id", element.getUniqueItemId().toString());
                    profileElementMap.mapPut("URI", toObjectURI(element.getOID()));
                    profileElementMap.mapPut("description", element.getDescription());
                    profileElements.arrayAdd(profileElementMap);
                }

                JsonRepresentation profileAndElementMap = JsonRepresentation.newMap();
                profileAndElementMap.mapPut("id", profile.getUniqueItemId().toString());
                profileAndElementMap.mapPut("URI", toObjectURI(profile.getOID()));
                profileAndElementMap.mapPut("description", profile.getProfileName());
                profileAndElementMap.mapPut("profileElements", profileElements);
                profileAndElements.arrayAdd(profileAndElementMap);

            }

            demandAndProfilesAndElementsMap.mapPut("id", demand.getUniqueItemId().toString());
            demandAndProfilesAndElementsMap.mapPut("URI", toObjectURI(demand.getOID()));
            demandAndProfilesAndElementsMap.mapPut("description", demand.getDemandDescription());
            demandAndProfilesAndElementsMap.mapPut("profiles", profileAndElements);
            demandsAndProfilesAndElements.arrayAdd(demandAndProfilesAndElementsMap);
        }
        all.mapPut("demands", demandsAndProfilesAndElements);

        //supplies
        JsonRepresentation suppliesAndProfilesAndElements = JsonRepresentation.newArray();
        for (Supply supply : activePerson.getCollectSupplies()) {

            JsonRepresentation supplyAndProfilesAndElementsMap = JsonRepresentation.newMap();
            JsonRepresentation profileAndElements = JsonRepresentation.newArray();

            for (Profile profile : supply.getCollectSupplyProfiles()) {

                JsonRepresentation profileElements = JsonRepresentation.newArray();


                for (ProfileElement element : profile.getCollectProfileElements()) {
                    JsonRepresentation profileElementMap = JsonRepresentation.newMap();
                    profileElementMap.mapPut("id", element.getUniqueItemId().toString());
                    profileElementMap.mapPut("URI", toObjectURI(element.getOID()));
                    profileElementMap.mapPut("description", element.getDescription());
                    profileElements.arrayAdd(profileElementMap);
                }

                JsonRepresentation profileAndElementMap = JsonRepresentation.newMap();
                profileAndElementMap.mapPut("id", profile.getUniqueItemId().toString());
                profileAndElementMap.mapPut("URI", toObjectURI(profile.getOID()));
                profileAndElementMap.mapPut("description", profile.getProfileName());
                profileAndElementMap.mapPut("profileElements", profileElements);
                profileAndElements.arrayAdd(profileAndElementMap);

            }

            supplyAndProfilesAndElementsMap.mapPut("id", supply.getUniqueItemId().toString());
            supplyAndProfilesAndElementsMap.mapPut("URI", toObjectURI(supply.getOID()));
            supplyAndProfilesAndElementsMap.mapPut("description", supply.getSupplyDescription());
            supplyAndProfilesAndElementsMap.mapPut("profiles", profileAndElements);
            suppliesAndProfilesAndElements.arrayAdd(supplyAndProfilesAndElementsMap);
        }
        all.mapPut("supplies", suppliesAndProfilesAndElements);

        return Response.status(200).entity(all.toString()).build();
    }



    private String toObjectURI(final String OID){
        String[] parts = OID.split(Pattern.quote("[OID]"));
        String part1 = parts[0];
        String part2 = parts[1];
        String URI = "objects/".concat(part2).concat("/L_").concat(part1);
        return URI;
    }

}
