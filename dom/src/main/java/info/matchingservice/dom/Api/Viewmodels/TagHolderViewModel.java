package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Tags.TagHolder;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 02/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class TagHolderViewModel extends ApiAbstractViewModel {

    public TagHolderViewModel(){}

    public TagHolderViewModel(
            final TagHolder tagHolder
    ){
        super(tagHolder);
        this.description = tagHolder.getTag().getTagDescription();
        this.category = tagHolder.getTag().getTagCategory().getTagCategoryDescription();
        this.numberOfTimesUsed = tagHolder.getTag().getNumberOfTimesUsed();
        this.dateLastUsed = tagHolder.getTag().getDateLastUsed().toString();
    }

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

    //region > category (property)
    private String category;

    @MemberOrder(sequence = "2")
    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }
    //endregion

    //region > numberOfTimesUsed (property)
    private Integer numberOfTimesUsed;

    @MemberOrder(sequence = "3")
    public Integer getNumberOfTimesUsed() {
        return numberOfTimesUsed;
    }

    public void setNumberOfTimesUsed(final Integer numberOfTimesUsed) {
        this.numberOfTimesUsed = numberOfTimesUsed;
    }
    //endregion

    //region > dateLastUsed (property)
    private String dateLastUsed;

    @MemberOrder(sequence = "1")
    public String getDateLastUsed() {
        return dateLastUsed;
    }

    public void setDateLastUsed(final String dateLastUsed) {
        this.dateLastUsed = dateLastUsed;
    }
    //endregion


}
