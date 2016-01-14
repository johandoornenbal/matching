package info.matchingservice.webapp.custom_rest.utils;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
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




    private Company createCompany(Profile companyProfile){
        String name= null, description= null, branche= null, postal= null, city = null;
        name = companyProfile.getName();

        Optional<ProfileElement> pdescription = companyProfile.getElementOfType(ProfileElementType.STORY);
        if(pdescription.isPresent()){
            description = ((ProfileElementText)pdescription.get()).getTextValue();
        }
        Optional<ProfileElement> pbranche = companyProfile.getElementOfType(ProfileElementType.BRANCHE);
        if(pbranche.isPresent()){
            branche = ((ProfileElementText)pbranche.get()).getTextValue();
        }

        Optional<ProfileElement> pcity = companyProfile.getElementOfType(ProfileElementType.CITY);
        if(pcity.isPresent()){
            city = ((ProfileElementText)pcity.get()).getTextValue();
        }

        Optional<ProfileElement> ppostal = companyProfile.getElementOfType(ProfileElementType.LOCATION);
        if(pcity.isPresent()){
            postal = ((ProfileElementLocation)ppostal.get()).getPostcode();
        }


        Location l = new Location(city, postal);

        Company company  = new Company(name, branche, description, l);
        return company;

    }

    public List<Company> getCompaniesOfPerson(Person person){
        List<Company> companies = new ArrayList<>();
        List<Profile> profiles = person.getPersonalSupply().getProfilesOfType(ProfileType.COMPANY_PROFILE);
        profiles.forEach(profile -> companies.add(createCompany(profile)));
        return companies;
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



    public List<String> getQualities(Person student){


        Profile personProfile = getPersonProfile(student);

        List<ProfileElementTag> qualityElements = new ArrayList<>();
        personProfile.getElements().stream().filter(profileElement -> profileElement instanceof ProfileElementTag && profileElement.getProfileElementType() == ProfileElementType.QUALITY_TAGS).forEach(profileElement1 -> qualityElements.add((ProfileElementTag) profileElement1));

        List<TagHolder> qualityTagHolders = new ArrayList<>();

        qualityElements.stream().filter(profileElement1 -> profileElement1 instanceof  ProfileElementTag).forEach(profileElement2 -> qualityTagHolders.addAll(((ProfileElementTag)profileElement2).getCollectTagHolders()));
        List<String> qualities = new ArrayList<>();
        qualityTagHolders.forEach(tagHolder -> qualities.add(tagHolder.getTag().getTagDescription()));

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


        Profile userProfile = wrappedApi.getPersonProfile(person);


        Optional<ProfileElement> storyOpt = userProfile.getElementOfType(ProfileElementType.STORY);
        Optional<ProfileElement> urlBackgroundOpt = userProfile.getElementOfType(ProfileElementType.URL_PROFILE_BACKGROUND);




        String story = "";
        String backgroundImgUrl = "";

        if(storyOpt.isPresent()){
            story = ((ProfileElementText)storyOpt.get()).getTextValue();
        }
        if(urlBackgroundOpt.isPresent()){
            backgroundImgUrl = ((ProfileElementText)urlBackgroundOpt.get()).getTextValue();
        }

        //ADDRESS
        Address address = (Address) wrappedApi.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).get(0);
        String city = address.getTown();

        ProfileBasic profileBasic = new ProfileBasic(person.getIdAsInt(), person.getFirstName(), person.getMiddleName(), person.getLastName(), person.getImageUrl(), entity, roles, story, backgroundImgUrl, city);


        if(person.getIsStudent()){
            List<Interest> interests = getInterests(person);
            List<Education> educations = new ArrayList<>();

            wrappedApi.getEducationsByPerson(person).forEach(education -> educations.add(new Education(education)));
            List<String> qualities = getQualities(person);

            return new ProfileStudent(profileBasic, interests, educations, qualities);


        }


        List<Company> companies = getCompaniesOfPerson(person);
//        STUDENT("student"),
//                PROFESSIONAL("zp'er"),
//                PRINCIPAL("mkb'er");

        if(person.getIsProfessional()){
            List<Interest> interests = getInterests(person);
            return new ProfileZper(profileBasic, interests, companies);

        }

        return new ProfileMkber(profileBasic, companies);


        
    }







}
