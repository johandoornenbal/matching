package info.matchingservice.webapp.custom_rest.v2;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.webapp.custom_rest.utils.RepositoryResource;
import info.matchingservice.webapp.custom_rest.utils.XtalusApi;
import info.matchingservice.webapp.custom_rest.viewmodels.ProfileBasic;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 15-12-15.
 */
@Path("/v2/persons")
public class PersonResource extends ResourceAbstract implements RepositoryResource{


    private Api wrappedApi = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);
    private XtalusApi api = new XtalusApi(wrappedApi);




    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<JsonElement> profiles = wrappedApi.allActivePersons().stream().map(person -> api.getProfileByPerson(person).asJsonElement()).collect(Collectors.toList());
        System.out.println(profiles.toString());


        return Response.ok(profiles.toString()).build();
    }

    @GET
    @Override
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id")int id) {

        Person ap = wrappedApi.activePerson();
        if(ap == null){
            return Response.status(404).build();
        }


        Optional<Person> person = wrappedApi.getPersonById(id);
        if (!person.isPresent()){
            return Response.noContent().build();
        }

        //security
//        if(ap.getIdAsInt() != id){
//            return Response.status(404).build();
//        }

        JsonObject root = new JsonObject();
        JsonElement responseElement = api.getProfileByPerson(person.get()).asJsonElement();
        return Response.ok(responseElement.toString()).build();
    }


    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(InputStream is) {

        
        JsonElement personJson = null;
        try {
            String objectStr = Util.asStringUtf8(is);
            personJson = new Gson().toJsonTree(objectStr);
            if(personJson == null){
                throw new Exception();
            }
        }catch (Exception e){
            return Response.status(400).build();
        }
        Person ap = wrappedApi.activePerson();
        if(ap == null){
            return Response.status(404).build();
        }
        return Response.ok().build();

    }




}
