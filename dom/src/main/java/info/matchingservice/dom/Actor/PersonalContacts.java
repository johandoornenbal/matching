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
import info.matchingservice.dom.TrustLevel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;

import com.google.common.base.Objects;

@DomainService(repositoryFor = PersonalContact.class, nature=NatureOfService.DOMAIN)
@DomainServiceLayout(menuOrder="13")
public class PersonalContacts extends MatchingDomainService<PersonalContact>{
    
    public PersonalContacts() {
        super(PersonalContacts.class, PersonalContact.class);
    }
    
    //** HELPERS **//
    //** HELPERS: programmatic actions **//
    @Programmatic
    public List<PersonalContact> listAll() {
        return container.allInstances(PersonalContact.class);
    }
    
    @Programmatic
    public List<PersonalContact> allPersonalContactsOfUser(){
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContact", 
                    "ownedBy", currentUserName());
        return allMatches(query);
    }
    
    @Programmatic
    public List<PersonalContact> allPersonalContactsReferringToUser(final String userName){
    	final List<PersonalContact> contacts = Collections.emptyList();
    	// find PersonObject of user
    	// if not found return emptyList
    	if (persons.findPersonUnique(userName) == null ){
    		return contacts;
    	} else {
            QueryDefault<PersonalContact> query = 
                    QueryDefault.create(
                            PersonalContact.class, 
                        "findPersonalContactReferringToPerson", 
                        "contact", persons.findPersonUnique(userName).getOwnedBy());
            return allMatches(query);
    	}
    }
    
    @Programmatic //userName can now also be set by fixtures
    public PersonalContact createPersonalContact(
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
    public PersonalContact createPersonalContact(
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
    //-- HELPERS: programmatic actions --//
    //** HELPERS: generic service helpers **//
    private String currentUserName() {
        return container.getUser().getName();
    }
    //-- HELPERS: generic service helpers --//
    //-- HELPERS --//
    
    //** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
     
    @Inject
    Persons persons;
    //-- INJECTIONS --//
    
    //** HIDDEN: ACTIONS **//
    @MemberOrder(name = "Personen", sequence = "10")
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(hidden=Where.ANYWHERE)
    public PersonalContact createPersonalContact(
            @ParameterLayout(named="contactPerson")
            final Person contactPerson) {
        return createPersonalContact(contactPerson, currentUserName()); // see region>helpers
    }
    
    public List<Person> autoComplete0CreatePersonalContact(final String search) {
        return persons.findPersons(search);
    }
    
    public boolean hideCreatePersonalContact(final Person contactPerson){
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
    public String validateCreatePersonalContact(final Person contactPerson) {
        
        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "NO_USE";
        }
        
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson.getOwnedBy());
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
    }
    //-- HIDDEN: ACTIONS --//    
}
