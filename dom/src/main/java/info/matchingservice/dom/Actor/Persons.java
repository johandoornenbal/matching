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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.MatchingDomainService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.value.Blob;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


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
            final Blob picture,
            @ParameterLayout(named="pictureLink")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String pictureLink,
            final PersonRoleType personRoleType
            ) {
        return createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, personRoleType, currentUserName()); // see region>helpers
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
            final Blob picture,
            final String pictureLink,
            final PersonRoleType personRoleType) {
        return validateCreatePerson(firstName, middleName, lastName, dateOfBirth, currentUserName(), picture, pictureLink);
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

    @Programmatic
    public List<Person> findPersonsByName(
            final String search
    ) {

        return allMatches("matchPersonByNameContains", "search", search.toLowerCase());
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
            final String pictureLink,
            final PersonRoleType personRoleType,
            final String userName) {
        final Person person = newTransientInstance(Person.class);
        final UUID uuid=UUID.randomUUID();
        person.setUniqueItemId(uuid);
        person.setFirstName(firstName);
        person.setMiddleName(middleName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
        person.setDateCreated(clockService.now());
        person.setOwnedBy(userName);
        person.setPicture(picture);
        person.setPictureLink(pictureLink);
        if (personRoleType!=null) {
            if (personRoleType.equals(PersonRoleType.STUDENT)) {
                person.addRoleStudent();
            }
            if (personRoleType.equals(PersonRoleType.PROFESSIONAL)) {
                person.addRoleProfessional();
            }
            if (personRoleType.equals(PersonRoleType.PRINCIPAL)) {
                person.addRolePrincipal();
            }
        }
        person.setActivated(false);
        persist(person);

        // create first emailAddress contributed to Person copied from securityModule
        if (applicationUsers.findUserByUsername(userName) != null) {
            final String emailAddress = applicationUsers.findUserByUsername(userName).getEmailAddress();
            communicationChannels.createEmail(emailAddress, CommunicationChannelType.EMAIL_MAIN, person);
        }

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
            final Blob picture,
            final String pictureLink) {
        
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
    		final Blob picture,
            final String pictureLink
    		){
    	person.setFirstName(firstName);   	
    	person.setMiddleName(middleName);
    	person.setLastName(lastName);
    	person.setDateOfBirth(dateOfBirth);
//    	if (picture != null){
    		person.setPicture(picture);
//    	}
        person.setPictureLink(pictureLink);
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

    @Programmatic
    // for Api
    public void deletePerson(String ownedBy) {
        QueryDefault<Person> query =
                QueryDefault.create(
                        Person.class,
                        "findPersonUnique",
                        "ownedBy", ownedBy);
        Person personToDelete = allMatches(query).get(0);
        List<CommunicationChannel> channels = communicationChannels.findCommunicationChannelByPerson(personToDelete);
        for (CommunicationChannel channel : channels){
            channel.deleteCommunicationChannel(true);
        }
        container.removeIfNotAlready(personToDelete);
    }

    @Programmatic
    // for Api
    public String activatePerson(String ownedBy){
        QueryDefault<Person> query =
                QueryDefault.create(
                        Person.class,
                        "findPersonUnique",
                        "ownedBy", ownedBy);
        if (allMatches(query).size()>0) {
            Person personToActivate = allMatches(query).get(0);
            personToActivate.setActivated(true);
            return "ACTIVATED";
        }
        return "NOT_ACTIVATED";
    }

    @Programmatic
    // for Api
    public String deActivatePerson(String ownedBy){
        QueryDefault<Person> query =
                QueryDefault.create(
                        Person.class,
                        "findPersonUnique",
                        "ownedBy", ownedBy);
        if (allMatches(query).size()>0) {
            Person personToDeActivate = allMatches(query).get(0);
            personToDeActivate.setActivated(false);
            return "DEACTIVATED";

        }
        return "NOT_DEACTIVATED";
    }

    // Api v1
    @Programmatic
    public Person matchPersonApiId(final String id) {

        for (Person p : container.allInstances(Person.class)) {
            String[] parts = p.getOID().split(Pattern.quote("[OID]"));
            String part1 = parts[0];
            String ApiId = "L_".concat(part1);
            if (id.equals(ApiId)) {
                return p;
            }
        }

        return null;

    }

    public List<Person> autoComplete(String search) {
        return findPersons(search);
    }

    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    private CommunicationChannels communicationChannels;

    @Inject
    private ApplicationUsers applicationUsers;

    @Inject
    private ClockService clockService;


}
