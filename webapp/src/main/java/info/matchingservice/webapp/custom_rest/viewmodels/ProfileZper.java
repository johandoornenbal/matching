package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 19-12-15.
 */
public class ProfileZper extends ProfileBasic {

    private List<Interest> interests = new ArrayList<>();
    private List<Company> companies = new ArrayList<>();

    public ProfileZper(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, String story, String pictureBackground, String city, List<Company> companies, List<Interest> interests) {
        super(id, firstName, middleName, lastName, picture, entity, roles, story, pictureBackground, city);
        this.interests = interests;
    }

    public ProfileZper(ProfileBasic profileBasic, List<Interest> interests, List<Company> companies){
        super(profileBasic);
        this.interests = interests;
        this.companies = companies;


    }
}
