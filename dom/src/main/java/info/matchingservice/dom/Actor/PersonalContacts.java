package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;


@DomainService(repositoryFor = PersonalContact.class)
@DomainServiceLayout(named="Personal Contacts", menuOrder="13")
public class PersonalContacts extends MatchingDomainService<PersonalContact>{
    
    public PersonalContacts() {
        super(PersonalContacts.class, PersonalContact.class);
    }
    
    @MemberOrder(name = "Personen", sequence = "20")
    public List<PersonalContact> allYourContacts(){
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContact", 
                    "ownedBy", currentUserName());
        return allMatches(query);
    }
    
    @MemberOrder(name = "Personen", sequence = "10")
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public PersonalContact newPersonalContact(
            @ParameterLayout(named="contactPerson")
            final Person contactPerson) {
        return newPersonalContact(contactPerson, currentUserName()); // see region>helpers
    }
    
    public List<Person> autoComplete0NewPersonalContact(final String search) {
        return persons.findPersonsContains(search);
    }
    
    public boolean hideNewPersonalContact(final Person contactPerson){
        // show in service menu
        if (contactPerson == null) {
            return false;
        }
        // do not show on own personinstance - you cannot add yourself as personal contact
        if (contactPerson.getOwnedBy().equals(currentUserName())) {
            return true;
        }
        // do not show when already contacted
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson.getOwnedBy());
        return container.firstMatch(query) != null?
        true  : false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPersonalContact(final Person contactPerson) {
        
        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "Contact maken met jezelf heeft geen zin.";
        }
        
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson.getOwnedBy());
        return container.firstMatch(query) != null?
        "Dit contact heb je al gemaakt. Pas het eventueel aan in plaats van een nieuwe te maken."        
        :null;
    }
    
    @NotInServiceMenu
    @CollectionLayout(named="Alle persoonlijke contacten")
    public List<PersonalContact> listAll() {
        return container.allInstances(PersonalContact.class);
    }
    
    // Region>helpers //////////////////////////// 
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public PersonalContact newPersonalContact(
            final Person contactPerson,
            final String userName) {
        final PersonalContact contact = newTransientInstance(PersonalContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnerPerson(persons.findPersonUnique(userName));
        contact.setOwnedBy(userName);
        persist(contact);
        return contact;
    }

    @Programmatic //userName and trustLevel can now also be set by fixtures
    public PersonalContact newPersonalContact(
            final Person contactPerson,
            final String userName,
            final TrustLevel trustLevel) {
        final PersonalContact contact = newTransientInstance(PersonalContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnerPerson(persons.findPersonUnique(userName));
        contact.setOwnedBy(userName);
        contact.setTrustLevel(trustLevel);
        persist(contact);
        return contact;
    }
    
    // Region>injections ////////////////////////////  
    
    @javax.inject.Inject
    private DomainObjectContainer container;
     
    @Inject
    Persons persons;
    
}