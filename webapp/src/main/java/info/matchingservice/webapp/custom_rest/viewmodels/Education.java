package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

/**
 * Created by jonathan on 27-12-15.
 */
public class Education implements JsonConvertable {


    private final String name, education;
    private final Location location;

    public Education(String name, String education, Location location) {
        this.name = name;
        this.education = education;
        this.location = location;
    }
}
