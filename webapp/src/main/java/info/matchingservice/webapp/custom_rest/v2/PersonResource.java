package info.matchingservice.webapp.custom_rest.v2;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.webapp.custom_rest.utils.RepositoryResource;
import info.matchingservice.webapp.custom_rest.utils.XtalusApi;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.rendering.RestfulObjectsApplicationException;
import org.apache.isis.viewer.restfulobjects.rendering.util.Util;
import org.apache.isis.viewer.restfulobjects.server.resources.ResourceAbstract;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
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


        List<JsonElement> profiles = new ArrayList<>();
        for(Person p : wrappedApi.allActivePersons()){
            try{
                profiles.add(api.getProfileByPerson(p).asJsonElement());
            }catch (Exception e){
            }
        }
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(InputStream is, @PathParam("id")int id) {

        String objectStr = Util.asStringUtf8(is);
        JsonRepresentation argRepr = Util.readAsMap(objectStr);
        if (!argRepr.isMap()) {
            return Response.status(400).build();}


        // name
        //

        String firstName , middleName , lastName , picture,  pictureBackground;

        firstName = getParameterValue("firstName", argRepr);
        middleName = getParameterValue("middleName", argRepr);
        lastName = getParameterValue("lastName", argRepr);
        picture = getParameterValue("picture", argRepr);
        pictureBackground = getParameterValue("pictureBackground", argRepr);


        
        System.out.println("JOOOOOOOOOOO" + argRepr.toString());

        Person ap = wrappedApi.activePerson();
        if(ap == null){
            return Response.status(404).build();
        }
        return Response.ok().build();

    }



    /**
     * returns the parameter for the given name
     * if the parameter is not present throws an exception
     *
     * @param parameterName
     * @param argumentMap
     * @return
     */
    private String getParameterValue(final String parameterName, JsonRepresentation argumentMap) {
        assert parameterName != null;
        assert argumentMap != null;
        String value;
        try {
            JsonRepresentation property = argumentMap.getRepresentation(parameterName);
            value = property.getString("");
        } catch (Exception e) {
            return null;
        }
        return value;
    }


    private Integer getParameterValueInt(final String parameterName, JsonRepresentation argumentMap) {
        assert parameterName != null;
        assert argumentMap != null;
        int value;
        try {
            JsonRepresentation property = argumentMap.getRepresentation(parameterName);
            value = property.getInt("");
        } catch (Exception e) {
            return null;
        }
        return value;
    }


}
