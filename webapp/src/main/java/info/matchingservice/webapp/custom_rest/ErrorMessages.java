package info.matchingservice.webapp.custom_rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by jodo on 20/11/15.
 */
public class ErrorMessages {

    public static Response getError400Response(final List<String> errors) {
        Gson gson = new Gson();
        JsonObject errorResult = new JsonObject();
        errorResult.addProperty("success", 0);
        errorResult.add("errors", gson.toJsonTree(errors));
        return Response.status(400).entity(errorResult.toString()).build();
    }

    public static Response getError400Response(final TreeMap<String, String> errors) {
        Gson gson = new Gson();
        JsonObject errorResult = new JsonObject();
        errorResult.addProperty("success", 0);
        errorResult.add("errors", gson.toJsonTree(errors));
        return Response.status(400).entity(errorResult.toString()).build();
    }

}
