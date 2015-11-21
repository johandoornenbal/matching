package info.matchingservice.webapp.custom_rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import info.matchingservice.dom.Api.Viewmodels.ProfileElementViewModel;
import info.matchingservice.dom.Profile.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 16/11/15.
 */
public class GenerateJsonElementService {

//    private static Gson gson = new Gson();

    public static JsonElement generateProfileElements(final List<ProfileElement> profileElementList, final Gson gson) {

        List<ProfileElementViewModel> profileElementViewModels = new ArrayList<>();

        for (ProfileElement element : profileElementList){
            //profile element tag
            if (element.getClass().equals(ProfileElementTag.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTag) element);
                profileElementViewModels.add(model);
            }

            //required profile element role
            if (element.getClass().equals(RequiredProfileElementRole.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((RequiredProfileElementRole) element);
                profileElementViewModels.add(model);
            }

            //profile element location
            if (element.getClass().equals(ProfileElementLocation.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementLocation) element);
                profileElementViewModels.add(model);
            }

            //profile element boolean
            if (element.getClass().equals(ProfileElementBoolean.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementBoolean) element);
                profileElementViewModels.add(model);
            }

            //profile element dropdown
            if (element.getClass().equals(ProfileElementDropDown.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementDropDown) element);
                profileElementViewModels.add(model);
            }

            //profile element numeric
            if (element.getClass().equals(ProfileElementNumeric.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementNumeric) element);
                profileElementViewModels.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementText.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementText) element);
                profileElementViewModels.add(model);
            }

            //profile element time period
            if (element.getClass().equals(ProfileElementTimePeriod.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTimePeriod) element);
                profileElementViewModels.add(model);
            }

            //profile element use predicate
            if (element.getClass().equals(ProfileElementUsePredicate.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementUsePredicate) element);
                profileElementViewModels.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementChoice.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel(element);
                profileElementViewModels.add(model);
            }

        }
        return gson.toJsonTree(profileElementViewModels);
    }

}
