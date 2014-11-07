package nl.yodo.dom.Party;

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import nl.yodo.dom.TrustLevel;
import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "13", repositoryFor = YodoPersonalContact.class)
@Named("Pruts Personal Contacts")
public class YodoPersonalContacts extends YodoDomainService<YodoPersonalContact> {
    
    public YodoPersonalContacts() {
        super(YodoPersonalContacts.class, YodoPersonalContact.class);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "20")
    public List<YodoPersonalContact> allYourContacts(){
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContact", 
                    "ownedBy", currentUserName());
        return allMatches(query);
    }
    
    @MemberOrder(name = "Pruts Personen", sequence = "10")
    @ActionSemantics(Of.NON_IDEMPOTENT)
    @Named("Personal contact maken")
    public YodoPersonalContact newPersonalContact(
            final @Named("Contact") YodoPerson contactPerson) {
        return newPersonalContact(contactPerson, currentUserName()); // see region>helpers
    }
    
    public List<YodoPerson> autoComplete0NewPersonalContact(final String search) {
        return yodopersons.findYodoPersons(search);
    }
    
    public boolean hideNewPersonalContact(final @Named("Contact") YodoPerson contactPerson){
        // show in service menu
        if (contactPerson == null) {
            return false;
        }
        // do not show on own personinstance - you cannot add yourself as personal contact
        if (contactPerson.getOwnedBy().equals(currentUserName())) {
            return true;
        }
        // do not show when already contacted
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson.getOwnedBy());
        return container.firstMatch(query) != null?
        true  : false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPersonalContact(final YodoPerson contactPerson) {
        
        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "Contact maken met jezelf heeft geen zin.";
        }
        
        QueryDefault<YodoPersonalContact> query = 
                QueryDefault.create(
                        YodoPersonalContact.class, 
                    "findYodoPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson.getOwnedBy());
        return container.firstMatch(query) != null?
        "Dit contact heb je al gemaakt. Pas het eventueel aan in plaats van een nieuwe te maken."        
        :null;
    }
    
    // Region>helpers //////////////////////////// 
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public YodoPersonalContact newPersonalContact(
            final @Named("Contact") YodoPerson contactPerson,
            final String userName) {
        final YodoPersonalContact contact = newTransientInstance(YodoPersonalContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnedBy(userName);
        persist(contact);
        return contact;
    }

    @Programmatic //userName and trustLevel can now also be set by fixtures
    public YodoPersonalContact newPersonalContact(
            final @Named("Contact") YodoPerson contactPerson,
            final String userName,
            final TrustLevel trustLevel) {
        final YodoPersonalContact contact = newTransientInstance(YodoPersonalContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnedBy(userName);
        contact.setLevel(trustLevel);
        persist(contact);
        return contact;
    }
    
    // Region>injections ////////////////////////////  
    
    @javax.inject.Inject
    private DomainObjectContainer container;
     
    @Inject
    YodoPersons yodopersons;
}
