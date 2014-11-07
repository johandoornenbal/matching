package nl.socrates.dom.feedback;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import nl.socrates.dom.SocratesMutableObject;
import nl.socrates.dom.Titled;
import nl.socrates.dom.party.Party;
import nl.socrates.dom.party.PersonContact;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findFeedbackByOwnerAndReceiver", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.feedback.FeedbackItem "
                    + "WHERE owner == :owner && feedbackReceiver == :receiver")                   
    })
public class FeedbackItem extends SocratesMutableObject<FeedbackItem> implements Comparable<FeedbackItem>, Titled{
    
    public FeedbackItem() {
        super("owner");
    }
    
    public String title(){
        return "Feedback aan " + this.getFeedbackReceiver().getName();
    }

    
    private Party owner;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Feedback van")
    @Hidden(where = Where.PARENTED_TABLES)
    public Party getOwner() {
        return owner;
    }
    
    public void setOwner(final Party owner) {
        this.owner = owner;
    }
    
    private Party feedbackReceiver;
    
    @Disabled
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Feedback aan")
    public Party getFeedbackReceiver() {
        return feedbackReceiver;
    }
    
    public void setFeedbackReceiver(final Party subject) {
        this.feedbackReceiver = subject;
    }
    
    private PersonContact personcontact;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Persoonlijk contact")
    @Hidden(where = Where.PARENTED_TABLES)
    public PersonContact getPersoncontact() {
        return personcontact;
    }
    
    public void setPersoncontact(final PersonContact personcontact) {
        this.personcontact = personcontact;
    }
    
    private String testfeedback;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getTestfeedback() {
        return testfeedback;
    }
    
    public void setTestfeedback(final String testfeedback) {
        this.testfeedback = testfeedback;
    }

    private LocalDateTime createdOn;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Persistent
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime created) {
        this.createdOn = created;
    }
    
    @MemberOrder(sequence = "20")
    @Named("gemaakt op")
    public LocalDate getDateCreatedOn() {
        return getCreatedOn().toLocalDate();
    }    

    @Named("Feedback verwijderen")
    public List<FeedbackItem> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Feedback verwijderd");
        
        QueryDefault<FeedbackItem> query = 
                QueryDefault.create(
                        FeedbackItem.class, 
                    "findFeedbackByOwnerAndReceiver", 
                    "owner", getPersoncontact().getOwnerPerson(),
                    "receiver" , getPersoncontact().getContact());
        
        return (List<FeedbackItem>) container.allMatches(query);   
    }    

    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Zeker weten? Zet dan even een vinkje ...";
    }    
    
    @Override
    public int compareTo(FeedbackItem fbi) {
        return ComparisonChain.start()
                .compare(this.getPersoncontact(), fbi.getPersoncontact())
                .compare(this.getCreatedOn(), fbi.getCreatedOn())
                .result();
    }
    
    @javax.inject.Inject
    private DomainObjectContainer container;
}
