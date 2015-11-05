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
public class SupplyViewModel extends ApiAbstractViewModel {

    SupplyViewModel(){
    }

    SupplyViewModel(final Supply supply){
        super(supply);
        this.supplyDescription = supply.getSupplyDescription();
        if (supply.getDemandOrSupplyProfileStartDate()!=null){
            this.demandOrSupplyProfileStartDate = supply.getDemandOrSupplyProfileStartDate().toString();

        }
        if (supply.getDemandOrSupplyProfileEndDate()!=null){
            this.demandOrSupplyProfileEndDate = supply.getDemandOrSupplyProfileEndDate().toString();

        }
        this.imageUrl = supply.getImageUrl();
        this.supplyType = supply.getSupplyType().toString();
        this.weight = supply.getWeight();
        List<ProfileViewModel> supplyProfiles = new ArrayList<>();
        for (Profile profile : supply.getCollectSupplyProfiles()){
            ProfileViewModel viewModel = new ProfileViewModel(profile);
            supplyProfiles.add(viewModel);
        }
        this.supplyProfiles = supplyProfiles;
    }

    //region > demandDescription (property)
    private String supplyDescription;

    @MemberOrder(sequence = "1")
    public String getSupplyDescription() {
        return supplyDescription;
    }

    public void setSupplyDescription(final String supplyDescription) {
        this.supplyDescription = supplyDescription;
    }
    //endregion



    private String demandOrSupplyProfileStartDate;

    @MemberOrder(sequence = "1")
    public String getDemandOrSupplyProfileStartDate() {
        return demandOrSupplyProfileStartDate;
    }

    public void setDemandOrSupplyProfileStartDate(final String demandOrSupplyProfileStartDate) {
        this.demandOrSupplyProfileStartDate = demandOrSupplyProfileStartDate;
    }
    //endregion

    private String demandOrSupplyProfileEndDate;

    @MemberOrder(sequence = "1")
    public String getDemandOrSupplyProfileEndDate() {
        return demandOrSupplyProfileEndDate;
    }

    public void setDemandOrSupplyProfileEndDate(final String demandOrSupplyProfileEndDate) {
        this.demandOrSupplyProfileEndDate = demandOrSupplyProfileEndDate;
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

    //region > supplyProfiles (property)
    private List<ProfileViewModel> supplyProfiles;

    @MemberOrder(sequence = "1")
    public List<ProfileViewModel> getSupplyProfiles() {
        return supplyProfiles;
    }

    public void setSupplyProfiles(final List<ProfileViewModel> supplyProfiles) {
        this.supplyProfiles = supplyProfiles;
    }
    //endregion

}
