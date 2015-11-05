package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.DemandSupply.Demand;
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
public class DemandViewModel extends ApiAbstractViewModel {

    DemandViewModel(){
    }

    DemandViewModel(final Demand demand){
        super(demand);
        this.demandDescription = demand.getDemandDescription();
        this.demandSummary = demand.getDemandSummary();
        this.demandStory = demand.getDemandStory();
        if (demand.getDemandOrSupplyProfileStartDate()!=null){
            this.demandOrSupplyProfileStartDate = demand.getDemandOrSupplyProfileStartDate().toString();

        }
        if (demand.getDemandOrSupplyProfileEndDate()!=null){
            this.demandOrSupplyProfileEndDate = demand.getDemandOrSupplyProfileEndDate().toString();

        }
        this.imageUrl = demand.getImageUrl();
        this.demandType = demand.getDemandType().toString();
        this.weight = demand.getWeight();
        List<ProfileViewModel> demandProfiles = new ArrayList<>();
        for (Profile profile : demand.getCollectDemandProfiles()){
            ProfileViewModel viewModel = new ProfileViewModel(profile);
            demandProfiles.add(viewModel);
        }
        this.demandProfiles = demandProfiles;
    }

    //region > demandDescription (property)
    private String demandDescription;

    @MemberOrder(sequence = "1")
    public String getDemandDescription() {
        return demandDescription;
    }

    public void setDemandDescription(final String demandDescription) {
        this.demandDescription = demandDescription;
    }
    //endregion

    //region > demandSummary (property)
    private String demandSummary;

    @MemberOrder(sequence = "1")
    public String getDemandSummary() {
        return demandSummary;
    }

    public void setDemandSummary(final String demandSummary) {
        this.demandSummary = demandSummary;
    }
    //endregion

    //region > demandStory (property)
    private String demandStory;

    @MemberOrder(sequence = "1")
    public String getDemandStory() {
        return demandStory;
    }

    public void setDemandStory(final String demandStory) {
        this.demandStory = demandStory;
    }
    //endregion

    //region > demandStory (property)
    private String demandOrSupplyProfileStartDate;

    @MemberOrder(sequence = "1")
    public String getDemandOrSupplyProfileStartDate() {
        return demandOrSupplyProfileStartDate;
    }

    public void setDemandOrSupplyProfileStartDate(final String demandOrSupplyProfileStartDate) {
        this.demandOrSupplyProfileStartDate = demandOrSupplyProfileStartDate;
    }
    //endregion

    //region > demandStory (property)
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

    //region > demandType (property)
    private String demandType;

    @MemberOrder(sequence = "1")
    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(final String demandType) {
        this.demandType = demandType;
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

    //region > demandProfiles (property)
    private List<ProfileViewModel> demandProfiles;

    @MemberOrder(sequence = "1")
    public List<ProfileViewModel> getDemandProfiles() {
        return demandProfiles;
    }

    public void setDemandProfiles(final List<ProfileViewModel> demandProfiles) {
        this.demandProfiles = demandProfiles;
    }
    //endregion

}
