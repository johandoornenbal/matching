package info.matchingservice.dom.Party;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Utils.StringUtils;

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


@DomainService(menuOrder = "10", repositoryFor = Person.class)
@Named("Personen")
public class Persons extends MatchingDomainService<Person> {
    
    public Persons() {
        super(Persons.class, Person.class);
    }
    
    @ActionSemantics(Of.NON_IDEMPOTENT)
    public Person newPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName
            ) {
        return newPerson(uniquePartyId, firstName, lastName, middleName, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewPerson() {
        return hideNewPerson(currentUserName());
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
        return validateNewPerson(uniquePartyId, firstName, middleName, lastName, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    public List<Person> thisIsYou() {
        return thisIsYou(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    public List<Person> allPersons() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @NotContributed(As.ACTION)
    @Render(Type.EAGERLY)
    @Named("Alle andere personen")
    public List<Person> AllOtherPersons(final Person personMe) {
        final List<Person> allPersons = allPersons();
        return Lists.newArrayList(Iterables.filter(allPersons, excluding(personMe)));
    }

    private static Predicate<Person> excluding(final Person person) {
        return new Predicate<Person>() {
            @Override
            public boolean apply(Person input) {
                return input != person;
            }
        };
    }
    //endregion
    
   
    
    @MemberOrder(sequence="100")
    public List<Person> findPersons(final String lastname) {
        return allMatches("matchPersonByLastName", "lastName", StringUtils.wildcardToCaseInsensitiveRegex(lastname));
    }
    
    @MemberOrder(sequence="105")
    public List<Person> findPersonsContains(final String lastname) {
        return allMatches("matchPersonByLastNameContains", "lastName", lastname);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Person newPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName,
            final String userName) {
        final Person person = newTransientInstance(Person.class);
        person.setUniquePartyId(uniquePartyId);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setOwnedBy(userName);
        persist(person);
        return person;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public boolean hideNewPerson(String userName) {
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        true        
        :false;        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewPerson(
            final @Named("Uniek ID") String uniquePartyId,
            final @Named("Voornaam") String firstName,
            final @Optional  @Named("tussen") String middleName,
            final @Named("Achternaam") String lastName,
            final String userName) {
        
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        "Je hebt jezelf al aangemaakt. Pas je gegevens eventueel aan in plaats van hier een nieuwe te maken."        
        :null;
        
    }
    
    @Programmatic //userName can now also be set by fixtures
    public List<Person> thisIsYou(final String userName) {
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", userName);          
        return allMatches(query);
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
