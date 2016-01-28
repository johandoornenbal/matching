package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

/**
 * Created by jonathan on 27-12-15.
 */
public class Education implements JsonConvertable {


    private final String institute, name, honoursProgram;
    private final Location location;



    public Education(String institute, String name, Location location, String honoursProgram) {
        this.institute = institute;
        this.name = name;
        this.location = location;
        this.honoursProgram = honoursProgram;
    }

}
