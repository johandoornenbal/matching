package info.matchingservice.webapp.custom_rest.viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.PersonProfileViewModel;
import info.matchingservice.dom.Api.Viewmodels.PersonViewModel;
import info.matchingservice.webapp.custom_rest.utils.JsonConvertable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 15-12-15.
 */
public class ProfileBasic implements JsonConvertable{




    private final int id;
    private final String firstName, middleName, lastName, picture, entity;
    private final List<String> roles;
    private final String story = "verhaaltje";


    //private final Location location;




    public ProfileBasic(int id, String firstName, String middleName, String lastName, String picture, String entity, List<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.picture = picture;
        this.entity = entity;
        this.roles = roles;
    }

    public static ProfileBasic fromPerson(Person person){
        
        assert person!= null;
        List<String> roles = new ArrayList<>();
        String entity = "";
        if(person.getIsStudent()){
            roles.add("opdrachtnemer");
            entity = "student";
        }
        if(person.getIsProfessional()){
            roles.add("opdrachtgever");
            entity ="zzp'er";
        }
        if(person.getIsPrincipal()){
            roles.add("opdrachtgever");
            roles.add("opdrachtnemer");

            entity = "mkb'er";
        }






        assert entity.equals("");

        return new ProfileBasic(person.getIdAsInt(), person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getImageUrl(), entity, roles);

    }



}
