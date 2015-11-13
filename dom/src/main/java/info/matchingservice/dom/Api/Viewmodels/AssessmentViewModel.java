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
        this.targetOwner = assessment.getTargetOwnerActor().getUri();
        this.targetOwnerFullName = assessment.getTargetOwnerActor().title();
        this.targetOwnerId = assessment.getTargetOwnerActor().getIdAsInt();
        this.targetOwnerType = assessment.getTargetOwnerActor().getClass().getSimpleName();
        Person targetOwner = (Person) assessment.getTargetOwnerActor();
        this.targetOwnerImageUrl = targetOwner.getImageUrl();
        this.owner = assessment.getAssessmentOwnerActor().getUri();
        this.ownerId = assessment.getAssessmentOwnerActor().getIdAsInt();
        this.ownerFullName = assessment.getAssessmentOwnerActor().title();
        this.ownerType = assessment.getAssessmentOwnerActor().getClass().getSimpleName();
        Person assessmentOwner = (Person) assessment.getAssessmentOwnerActor();
        this.ownerImageUrl = assessmentOwner.getImageUrl();
        this.description = assessment.getDescription();
        this.type =  assessment.getClass().getSimpleName();
    }

    public AssessmentViewModel(final DemandAssessment assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final SupplyAssessment assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileAssessment assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileMatchAssessment assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
    }

    public AssessmentViewModel(final ProfileFeedback assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final DemandFeedback assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final SupplyFeedback assessment){
        this((Assessment) assessment);
        this.target = assessment.getTargetOfAssessment().getUri();
        this.targetType = assessment.getTargetOfAssessment().getClass().getSimpleName();
        this.targetId = assessment.getTargetOfAssessment().getIdAsInt();
        this.feedback = assessment.getFeedback();
    }

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
    private Integer targetId;

    @MemberOrder(sequence = "1")
    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(final Integer targetId) {
        this.targetId = targetId;
    }
    //endregion

    //region > targetOwner (property)
    private String targetOwner;

    @MemberOrder(sequence = "1")
    public String getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(final String targetOwner) {
        this.targetOwner = targetOwner;
    }
    //endregion

    //region > targetOwnerId (property)
    private Integer targetOwnerId;

    @MemberOrder(sequence = "1")
    public Integer getTargetOwnerId() {
        return targetOwnerId;
    }

    public void setTargetOwnerId(final Integer targetOwnerId) {
        this.targetOwnerId = targetOwnerId;
    }
    //endregion

    //region > targetOwnerType (property)
    private String targetOwnerType;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerType() {
        return targetOwnerType;
    }

    public void setTargetOwnerType(final String targetOwnerType) {
        this.targetOwnerType = targetOwnerType;
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
    private String owner;

    @MemberOrder(sequence = "1")
    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }
    //endregion

    //region > ownerId (property)
    private Integer ownerId;

    @MemberOrder(sequence = "1")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(final Integer ownerId) {
        this.ownerId = ownerId;
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

    //region > ownerType (property)
    private String ownerType;

    @MemberOrder(sequence = "1")
    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(final String ownerType) {
        this.ownerType = ownerType;
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
    private String target;

    @MemberOrder(sequence = "1")
    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
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
