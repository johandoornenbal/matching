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

        List<Integer> profileElements = new ArrayList<>();
        for (ProfileElement element : profile.getCollectProfileElements()) {
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

    private List<Integer> elements;

    public List<Integer> getElements() {
        return elements;
    }

    public void setElements(List<Integer> elements) {
        this.elements = elements;
    }
}
