package info.matchingservice.webapp.custom_rest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by jonathan on 15-12-15.
 */
public interface JsonConvertable {

    default String getRootTitle(){
        return getClass().getSimpleName();
    }

    Gson gs = new Gson();

    default JsonElement asJsonElement(){

        JsonObject root = new JsonObject();
        root.add(getRootTitle(), gs.toJsonTree(this));
        return root;
    }


}
