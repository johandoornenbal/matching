package info.matchingservice.webapp.custom_rest.utils;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.Xtalus.XtalusProfile;
import info.matchingservice.webapp.custom_rest.viewmodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 27-12-15.
 */
public class XtalusApi {


    private final Api wrappedApi;

    public XtalusApi(Api wrappedApi) {
        this.wrappedApi = wrappedApi;
    }

    public List<Interest> getInterestsByPerson(Person person) {
        return wrappedApi.getDemandsForPerson(person).stream().map(Interest::new).collect(Collectors.toList());
    }




    public ProfileBasic getProfileByPerson(Person person){


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

            entity = "mkb'er";
        }

        XtalusProfile xtalusProfile = wrappedApi.getXtalusProfileByPerson(person);

        String story = "";
        String backgroundImgUrl = "";
        if(xtalusProfile != null){
            story = xtalusProfile.getStory();
            backgroundImgUrl = xtalusProfile.getBackgroundImage();
        }

        //ADDRESS
        Address address = (Address) wrappedApi.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).get(0);
        String city = address.getTown();

        ProfileBasic profileBasic = new ProfileBasic(person.getIdAsInt(), person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getImageUrl(), entity, roles, story, backgroundImgUrl, city);


        if(person.getIsStudent()){
            List<Interest> interests = getInterestsByPerson(person);
            List<Education> educations = wrappedApi.getEducationsByProfile(xtalusProfile).stream().map(Education::new).collect(Collectors.toList());

            return new ProfileStudent(profileBasic, interests, educations);


        }


//        STUDENT("student"),
//                PROFESSIONAL("zp'er"),
//                PRINCIPAL("mkb'er");

        if(person.getIsProfessional()){


        }

        //TODO

        return profileBasic;


        
    }







}
