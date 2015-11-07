package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Profile.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class PersonProfileViewModel extends ApiAbstractViewModel {

    public PersonProfileViewModel(){}

    public PersonProfileViewModel(final Person person){
        super(person.getSupplies().first().getCollectSupplyProfiles().first());
        Profile p = person.getSupplies().first().getCollectSupplyProfiles().first();
        if (p.getProfileStartDate() != null) {
            this.profileStartDate = p.getProfileStartDate().toString();
        }
        if (p.getProfileEndDate() != null) {
            this.profileEndDate = p.getProfileEndDate().toString();
        }
        this.imageUrl = p.getImageUrl();

        List<ProfileElementViewModel> profileElements = new ArrayList<>();
        for (ProfileElement element : p.getCollectProfileElements()) {
            //profile element tag
            if (element.getClass().equals(ProfileElementTag.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTag) element);
                profileElements.add(model);
            }

            //required profile element role
            if (element.getClass().equals(RequiredProfileElementRole.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((RequiredProfileElementRole) element);
                profileElements.add(model);
            }

            //profile element location
            if (element.getClass().equals(ProfileElementLocation.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementLocation) element);
                profileElements.add(model);
            }

            //profile element boolean
            if (element.getClass().equals(ProfileElementBoolean.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementBoolean) element);
                profileElements.add(model);
            }

            //profile element dropdown
            if (element.getClass().equals(ProfileElementDropDown.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementDropDown) element);
                profileElements.add(model);
            }

            //profile element numeric
            if (element.getClass().equals(ProfileElementNumeric.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementNumeric) element);
                profileElements.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementText.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementText) element);
                profileElements.add(model);
            }

            //profile element time period
            if (element.getClass().equals(ProfileElementTimePeriod.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementTimePeriod) element);
                profileElements.add(model);
            }

            //profile element use predicate
            if (element.getClass().equals(ProfileElementUsePredicate.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel((ProfileElementUsePredicate) element);
                profileElements.add(model);
            }

            //profile element text
            if (element.getClass().equals(ProfileElementChoice.class)) {
                ProfileElementViewModel model = new ProfileElementViewModel(element);
                profileElements.add(model);
            }

        }
        this.profileElements = profileElements;
    }

    //region > profileStartDate (property)
    private String profileStartDate;

    @MemberOrder(sequence = "2")
    public String getProfileStartDate() {
        return profileStartDate;
    }

    public void setProfileStartDate(final String profileStartDate) {
        this.profileStartDate = profileStartDate;
    }
    //endregion

    //region > profileEndDate (property)
    private String profileEndDate;

    @MemberOrder(sequence = "3")
    public String getProfileEndDate() {
        return profileEndDate;
    }

    public void setProfileEndDate(final String profileEndDate) {
        this.profileEndDate = profileEndDate;
    }
    //endregion

    //region > imageUrl (property)
    private String imageUrl;

    @MemberOrder(sequence = "4")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion

    private List<ProfileElementViewModel> profileElements;

    public List<ProfileElementViewModel> getProfileElements() {
        return profileElements;
    }

    public void setProfileElements(List<ProfileElementViewModel> profileElements) {
        this.profileElements = profileElements;
    }
}
