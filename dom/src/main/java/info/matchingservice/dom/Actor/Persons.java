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

import java.util.List;
import java.util.UUID;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;


@DomainService(repositoryFor = Person.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder="10")
public class Persons extends MatchingDomainService<Person> {
    
    public Persons() {
        super(Persons.class, Person.class);
    }
   
    @ActionLayout()
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public Person createPerson(
            @ParameterLayout(named="firstName")
            final String firstName,
            @ParameterLayout(named="middleName")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String middleName,
            @ParameterLayout(named="lastName")
            final String lastName,
            @ParameterLayout(named="dateOfBirth")
            final LocalDate dateOfBirth,
            @ParameterLayout(named="picture")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Blob picture
            ) {
        return createPerson(firstName, middleName, lastName, dateOfBirth, picture, currentUserName()); // see region>helpers
    }
    
    public boolean hideCreatePerson() {
        return hideCreatePerson(currentUserName());
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateCreatePerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final Blob picture) {
        return validateCreatePerson(firstName, middleName, lastName, dateOfBirth, currentUserName(), picture);
    }
    
    @MemberOrder(sequence="5")
    @ActionLayout(hidden=Where.ANYWHERE)
    public List<Person> activePerson() {
        return activePerson(currentUserName());
    }
    
    @Programmatic
    public List<Person> allPersons() {
        return allInstances();
    }
    
    
    //region > otherPersons (contributed collection)
    @MemberOrder(sequence="110")
    @ActionLayout(hidden=Where.ANYWHERE, contributed=Contributed.AS_ASSOCIATION)
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
    
    
    @MemberOrder(sequence="105")
    @ActionLayout(hidden=Where.ANYWHERE)
    public List<Person> findPersons(
            @ParameterLayout(named="searchInLastName")
            final String lastName
            ) {
    	
        return allMatches("matchPersonByLastNameContains", "lastName", lastName.toLowerCase());
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public Person createPerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final Blob picture,
            final String userName) {
        final Person person = newTransientInstance(Person.class);
        final UUID uuid=UUID.randomUUID();
        person.setUniqueItemId(uuid);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
        person.setOwnedBy(userName);
        person.setPicture(picture);
        person.addRoleStudent();
        persist(person);
        return person;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public boolean hideCreatePerson(String userName) {
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
    public String validateCreatePerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final String userName,
            final Blob picture) {
        
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", userName);        
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
        
    }
    
    @Programmatic
    public Person updatePerson(
    		final Person person,
    		final String firstName,
    		final String middleName,
    		final String lastName,
    		final LocalDate dateOfBirth,
    		final Blob picture
    		){
    	person.setFirstName(firstName);   	
    	person.setMiddleName(middleName);
    	person.setLastName(lastName);
    	person.setDateOfBirth(dateOfBirth);
//    	if (picture != null){
    		person.setPicture(picture);
//    	}
    	persistIfNotAlready(person);
    	return person;
    }
    
    @Programmatic //userName can now also be set by fixtures
    public List<Person> activePerson(final String userName) {
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
        if (allMatches(query).isEmpty()){
        	return null;
        } else {
        	return allMatches(query).get(0);
        }
    }
    
    @Programmatic
    // for Api
    public List<Person> findPersonByUniqueItemId(UUID uniqueItemId) {
        return allMatches("findPersonByUniqueItemId",
        		"uniqueItemId", uniqueItemId);
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
