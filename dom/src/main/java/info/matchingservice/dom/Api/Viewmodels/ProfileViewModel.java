package info.matchingservice.dom.Api.Viewmodels;

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
public class ProfileViewModel extends ApiAbstractViewModel {

    public ProfileViewModel(){}

    public ProfileViewModel(final Profile profile){
        super(profile);
        this.name = profile.getName();
        if (profile.getStartDate() != null) {
            this.startDate = profile.getStartDate().toString();
        }
        if (profile.getEndDate() != null) {
            this.endDate = profile.getEndDate().toString();
        }
        this.imageUrl = profile.getImageUrl();
        this.type = profile.getType().title();
        this.demandOrSupply = profile.getDemandOrSupply().toString();
        if (profile.getDemandOrSupply() == DemandOrSupply.DEMAND) {
            this.demand = profile.getDemand().getIdAsInt();
        }
        if (profile.getDemandOrSupply() == DemandOrSupply.SUPPLY) {
            this.supply = profile.getSupply().getIdAsInt();
        }
        this.owner = profile.getOwner().getIdAsInt();

        List<Integer> profileElements = new ArrayList<>();
        for (ProfileElement element : profile.getElements()) {
            profileElements.add(element.getIdAsInt());
        }
        this.elements = profileElements;

    }

    //region > description (property)
    private String name;

    @MemberOrder(sequence = "1")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    //region > startDate (property)
    private String startDate;

    @MemberOrder(sequence = "2")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    //endregion

    //region > endDate (property)
    private String endDate;

    @MemberOrder(sequence = "3")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
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

    //region > type (property)
    private String type;

    @MemberOrder(sequence = "1")
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
    //endregion

    //region > demandOrSupply (property)
    private String demandOrSupply;

    @MemberOrder(sequence = "1")
    public String getDemandOrSupply() {
        return demandOrSupply;
    }

    public void setDemandOrSupply(final String demandOrSupply) {
        this.demandOrSupply = demandOrSupply;
    }
    //endregion

    //region > demand (property)
    private Integer demand;

    @MemberOrder(sequence = "1")
    public Integer getDemand() {
        return demand;
    }

    public void setDemand(final Integer demand) {
        this.demand = demand;
    }
    //endregion

    //region > supply (property)
    private Integer supply;

    @MemberOrder(sequence = "1")
    public Integer getSupply() {
        return supply;
    }

    public void setSupply(final Integer supply) {
        this.supply = supply;
    }
    //endregion

    //region > owner (property)
    private Integer owner;

    @MemberOrder(sequence = "1")
    public Integer getOwner() {
        return owner;
    }

    public void setOwner(final Integer owner) {
        this.owner = owner;
    }
    //endregion

    private List<Integer> elements;

    public List<Integer> getElements() {
        return elements;
    }

    public void setElements(List<Integer> elements) {
        this.elements = elements;
    }
}
