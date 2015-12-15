package info.matchingservice.webapp.custom_rest.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Created by jonathan on 15-12-15.
 */
public interface JsonConvertable {

    Gson gs = new Gson();

    default JsonElement asJsonElement(){
        return gs.toJsonTree(this);
    }


}
