package info.matchingservice.webapp.custom_rest.viewmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 15-12-15.
 */
public class ProfileStudent extends ProfileBasic {


    private List<Interest> interests = new ArrayList<>();
    //private final String institute, education;



    public ProfileStudent(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles) {
        super(id, firstName, middleName, lastName, picture, entity, roles);
    }



    public void addInterest(Interest interest){
        interests.add(interest);
    }

}
