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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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




        List<Person> persons = wrappedApi.allActivePersons().stream().filter(person -> person.getIdAsInt() == id).collect(Collectors.toList());


        JsonObject root = new JsonObject();


//        List<JsonElement> profiles = wrappedApi.allActivePersons().stream().filter(person -> person.getIdAsInt() == id).map(person -> ProfileBasic.fromPerson(person).asJsonElement()).collect(Collectors.toList());
        assert persons.size() == 1;

        if(persons.size() != 1){
            return Response.noContent().build();
        }


        Person p = persons.get(0);


        JsonElement responseElement = api.getProfileByPerson(p).asJsonElement();




        return Response.ok(responseElement.toString()).build();

    }
}
