package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 19-12-15.
 */
public class ProfileMkber extends ProfileBasic {

    private List<Company> companies = new ArrayList<>();

    public ProfileMkber(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, String story, String pictureBackground, String city, List<Company> companies) {
        super(id, firstName, middleName, lastName, picture, entity, roles, story, pictureBackground, city);
        this.companies = companies;
    }

    public ProfileMkber(ProfileBasic p, List<Company> companies) {
        super(p);
        this.companies = companies;
    }
}
