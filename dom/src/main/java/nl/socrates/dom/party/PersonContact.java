package nl.socrates.dom.party;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.AbstractDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.EventBusService;

import nl.yodo.dom.TrustLevel;
import nl.socrates.dom.feedback.FeedbackItem;
import nl.socrates.dom.feedback.FeedbackItems;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findPersonContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.PersonContact "
                    + "WHERE ownerPerson == :owner"),
    @javax.jdo.annotations.Query(
            name = "findReferringContact", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.PersonContact "
                    + "WHERE contact == :contact"),
    @javax.jdo.annotations.Query(
            name = "findConfirmedContacts", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.PersonContact "
                    + "WHERE (ownerPerson == :ownerPerson || contact == :ownerPerson) && status == :status"),
    @javax.jdo.annotations.Query(
            name = "findReferringContactUserName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.PersonContact "
                    + "WHERE ownerPerson == :ownerPerson && contactUserName == :contactUserName"),
    @javax.jdo.annotations.Query(
            name = "findReferringOwnerContactUserName", language = "JDOQL",
            value = "SELECT "
                    + "FROM nl.socrates.dom.party.PersonContact "
                    + "WHERE owner == :owner && contactUserName == :contactUserName")
    })
public class PersonContact extends AbstractDomainObject implements Comparable<PersonContact> {
    
    private String owner;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOwner() {
        return owner;
    }
    
    public void setOwner(final String owner) {
        this.owner=owner;
    }
    
    private Person ownerPerson;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "50")
    @Named("Eigenaar")
    @Hidden(where = Where.PARENTED_TABLES)
    @Disabled
    public Person getOwnerPerson() {
        return ownerPerson;
    }
    
    public void setOwnerPerson (final Person owner) {
        this.ownerPerson = owner;
    }
    
    private Person contact;
   
    @Title
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "10")
    @Named("Contact")
    @Disabled
    public Person getContact() {
        return contact;
    }
    
    public void setContact(final Person contact) {
        this.contact = contact;
    }
    
    public List<Person> autoCompleteContact(String search) {
        return persons.findPersons(search);
    }
    
    private String contactUserName;
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getContactUserName() {
        return contactUserName;
    }
    
    public void setContactUserName(final String username) {
        this.contactUserName=username;
    }
    
    private TrustLevel level;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "30")
    @Named("Vertrouwens niveau")
    public TrustLevel getLevel() {
        return level;
    }
    
    public void setLevel(final TrustLevel level) {
        this.level = level;
    }
    
    public TrustLevel defaultLevel() {
        return TrustLevel.ENTRY_LEVEL;
    }
    
    private PersonContactStatus status;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Status")
//    @Disabled
    public PersonContactStatus getStatus() {
        return status;
    }
    
    public void setStatus(final PersonContactStatus status){
        this.status = status;
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
    
    @Programmatic
    public void publishTestEvent() {
        eventBusService.post(new PersonContactEvent("Notificatie opgenomen bij ", this.getOwnerPerson(), this.getContact()));
    }
    
    @Named("Dit contact verwijderen")
    public List<PersonContact> delete(@Optional @Named("Verwijderen OK?") boolean areYouSure) { 
        container.removeIfNotAlready(this);
        
        container.informUser("Contact verwijderd");
        
        QueryDefault<PersonContact> query = 
                QueryDefault.create(
                        PersonContact.class, 
                    "findPersonContact", 
                    "owner", getOwnerPerson());
        
        return (List<PersonContact>) container.allMatches(query);   
    }
    public String validateDelete(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }

    private SortedSet<FeedbackItem> feedback = new TreeSet<FeedbackItem>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "personcontact", dependentElement = "true")
    @Named("Gegeven feedback aan dit contact")
    public SortedSet<FeedbackItem> getFeedback(){
        return feedback;
    }
    
    public void setFeedback(final SortedSet<FeedbackItem> feedback) {
        this.feedback = feedback;
    }
    
    @Named("Dit contact feedback geven")
    public List<FeedbackItem> addFeedback(String testfeedback){
        fbItems.createFeedbackItem(this.getOwnerPerson(), this.getContact(), testfeedback, this);
        QueryDefault<FeedbackItem> query = 
                QueryDefault.create(
                        FeedbackItem.class, 
                    "findFeedbackByOwnerAndReceiver", 
                    "owner", getOwnerPerson(),
                    "receiver" , getContact());
        return (List<FeedbackItem>) container.allMatches(query);           
    }
    
    @Override
    public int compareTo(PersonContact o) {
        return ComparisonChain.start()
                .compare(this.getOwnerPerson(), o.getOwnerPerson())
                .compare(this.getContact(), o.getContact())
                .compare(this.getCreatedOn(), o.getCreatedOn())
                .compare(this.getLevel(), o.getLevel())
                .result();
    }
    
    @Inject
    Persons persons;
    
    @Inject
    FeedbackItems fbItems;
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    private EventBusService eventBusService;
    public void injectEventBusService(final EventBusService eventBusService) {
        this.eventBusService = eventBusService;
        eventBusService.register(this);
    }

}
