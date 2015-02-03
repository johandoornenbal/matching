/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Utils.StringUtils;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NotContributed;
import org.apache.isis.applib.annotation.NotContributed.As;
import org.apache.isis.applib.annotation.NotInServiceMenu;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;


@DomainService(repositoryFor = Person.class)
@DomainServiceLayout(named="Personen", menuOrder="10")
public class Persons extends MatchingDomainService<Person> {
    
    public Persons() {
        super(Persons.class, Person.class);
    }
   
    @ActionLayout(named="Maak jezelf aan als persoon in het systeem")
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Person newPerson(
            @ParameterLayout(named="firstName")
            final String firstName,
            @ParameterLayout(named="middleName")
            @Parameter(optional=Optionality.TRUE)
            final String middleName,
            @ParameterLayout(named="lastName")
            final String lastName,
            @ParameterLayout(named="dateOfBirth")
            final LocalDate dateOfBirth
            ) {
        return newPerson(firstName, middleName, lastName, dateOfBirth, currentUserName()); // see region>helpers
    }
    
    public boolean hideNewPerson() {
        return hideNewPerson(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateNewPerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth) {
        return validateNewPerson(firstName, middleName, lastName, dateOfBirth, currentUserName());
    }
    
    @MemberOrder(sequence="5")
    @ActionLayout(named="Dit ben jij ...")
    public List<Person> thisIsYou() {
        return thisIsYou(currentUserName());
    }
    
    @MemberOrder(sequence="10")
    @ActionLayout(named="Alle personen")
    public List<Person> allPersons() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @NotInServiceMenu
    @NotContributed(As.ACTION)
    @ActionLayout(named="Alle andere personen")
    @Action(semantics=SemanticsOf.SAFE)
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
    @ActionLayout(named="Vind op achternaam")
    public List<Person> findPersons(
            @ParameterLayout(named="lastName")
            final String lastname
            ) {
        return allMatches("matchPersonByLastName", "lastName", StringUtils.wildcardToCaseInsensitiveRegex(lastname));
    }
    
    @MemberOrder(sequence="105")
    @ActionLayout(named="Vind op overeenkomst achternaam")
    public List<Person> findPersonsContains(
            @ParameterLayout(named="lastName")
            final String lastname
            ) {
        return allMatches("matchPersonByLastNameContains", "lastName", lastname);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Person newPerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final String userName) {
        final Person person = newTransientInstance(Person.class);
        final UUID uuid=UUID.randomUUID();
        person.setUniqueItemId(uuid);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
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
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
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
    
    @Programmatic
    public Person EditPerson(
    		Person person,
    		String firstName,
    		String middleName,
    		String lastName,
    		LocalDate dateOfBirth
    		){
    	person.setFirstName(firstName);
    	person.setMiddleName(middleName);
    	person.setLastName(lastName);
    	person.setDateOfBirth(dateOfBirth);
    	persistIfNotAlready(person);
    	return person;
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
    
    @Programmatic
    public Person findPersonUnique(String ownedBy){
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", ownedBy);          
        return allMatches(query).get(0);
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
