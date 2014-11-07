package nl.socrates.dom.feedback;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.services.clock.ClockService;

import nl.socrates.dom.SocratesDomainService;
import nl.socrates.dom.party.SocPerson;
import nl.socrates.dom.party.PersonContact;
import nl.socrates.dom.party.SocPersons;

@DomainService(menuOrder = "40", repositoryFor = FeedbackItem.class)
public class FeedbackItems extends SocratesDomainService<FeedbackItem> {
    
    public FeedbackItems() {
        super(FeedbackItems.class, FeedbackItem.class);
    }

    @MemberOrder(name = "Feedback", sequence = "1")
    @Named("Geef feedback")
    @NotInServiceMenu
    @Hidden
    public FeedbackItem createFeedbackItem(
            final SocPerson owner,
            final SocPerson receiver,
            final String testfeedback,
            final PersonContact personcontact){
        final FeedbackItem feedback = container.newTransientInstance(FeedbackItem.class);
        feedback.setOwner(owner);
        feedback.setFeedbackReceiver(receiver);
        feedback.setTestfeedback(testfeedback);
        feedback.setPersoncontact(personcontact);
        feedback.setCreatedOn(clockService.nowAsLocalDateTime());
        container.persistIfNotAlready(feedback);
        return feedback;
    }
    
    public List<SocPerson> autoComplete1CreateFeedbackItem(final String search) {
        return persons.findPersons(search);
    }
    
    @Named("Alle feedback items")
//    @NotInServiceMenu
    public List<FeedbackItem> listAll() {
        return container.allInstances(FeedbackItem.class);
    }    
    
    @javax.inject.Inject 
    DomainObjectContainer container;
    
    @Inject
    SocPersons persons;
    
    @Inject
    private ClockService clockService;
}
