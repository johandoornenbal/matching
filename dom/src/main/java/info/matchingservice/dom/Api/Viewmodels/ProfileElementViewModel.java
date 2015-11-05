package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.Tags.TagHolder;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class ProfileElementViewModel extends ApiAbstractViewModel {

    public ProfileElementViewModel() {}

    public ProfileElementViewModel(final ProfileElement element){
        super(element);
        this.description = element.getDescription();
        this.weight = element.getWeight();
        this.widgetType = element.getWidgetType().toString();
        if (element.getDisplayValue() != null) {
            this.displayValue = element.getDisplayValue().toString();
        }
    }

    public ProfileElementViewModel(
            final ProfileElementTag element
    ) {
        this((ProfileElement) element);
        List<TagHolderViewModel> tagholders = new ArrayList<>();
        for (TagHolder tagHolder : element.getCollectTagHolders()){
            TagHolderViewModel tagHolderViewModel = new TagHolderViewModel(tagHolder);
            tagholders.add(tagHolderViewModel);
        }
        this.tagholders = tagholders;
    }

    public ProfileElementViewModel(
            final RequiredProfileElementRole element
    ) {
        this((ProfileElement) element);
        this.student = element.getStudent();
        this.professional = element.getProfessional();
        this.principal = element.getPrincipal();
    }

    public ProfileElementViewModel(
            final ProfileElementLocation element
    ) {
        this((ProfileElement) element);
        this.postcode = element.getPostcode();
        this.latitude = element.getLatitude();
        this.longitude = element.getLongitude();
        this.isValid = element.getIsValid();
    }

    public ProfileElementViewModel(
            final ProfileElementBoolean element
    ) {
        this((ProfileElement) element);
        this.booleanValue = element.getBooleanValue();
    }

    public ProfileElementViewModel(
            final ProfileElementDropDown element
    ) {
        this((ProfileElement) element);
    }

    public ProfileElementViewModel(
            final ProfileElementNumeric element
    ) {
        this((ProfileElement) element);
        this.numericValue = element.getNumericValue();
    }

    public ProfileElementViewModel(
            final ProfileElementText element
    ) {
        this((ProfileElement) element);
        this.textValue = element.getTextValue();
    }

    public ProfileElementViewModel(
            final ProfileElementTimePeriod element
    ) {
        this((ProfileElement) element);
        if (element.getStartDate()!=null){
            this.startDate = element.getStartDate().toString();
        }
        if (element.getEndDate()!=null){
            this.endDate = element.getEndDate().toString();
        }
    }

    public ProfileElementViewModel(
            final ProfileElementUsePredicate element
    ) {
        this((ProfileElement) element);
        this.useTimePeriod = element.getUseTimePeriod();
        this.useAge = element.getUseAge();
    }


    //TODO: check if this one is used and why it is not extending ProfileElement
//    public ProfileElementViewModel(
//            final ProfileElementChoice element
//    ) {
//        this.description = element.getDescription();
//        this.widgetType = element.getWidgetType().toString();
//    }

    /// Generic properties for profileElement /////////////////////////////////////////////

    //region > description (property)
    private String description;

    @MemberOrder(sequence = "2")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > weight (property)
    private Integer weight;

    @MemberOrder(sequence = "3")
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    //endregion

    //region > widgetType (property)
    private String widgetType;

    @MemberOrder(sequence = "4")
    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(final String widgetType) {
        this.widgetType = widgetType;
    }
    //endregion

    //region > displayValue (property)
    private String displayValue;

    @MemberOrder(sequence = "5")
    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(final String displayValue) {
        this.displayValue = displayValue;
    }
    //endregion

    /// properties for profile element tag /////////////////////////////////////////////

    private List<TagHolderViewModel> tagholders;

    public List<TagHolderViewModel> getTagholders() {
        return tagholders;
    }

    public void setTagholders(List<TagHolderViewModel> tagholders) {
        this.tagholders = tagholders;
    }

    /// properties for required profile element role /////////////////////////////////////////////

    //region > student (property)
    private Boolean student;

    @MemberOrder(sequence = "1")
    public Boolean getStudent() {
        return student;
    }

    public void setStudent(final Boolean student) {
        this.student = student;
    }
    //endregion

    //region > professional (property)
    private Boolean professional;

    @MemberOrder(sequence = "1")
    public Boolean getProfessional() {
        return professional;
    }

    public void setProfessional(final Boolean professional) {
        this.professional = professional;
    }
    //endregion

    //region > principal (property)
    private Boolean principal;

    @MemberOrder(sequence = "1")
    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(final Boolean principal) {
        this.principal = principal;
    }
    //endregion

    /// properties for profile element location /////////////////////////////////////////////

    //region > postcode (property)
    private String postcode;

    @MemberOrder(sequence = "1")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }
    //endregion

    //region > latitude (property)
    private Double latitude;

    @MemberOrder(sequence = "1")
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }
    //endregion

    //region > longitude (property)
    private Double longitude;

    @MemberOrder(sequence = "1")
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }
    //endregion

    //region > isValid (property)
    private Boolean isValid;

    @MemberOrder(sequence = "1")
    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(final Boolean isValid) {
        this.isValid = isValid;
    }
    //endregion

    /// properties for profile element boolean /////////////////////////////////////////////

    //region > booleanValue (property)
    private Boolean booleanValue;

    @MemberOrder(sequence = "1")
    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(final Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    //endregion

    /// properties for profile element numeric /////////////////////////////////////////////

    //region > numericValue (property)
    private Integer numericValue;

    @MemberOrder(sequence = "1")
    public Integer getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(final Integer numericValue) {
        this.numericValue = numericValue;
    }
    //endregion

    /// properties for profile element text /////////////////////////////////////////////

    //region > textValue (property)
    private String textValue;

    @MemberOrder(sequence = "1")
    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(final String textValue) {
        this.textValue = textValue;
    }
    //endregion

    /// properties for profile element timePeriod /////////////////////////////////////////////

    //region > startDate (property)
    private String startDate;

    @MemberOrder(sequence = "1")
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    //endregion

    //region > endDate (property)
    private String endDate;

    @MemberOrder(sequence = "1")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    //endregion

    /// properties for profile element usePredicate /////////////////////////////////////////////

    //region > useTimePeriod (property)
    private Boolean useTimePeriod;

    @MemberOrder(sequence = "1")
    public Boolean getUseTimePeriod() {
        return useTimePeriod;
    }

    public void setUseTimePeriod(final Boolean useTimePeriod) {
        this.useTimePeriod = useTimePeriod;
    }
    //endregion

    //region > useAge (property)
    private Boolean useAge;

    @MemberOrder(sequence = "1")
    public Boolean getUseAge() {
        return useAge;
    }

    public void setUseAge(final Boolean useAge) {
        this.useAge = useAge;
    }
    //endregion

}
