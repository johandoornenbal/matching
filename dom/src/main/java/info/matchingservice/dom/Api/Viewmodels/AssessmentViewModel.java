package info.matchingservice.dom.Api.Viewmodels;

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
        this.targetOwnerActor = assessment.getTargetOwnerActor().getUri();
        this.assessmentOwnerActor = assessment.getAssessmentOwnerActor().getUri();
        this.assessmentDescription = assessment.getAssessmentDescription();
    }

    public AssessmentViewModel(final DemandAssessment assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
    }

    public AssessmentViewModel(final SupplyAssessment assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
    }

    public AssessmentViewModel(final ProfileAssessment assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
    }

    public AssessmentViewModel(final ProfileFeedback assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final DemandFeedback assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
        this.feedback = assessment.getFeedback();
    }

    public AssessmentViewModel(final SupplyFeedback assessment){
        this((Assessment) assessment);
        this.targetOfAssessment = assessment.getTargetOfAssessment().getUri();
        this.feedback = assessment.getFeedback();
    }

    //region > targetOwnerActor (property)
    private String targetOwnerActor;

    @MemberOrder(sequence = "1")
    public String getTargetOwnerActor() {
        return targetOwnerActor;
    }

    public void setTargetOwnerActor(final String targetOwnerActor) {
        this.targetOwnerActor = targetOwnerActor;
    }
    //endregion

    //region > assessmentOwnerACtor (property)
    private String assessmentOwnerActor;

    @MemberOrder(sequence = "1")
    public String getAssessmentOwnerActor() {
        return assessmentOwnerActor;
    }

    public void setAssessmentOwnerActor(final String assessmentOwnerActor) {
        this.assessmentOwnerActor = assessmentOwnerActor;
    }
    //endregion

    //region > assessmentDescription (property)
    private String assessmentDescription;

    @MemberOrder(sequence = "1")
    public String getAssessmentDescription() {
        return assessmentDescription;
    }

    public void setAssessmentDescription(final String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }
    //endregion

    //region > targetOfAssessment (property)
    private String targetOfAssessment;

    @MemberOrder(sequence = "1")
    public String getTargetOfAssessment() {
        return targetOfAssessment;
    }

    public void setTargetOfAssessment(final String targetOfAssessment) {
        this.targetOfAssessment = targetOfAssessment;
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
