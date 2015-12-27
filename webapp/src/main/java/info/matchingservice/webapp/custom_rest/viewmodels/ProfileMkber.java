package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.List;

/**
 * Created by jonathan on 19-12-15.
 */
public class ProfileMkber extends ProfileBasic {

    private final Company company;

    public ProfileMkber(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, String story, String pictureBackground, String city, Company company) {
        super(id, firstName, middleName, lastName, picture, entity, roles, story, pictureBackground, city);
        this.company = company;
    }

    public ProfileMkber(ProfileBasic p, Company company) {
        super(p);
        this.company = company;
    }
}
