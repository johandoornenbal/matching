package info.matchingservice.webapp.custom_rest.v2;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.webapp.custom_rest.utils.RepositoryResource;
import info.matchingservice.webapp.custom_rest.utils.XtalusApi;
import info.matchingservice.webapp.custom_rest.viewmodels.ProfileBasic;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        JsonObject root = new JsonObject();
        JsonElement responseElement = api.getProfileByPerson(person.get()).asJsonElement();
        return Response.ok(responseElement.toString()).build();
    }


    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id")int id, JsonObject personJson) {

        Person ap = wrappedApi.activePerson();
        if(ap == null){
            return Response.status(404).build();
        }

//        Optional<Person> person = wrappedApi.getPersonById(id);
//        if (!person.isPresent()){
//            return Response.status(404).build();
//        }

        System.out.println("JOOOOOOOOOOOO" + personJson.toString());


        return Response.ok().build();

    }

}
