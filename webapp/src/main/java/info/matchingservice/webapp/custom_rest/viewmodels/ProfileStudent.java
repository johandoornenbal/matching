package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.DemandSupply.Demand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 15-12-15.
 */
public class ProfileStudent extends ProfileBasic {

    private Education education;
    private List<Interest> interests = new ArrayList<>();
    private List<String> qualities = new ArrayList<>();
    //private final String institute, education;


    public ProfileStudent(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles, String story, String pictureBackground, String city, Education educations, List<Interest> interests) {
        super(id, firstName, middleName, lastName, picture, entity, roles, story, pictureBackground, city);
        this.education = educations;
        this.interests = interests;
    }

    public ProfileStudent(ProfileBasic profileBasic, List<Interest> interests, Education education, List<String> qualities){
        super(profileBasic);
        this.interests = interests;
        this.qualities = qualities;
        this.education = education;


    }
}
