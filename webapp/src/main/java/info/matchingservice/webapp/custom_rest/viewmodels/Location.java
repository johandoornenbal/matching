package info.matchingservice.webapp.custom_rest.viewmodels;

/**
 * Created by jonathan on 19-12-15.
 */
public class Location {

    private final String city;
    private String postal;

    public Location(String city) {
        this.city = city;
    }

    public Location(String city, String postal) {
        this.city = city;
        this.postal = postal;
    }
}
