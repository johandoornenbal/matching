package info.matchingservice.webapp.custom_rest.utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jonathan on 15-12-15.
 */
public interface RepositoryResource {



    @GET
    @Path("/")
    Response getAll();



    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getById(@PathParam("id") int id);



}
