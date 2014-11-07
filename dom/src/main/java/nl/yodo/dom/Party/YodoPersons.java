package nl.yodo.dom.Party;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;

import nl.socrates.dom.utils.StringUtils;
import nl.yodo.dom.YodoDomainService;

@DomainService(menuOrder = "10", repositoryFor = YodoPerson.class)
@Named("Pruts Personen")
public class YodoPersons extends YodoDomainService<YodoPerson> {
    
    public YodoPersons() {
        super(YodoPersons.class, YodoPerson.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public YodoPerson newPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName) {
        return newPerson(uniquePartyId, firstName, lastName, middleName, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewPerson() {
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "ownedBy", currentUserName());        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName) {
        
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "ownedBy", currentUserName());        
        return container.firstMatch(query) != null?
        "Je hebt jezelf al aangemaakt. Pas je gegevens eventueel aan in plaats van hier een nieuwe te maken."        
        :null;
        
    }
    
    @MemberOrder(sequence="5")
    public List<YodoPerson> thisIsYou() {
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "ownedBy", currentUserName());          
        return allMatches(query);
    }
    
    @MemberOrder(sequence="10")
    public List<YodoPerson> allPersons() {
        return allInstances();
    }
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Alle andere personen")
    public List<YodoPerson> AllOtherPersons(final YodoPerson personMe) {
        final List<YodoPerson> allPersons = allPersons();
        return Lists.newArrayList(Iterables.filter(allPersons, excluding(personMe)));
    }

    private static Predicate<YodoPerson> excluding(final YodoPerson person) {
        return new Predicate<YodoPerson>() {
            @Override
            public boolean apply(YodoPerson input) {
                return input != person;
            }
        };
    }
    //endregion
    
//    public List<YodoPerson> personsReferringToMe1() {
//        QueryDefault<YodoPersonalContact> query =
//                QueryDefault.create(
//                        YodoPersonalContact.class, 
//                        "findYodoPersonalContactReferringToMe", 
//                        "contact", currentUserName());
//        List<YodoPerson> tempList = new ArrayList<YodoPerson>();
//        for (YodoPersonalContact e: container.allMatches(query)) {
//            QueryDefault<YodoPerson> q =
//                    QueryDefault.create(
//                            YodoPerson.class, 
//                            "findYodoPersonUnique", 
//                            "ownedBy", e.getOwnedBy());
//            tempList.add(container.firstMatch(q));
//        }
//        return tempList;
//    }
 
    @MemberOrder(sequence="120")
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Personen die naar mij verwijzen")
    public List<Referral> personsReferringToMe(final YodoPerson personMe) {
        QueryDefault<YodoPersonalContact> query =
                QueryDefault.create(
                        YodoPersonalContact.class, 
                        "findYodoPersonalContactReferringToMe", 
                        "contact", currentUserName());
        List<Referral> tempList = new ArrayList<Referral>();
        for (YodoPersonalContact e: container.allMatches(query)) {
            QueryDefault<YodoPerson> q =
                    QueryDefault.create(
                            YodoPerson.class, 
                            "findYodoPersonUnique", 
                            "ownedBy", e.getOwnedBy());
            final Referral ref = new Referral(container.firstMatch(q), e.getLevel());
            tempList.add(ref);
        }
        return tempList;
    }  
    
    public boolean hidePersonsReferringToMe(final YodoPerson personMe) {
        QueryDefault<YodoPerson> query = 
                QueryDefault.create(
                        YodoPerson.class, 
                    "findYodoPersonUnique", 
                    "ownedBy", currentUserName());        
        return container.firstMatch(query).equals(personMe)?
        false        
        :true;        
    }
    
    
    @MemberOrder(sequence="100")
    public List<YodoPerson> findYodoPersons(final String lastname) {
        return allMatches("matchPersonByLastName", "lastName", StringUtils.wildcardToCaseInsensitiveRegex(lastname));
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public YodoPerson newPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName,
            final String userName) {
        final YodoPerson person = newTransientInstance(YodoPerson.class);
        person.setUniquePartyId(uniquePartyId);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setOwnedBy(userName);
        persist(person);
        return person;
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
