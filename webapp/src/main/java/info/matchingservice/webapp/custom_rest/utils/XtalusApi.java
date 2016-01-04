package info.matchingservice.webapp.custom_rest.utils;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
import info.matchingservice.dom.Xtalus.XtalusProfile;
import info.matchingservice.webapp.custom_rest.viewmodels.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 27-12-15.
 */
public class XtalusApi {


    private final Api wrappedApi;

    public XtalusApi(Api wrappedApi) {
        this.wrappedApi = wrappedApi;
    }



    public Profile getPersonProfile(Person person){
        Supply personSupply = wrappedApi.getSuppliesForPerson(person).stream().filter(supply -> supply.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY).findFirst().get();
        return personSupply.getProfiles().stream().filter(profile -> profile.getType() == ProfileType.PERSON_PROFILE).findFirst().get();
    }

    public Supply getPersonalSupply(Person person){
       return wrappedApi.getSuppliesForPerson(person).stream().filter(supply -> supply.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY).findFirst().get();
    }



    public List<Interest> getInterests(Person person){


        Supply personSupply = getPersonalSupply(person);
        List<Profile> jobProfiles = personSupply.getProfiles().stream().filter(profile -> profile.getType()== ProfileType.INTEREST_PROFILE).collect(Collectors.toList());

        List<Interest> interests = new ArrayList<>();
        for(Profile d: jobProfiles){

            ProfileElementNumeric availableElement = (ProfileElementNumeric) d.getElements().stream().filter(profileElement -> profileElement.getProfileElementType() == ProfileElementType.TIME_AVAILABLE && profileElement instanceof ProfileElementNumeric)
                    .findFirst().get();
            int timeAvailable = availableElement.getNumericValue();
            Interest i = new Interest(d.getName(), d.getStartDate(), d.getEndDate(), timeAvailable);
            interests.add(i);

        }
        return interests;


    }

    public List<Quality> getQualities(Person student){


        Profile personProfile = getPersonProfile(student);

        List<ProfileElementTag> qualityElements = new ArrayList<>();
        personProfile.getElements().stream().filter(profileElement -> profileElement instanceof ProfileElementTag && profileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS).forEach(profileElement1 -> qualityElements.add((ProfileElementTag) profileElement1));

        List<TagHolder> qualityTagHolders = new ArrayList<>();

        qualityElements.stream().filter(profileElement1 -> profileElement1 instanceof  ProfileElementTag).forEach(profileElement2 -> qualityTagHolders.addAll(((ProfileElementTag)profileElement2).getCollectTagHolders()));
        List<Quality> qualities = new ArrayList<>();
        qualityTagHolders.forEach(tagHolder -> qualities.add(new Quality(tagHolder.getOwnerElement().getWeight(), tagHolder.getTag().getTagDescription())));

        return qualities;
    }


    public ProfileBasic getProfileByPerson(Person person){


        List<String> roles = new ArrayList<>();
        String entity = "";
        if(person.getIsStudent()){
            roles.add("opdrachtnemer");
            entity = "student";
        }else if(person.getIsProfessional()){
            roles.add("opdrachtgever");
            entity ="zzp'er";
        } else if(person.getIsPrincipal()){
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
            List<Interest> interests = getInterests(person);
            List<Education> educations = wrappedApi.getEducationsByProfile(xtalusProfile).stream().map(Education::new).collect(Collectors.toList());
            List<Quality> qualities = getQualities(person);

            return new ProfileStudent(profileBasic, interests, educations, qualities);


        }

        getQualities(person);

//        STUDENT("student"),
//                PROFESSIONAL("zp'er"),
//                PRINCIPAL("mkb'er");

        if(person.getIsProfessional()){


        }

        //TODO

        return profileBasic;


        
    }







}
