package info.matchingservice.webapp.custom_rest.v2;

import com.google.gson.JsonElement;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.webapp.custom_rest.utils.RepositoryResource;
import info.matchingservice.webapp.custom_rest.viewmodels.ProfileBasic;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 15-12-15.
 */
@Path("/v2/persons/")
public class PersonResource extends ResourceAbstract implements RepositoryResource{


    private Api api = IsisContext.getPersistenceSession().getServicesInjector().lookupService(Api.class);

    @Override
    public Response getAll() {
        List<JsonElement> profiles = api.allActivePersons().stream().map(person -> ProfileBasic.fromPerson(person).asJsonElement()).collect(Collectors.toList());
        System.out.println(profiles.toString());


        return Response.ok(profiles, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response getById(int id) {
        return null;
    }
}
