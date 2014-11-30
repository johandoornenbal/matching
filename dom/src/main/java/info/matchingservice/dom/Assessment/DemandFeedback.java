package info.matchingservice.dom.Assessment;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.MultiLine;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.SUPERCLASS_TABLE)
public class DemandFeedback extends DemandAssessment {
    
    private String feedback;
    
    @MultiLine
    @javax.jdo.annotations.Column(allowsNull = "true")
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(final String feedback) {
        this.feedback = feedback;
    }

}
