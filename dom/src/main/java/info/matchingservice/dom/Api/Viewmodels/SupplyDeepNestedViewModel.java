package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 02/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class SupplyDeepNestedViewModel extends ApiAbstractViewModel {

    public SupplyDeepNestedViewModel(){
    }

    public SupplyDeepNestedViewModel(final Supply supply){
        super(supply);
        this.description = supply.getDescription();
        if (supply.getStartDate()!=null){
            this.startDate = supply.getStartDate().toString();

        }
        if (supply.getEndDate()!=null){
            this.endDate = supply.getEndDate().toString();

        }
        this.imageUrl = supply.getImageUrl();
        this.supplyType = supply.getSupplyType().toString();
        this.weight = supply.getWeight();
        List<ProfileDeepNestedViewModel> supplyProfiles = new ArrayList<>();
        for (Profile profile : supply.getProfiles()){
            ProfileDeepNestedViewModel viewModel = new ProfileDeepNestedViewModel(profile);
            supplyProfiles.add(viewModel);
        }
        this.profiles = supplyProfiles;
    }

    //region > demandDescription (property)
    private String description;

    @MemberOrder(sequence = "1")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion



    private String startDate;

    @MemberOrder(sequence = "1")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    //endregion

    private String endDate;

    @MemberOrder(sequence = "1")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    //endregion

    //region > imageUrl (property)
    private String imageUrl;

    @MemberOrder(sequence = "1")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion

    //region > supplyType (property)
    private String supplyType;

    @MemberOrder(sequence = "1")
    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(final String supplyType) {
        this.supplyType = supplyType;
    }
    //endregion

    //region > weight (property)
    private Integer weight;

    @MemberOrder(sequence = "1")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    //endregion

    //region > profiles (property)
    private List<ProfileDeepNestedViewModel> profiles;

    @MemberOrder(sequence = "1")
    public List<ProfileDeepNestedViewModel> getProfiles() {
        return profiles;
    }

    public void setProfiles(final List<ProfileDeepNestedViewModel> profiles) {
        this.profiles = profiles;
    }
    //endregion

}
