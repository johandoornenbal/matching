package info.matchingservice.webapp.custom_rest.viewmodels;

/**
 * Created by jonathan on 19-12-15.
 */
public class Company {


    private final String name, branche, description;
    private final Location location;

    public Company(String name, String branche, String description, Location location) {
        this.name = name;
        this.branche = branche;
        this.description = description;
        this.location = location;
    }
}
