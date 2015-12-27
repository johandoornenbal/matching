package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 15-12-15.
 */
public class ProfileBasic implements JsonConvertable {


    @Override
    public String getRootTitle() {
        return "person";
    }

    protected final int id;
    protected final String firstName, middleName, lastName, picture, entity, city;
    protected final List<String> roles;
    protected final String story;
    protected final String pictureBackground;



    //private final Location location;




    public ProfileBasic(int id, String firstName, String middleName, String lastName, String picture, String entity,
                        List<String> roles, String story, String pictureBackground, String city) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.picture = picture;
        this.entity = entity;
        this.roles = roles;
        this.story = story;
        this.pictureBackground = pictureBackground;
        this.city = city;
    }

    public ProfileBasic(ProfileBasic p){
        this.id = p.id;
        this.firstName = p.firstName;
        this.middleName = p.middleName;
        this.lastName = p.lastName;
        this.picture = p.picture;
        this.entity = p.entity;
        this.roles = p.roles;
        this.story = p.story;
        this.pictureBackground = p.pictureBackground;
        this.city = p.city;

    }





}
