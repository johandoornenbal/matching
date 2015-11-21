package info.matchingservice.webapp.custom_rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Api.Viewmodels.*;
import info.matchingservice.dom.Assessment.*;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementTag;
import info.matchingservice.dom.Tags.TagHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 20/11/15.
 */
public class Sideloads {

    public static JsonElement supplies(final Person person, final Api api, final Gson gson){

        List<SupplyViewModel> supplyViewmodels = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)) {
            supplyViewmodels.add(new SupplyViewModel(supply, api));
        }
        return gson.toJsonTree(supplyViewmodels);
    }

    public static JsonElement demands(final Person person, final Api api, final Gson gson){

        List<DemandViewModel> demandViewmodels = new ArrayList<>();
        for (Demand demand : api.getDemandsForPerson(person)){
            demandViewmodels.add(new DemandViewModel(demand, api));
        }
        return gson.toJsonTree(demandViewmodels);
    }

    public static JsonElement profiles(final Person person, final Api api, final Gson gson){

        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile, api));
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                profileViewModels.add(new ProfileViewModel(profile, api));
            }
        }
        return gson.toJsonTree(profileViewModels);
    }

    public static JsonElement profiles(final Demand demand, final Api api, final Gson gson) {
        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Profile profile : api.getProfilesForDemand(demand)) {
            profileViewModels.add(new ProfileViewModel(profile, api));
        }
        return gson.toJsonTree(profileViewModels);
    }

    public static JsonElement profiles(final Supply supply, final Api api, final Gson gson) {
        List<ProfileViewModel> profileViewModels = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(supply)) {
            profileViewModels.add(new ProfileViewModel(profile, api));
        }
        return gson.toJsonTree(profileViewModels);
    }


    public static JsonElement profileElements(final Person person, final Api api, final Gson gson){

        // collect the elements
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
                    profileElementList.add(element);
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
                    profileElementList.add(element);
                }
            }
        }

        return GenerateJsonElementService.generateProfileElements(profileElementList, gson);
    }

    public static JsonElement profileElements(final Demand demand, final Api api, final Gson gson) {
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Profile profile : api.getProfilesForDemand(demand)) {
            for (ProfileElement element : profile.getElements()) {
                profileElementList.add(element);
            }
        }
        return GenerateJsonElementService.generateProfileElements(profileElementList, gson);
    }

    public static JsonElement profileElements(final Supply supply, final Api api, final Gson gson) {
        List<ProfileElement> profileElementList = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(supply)) {
            for (ProfileElement element : profile.getElements()) {
                profileElementList.add(element);
            }
        }
        return GenerateJsonElementService.generateProfileElements(profileElementList, gson);
    }

    public static JsonElement profileElements(final Profile profile, final Api api, final Gson gson) {
        return GenerateJsonElementService.generateProfileElements(new ArrayList<>(profile.getElements()), gson);
    }

    public static JsonElement tagHolders(final Person person, final Api api, final Gson gson){

        // collect the Tag elements
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)){
            for (Profile profile : supply.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }
        for (Demand demand : api.getDemandsForPerson(person)){
            for (Profile profile : demand.getProfiles()) {
                for (ProfileElement element : profile.getElements()){
                    if (element.getClass().equals(ProfileElementTag.class)) {
                        profileElementTagList.add((ProfileElementTag) element);
                    }
                }
            }
        }

        //generate viewmodels
        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList){
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        return gson.toJsonTree(tagHolderViewModels);
    }

    public static JsonElement tagHolders(final Demand demand, final Api api, final Gson gson) {
        // sideload tagholders
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Profile profile : api.getProfilesForDemand(demand)) {
            for (ProfileElement element : profile.getElements()) {
                if (element.getClass().equals(ProfileElementTag.class)) {
                    profileElementTagList.add((ProfileElementTag) element);
                }
            }
        }
        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList) {
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        return gson.toJsonTree(tagHolderViewModels);
    }

    public static JsonElement tagHolders(final Supply supply, final Api api, final Gson gson) {
        // sideload tagholders
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();
        for (Profile profile : api.getProfilesForSupply(supply)) {
            for (ProfileElement element : profile.getElements()) {
                if (element.getClass().equals(ProfileElementTag.class)) {
                    profileElementTagList.add((ProfileElementTag) element);
                }
            }
        }
        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList) {
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        return gson.toJsonTree(tagHolderViewModels);
    }

    public static JsonElement tagHolders(final Profile profile, final Api api, final Gson gson) {
        // sideload tagholders
        List<ProfileElementTag> profileElementTagList = new ArrayList<>();

        for (ProfileElement element : profile.getElements()) {
            if (element.getClass().equals(ProfileElementTag.class)) {
                profileElementTagList.add((ProfileElementTag) element);
            }
        }

        List<TagHolderViewModel> tagHolderViewModels = new ArrayList<>();
        for (ProfileElementTag element : profileElementTagList) {
            for (TagHolder tagHolder : element.getCollectTagHolders()) {
                tagHolderViewModels.add(new TagHolderViewModel(tagHolder));
            }
        }
        return gson.toJsonTree(tagHolderViewModels);
    }


    public static JsonElement PersonalContacts(final Person person, final Api api, final Gson gson){
        List<PersonalContactViewModel> personalContactViewmodels = new ArrayList<>();
        for (PersonalContact contact : api.getPersonalContacts(person)){
            personalContactViewmodels.add(new PersonalContactViewModel(contact));
        }
        return gson.toJsonTree(personalContactViewmodels);
    }

    public static JsonElement assessments(final Person person, final Api api, final Gson gson){
        //collect assessments
        List<Assessment> assessments = api.getAllAssessments(person);
        List<AssessmentViewModel> assessmentViewModels = new ArrayList<>();
        for (Assessment assessment : assessments){
            if (assessment.getClass().equals(DemandFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((DemandFeedback)assessment));
            }
            if (assessment.getClass().equals(SupplyFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((SupplyFeedback)assessment));
            }
            if (assessment.getClass().equals(ProfileFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((ProfileFeedback)assessment));
            }
            if (assessment.getClass().equals(ProfileMatchAssessment.class)) {
                assessmentViewModels.add(new AssessmentViewModel((ProfileMatchAssessment) assessment));
            }
        }
        return gson.toJsonTree(assessmentViewModels);
    }

    public static JsonElement assessments(final Demand demand, final Api api, final Gson gson){
        //collect assessments
        List<Assessment> assessments = api.getAllAssessments(demand);
        List<AssessmentViewModel> assessmentViewModels = new ArrayList<>();
        for (Assessment assessment : assessments){
            if (assessment.getClass().equals(DemandFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((DemandFeedback)assessment));
            }
        }
        return gson.toJsonTree(assessmentViewModels);
    }

    public static JsonElement assessments(final Supply supply, final Api api, final Gson gson){
        //collect assessments
        List<Assessment> assessments = api.getAllAssessments(supply);
        List<AssessmentViewModel> assessmentViewModels = new ArrayList<>();
        for (Assessment assessment : assessments){
            if (assessment.getClass().equals(SupplyFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((SupplyFeedback)assessment));
            }
        }
        return gson.toJsonTree(assessmentViewModels);
    }

    public static JsonElement assessments(final Profile profile, final Api api, final Gson gson){
        //collect assessments
        List<Assessment> assessments = api.getAllAssessments(profile);
        List<AssessmentViewModel> assessmentViewModels = new ArrayList<>();
        for (Assessment assessment : assessments){
            if (assessment.getClass().equals(ProfileFeedback.class)) {
                assessmentViewModels.add(new AssessmentViewModel((ProfileFeedback)assessment));
            }
        }
        return gson.toJsonTree(assessmentViewModels);
    }

    public static JsonElement communicationChannels(final Person person, final Api api, final Gson gson){
        List<CommunicationChannelViewModel> communicationChannelViewModels = new ArrayList<>();
        for (CommunicationChannel communicationChannel : api.getCommunicationchannelsForPerson(person)){
            if (communicationChannel.getClass().equals(Email.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Email) communicationChannel));
            }
            if (communicationChannel.getClass().equals(Address.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Address) communicationChannel));
            }
            if (communicationChannel.getClass().equals(Phone.class)) {
                communicationChannelViewModels.add(new CommunicationChannelViewModel((Phone) communicationChannel));
            }
        }
        return gson.toJsonTree(communicationChannelViewModels);
    }


    public static JsonElement profileMatches(final Person person, final Api api, final Gson gson){
        List<ProfileMatchViewModel> profileMatchViewModels = new ArrayList<>();
        for (ProfileMatch match : api.getProfileMatchesForPerson(person)){
            profileMatchViewModels.add(new ProfileMatchViewModel(match));
        }
        return gson.toJsonTree(profileMatchViewModels);
    }


}
