package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 19-12-15.
 */
public class ProfileZper extends ProfileBasic {

    private final Company company;
    private List<Interest> interests = new ArrayList<>();


    public ProfileZper(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, String story, String pictureBackground, String city, Company company, List<Interest> interests) {
        super(id, firstName, middleName, lastName, picture, entity, roles, story, pictureBackground, city);
        this.company = company;
        this.interests = interests;
    }

    public ProfileZper(ProfileBasic profileBasic, List<Interest> interests, Company company){
        super(profileBasic);
        this.interests = interests;
        this.company = company;


    }
}
