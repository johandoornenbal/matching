package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 19-12-15.
 */
public class ProfileZper extends ProfileBasic {

    private final Company company;
    private List<Interest> interests = new ArrayList<>();

    public ProfileZper(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, Company company) {
        super(id, firstName, middleName, lastName, picture, entity, roles);
        this.company = company;
    }

    public void addInterest(Interest interest){
        interests.add(interest);
    }
}
