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

    public DemandViewModel(){
    }

    public DemandViewModel(final Demand demand){
        super(demand);
        this.demandOwnerId = demand.getDemandOwner().getIdAsInt();
        this.demandOwnerUri = demand.getDemandOwner().getUri();
        this.demandOwnerFullName = demand.getDemandOwner().title();
        this.description = demand.getDescription();
        this.summary = demand.getSummary();
        this.story = demand.getStory();
        if (demand.getStartDate()!=null){
            this.startDate = demand.getStartDate().toString();

        }
        if (demand.getEndDate()!=null){
            this.endDate = demand.getEndDate().toString();

        }
        this.imageUrl = demand.getImageUrl();
        this.demandType = demand.getDemandType().toString();
        this.weight = demand.getWeight();
        List<Integer> demandProfiles = new ArrayList<>();
        for (Profile profile : demand.getDemandProfiles()){
            demandProfiles.add(profile.getIdAsInt());
        }
        this.demandProfiles = demandProfiles;
    }

    //region > demandOwnerId (property)
    private Integer demandOwnerId;

    @MemberOrder(sequence = "1")
    public Integer getDemandOwnerId() {
        return demandOwnerId;
    }

    public void setDemandOwnerId(final Integer demandOwnerId) {
        this.demandOwnerId = demandOwnerId;
    }
    //endregion

    //region > demandOwnerUri (property)
    private String demandOwnerUri;

    @MemberOrder(sequence = "1")
    public String getDemandOwnerUri() {
        return demandOwnerUri;
    }

    public void setDemandOwnerUri(final String demandOwnerUri) {
        this.demandOwnerUri = demandOwnerUri;
    }
    //endregion

    //region > demandOwnerFullName (property)
    private String demandOwnerFullName;

    @MemberOrder(sequence = "1")
    public String getDemandOwnerFullName() {
        return demandOwnerFullName;
    }

    public void setDemandOwnerFullName(final String demandOwnerFullName) {
        this.demandOwnerFullName = demandOwnerFullName;
    }
    //endregion


    //region > description (property)
    private String description;

    @MemberOrder(sequence = "1")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > summary (property)
    private String summary;

    @MemberOrder(sequence = "1")
    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
    //endregion

    //region > story (property)
    private String story;

    @MemberOrder(sequence = "1")
    public String getStory() {
        return story;
    }

    public void setStory(final String story) {
        this.story = story;
    }
    //endregion

    //region > story (property)
    private String startDate;

    @MemberOrder(sequence = "1")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    //endregion

    //region > story (property)
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
    private List<Integer> demandProfiles;

    @MemberOrder(sequence = "1")
    public List<Integer> getDemandProfiles() {
        return demandProfiles;
    }

    public void setDemandProfiles(final List<Integer> demandProfiles) {
        this.demandProfiles = demandProfiles;
    }
    //endregion

}
