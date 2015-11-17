package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.*;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

/**
 * Created by jodo on 02/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class AssessmentViewModel extends ApiAbstractViewModel {

    public AssessmentViewModel(){ }

    public AssessmentViewModel(final Assessment assessment){
        super(assessment);
        this.targetOwnerUri = assessment.getTargetOwnerActor().getUri();
        this.targetOwnerFullName = assessment.getTargetOwnerActor().title();
        this.targetOwner = assessment.getTargetOwnerActor().getIdAsInt();
        this.targetOwnerClassType = assessment.getTargetOwnerActor().getClass().getSimpleName();
        Person targetOwner = (Person) assessment.getTargetOwnerActor();
        this.targetOwnerImageUrl = targetOwner.getImageUrl();
        this.ownerUri = assessment.getAssessmentOwnerActor().getUri();
        this.owner = assessment.getAssessmentOwnerActor().getIdAsInt();
        this.ownerFullName = assessment.getAssessmentOwnerActor().title();
        this.ownerClassType = assessment.getAssessmentOwnerActor().getClass().getSimpleName();
        Person assessmentOwner = (Person) assessment.getAssessmentOwnerActor();
        this.ownerImageUrl = assessmentOwner.getImageUrl();
        this.description = assessment.getDescription();
        this.classType =  assessment.getClass().getSimpleName();
    }

    public AssessmentViewModel(final DemandAssessment assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final SupplyAssessment assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileAssessment assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileMatchAssessment assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileFeedback assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final DemandFeedback assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final SupplyFeedback assessment){
        this((Assessment) assessment);
        this.targetUri = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.target = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

    //region > classType (property)
    private String classType;

    @MemberOrder(sequence = "1")
    public String getClassType() {
        return classType;
    }

    public void setClassType(final String classType) {
        this.classType = classType;
    }
    //endregion

    //region > targetType (property)
    private String targetType;

    @MemberOrder(sequence = "1")
    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(final String targetType) {
        this.targetType = targetType;
    }
    //endregion

    //region > targetId (property)
    private Integer target;

    @MemberOrder(sequence = "1")
    public Integer getTarget() {
        return target;
    }

    public void setTarget(final Integer target) {
        this.target = target;
    }
    //endregion

    //region > targetOwnerUri (property)
    private String targetOwnerUri;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerUri() {
        return targetOwnerUri;
    }

    public void setTargetOwnerUri(final String targetOwnerUri) {
        this.targetOwnerUri = targetOwnerUri;
    }
    //endregion

    //region > targetOwner (property)
    private Integer targetOwner;

    @MemberOrder(sequence = "1")
    public Integer getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(final Integer targetOwner) {
        this.targetOwner = targetOwner;
    }
    //endregion

    //region > targetOwnerClassType (property)
    private String targetOwnerClassType;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerClassType() {
        return targetOwnerClassType;
    }

    public void setTargetOwnerClassType(final String targetOwnerClassType) {
        this.targetOwnerClassType = targetOwnerClassType;
    }
    //endregion

    //region > targetOwnerFullName (property)
    private String targetOwnerFullName;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerFullName() {
        return targetOwnerFullName;
    }

    public void setTargetOwnerFullName(final String targetOwnerFullName) {
        this.targetOwnerFullName = targetOwnerFullName;
    }
    //endregion

    //region > targetOwnerImageUrl (property)
    private String targetOwnerImageUrl;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerImageUrl() {
        return targetOwnerImageUrl;
    }

    public void setTargetOwnerImageUrl(final String targetOwnerImageUrl) {
        this.targetOwnerImageUrl = targetOwnerImageUrl;
    }
    //endregion

    //region > assessmentOwnerACtor (property)
    private Integer owner;

    @MemberOrder(sequence = "1")
    public Integer getOwner() {
        return owner;
    }

    public void Integer(final Integer owner) {
        this.owner = owner;
    }
    //endregion

    //region > ownerUri (property)
    private String ownerUri;

    @MemberOrder(sequence = "1")
    public String getOwnerUri() {
        return ownerUri;
    }

    public void setOwnerUri(final String ownerUri) {
        this.ownerUri = ownerUri;
    }
    //endregion

    //region > ownerFullName (property)
    private String ownerFullName;

    @MemberOrder(sequence = "1")
    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(final String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }
    //endregion

    //region > ownerClassType (property)
    private String ownerClassType;

    @MemberOrder(sequence = "1")
    public String getOwnerClassType() {
        return ownerClassType;
    }

    public void setOwnerClassType(final String ownerClassType) {
        this.ownerClassType = ownerClassType;
    }
    //endregion

    //region > ownerImageUrl (property)
    private String ownerImageUrl;

    @MemberOrder(sequence = "1")
    public String getOwnerImageUrl() {
        return ownerImageUrl;
    }

    public void setOwnerImageUrl(final String ownerImageUrl) {
        this.ownerImageUrl = ownerImageUrl;
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

    //region > target (property)
    private String targetUri;

    @MemberOrder(sequence = "1")
    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(final String targetUri) {
        this.targetUri = targetUri;
    }
    //endregion

    //region > feedback (property)
    private String feedback;

    @MemberOrder(sequence = "1")
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }
    //endregion

}
