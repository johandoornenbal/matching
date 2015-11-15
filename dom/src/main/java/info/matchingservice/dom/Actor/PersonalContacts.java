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

import com.google.common.base.Objects;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@DomainService(repositoryFor = PersonalContact.class, nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
//@DomainServiceLayout(menuOrder="13")
public class PersonalContacts extends MatchingDomainService<PersonalContact>{
    
    public PersonalContacts() {
        super(PersonalContacts.class, PersonalContact.class);
    }

    @Programmatic
    public PersonalContact findUniquePersonalContact(final String username, final Person contact){
        return uniqueMatch("findPersonalContactUniqueContact", "ownedBy", username, "contact", contact);
    }
    
    //** CONTRIBUTED: ACTIONS **//
    @MemberOrder(name = "Personen", sequence = "10")
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed=Contributed.AS_ACTION)
    public PersonalContact addAsPersonalContact(
            @ParameterLayout(named="contactPerson")
            final Person contactPerson) {
        return createPersonalContact(contactPerson, currentUserName()); // see region>helpers
    }
    
//    public List<Person> autoComplete0CreatePersonalContact(final String search) {
//        return persons.findPersons(search);
//    }
    
    public boolean hideAddAsPersonalContact(final Person contactPerson){

        // do not show on own personinstance - you cannot add yourself as personal contact
        if (contactPerson.getOwnedBy().equals(currentUserName())) {
            return true;
        }
        // do not show when already contacted
        return findUniquePersonalContact(currentUserName(), contactPerson) != null?
        true  : false;        
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateAddAsPersonalContact(final Person contactPerson) {
        
        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "NO_USE";
        }
        return findUniquePersonalContact(currentUserName(), contactPerson) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
    }
    //-- CONTRIBUTED: ACTIONS --//   
    
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
    	if (persons.activatePerson(userName) == null ){
    		return contacts;
    	} else {
            QueryDefault<PersonalContact> query = 
                    QueryDefault.create(
                            PersonalContact.class, 
                        "findPersonalContactReferringToPerson", 
                        "contact", persons.activePerson(userName).getOwnedBy());
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
        contact.setOwner(persons.activePerson(userName));
        contact.setOwnedBy(userName);
        persist(contact);
        getContainer().flush();
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
        contact.setOwner(persons.activePerson(userName));
        contact.setOwnedBy(userName);
        contact.setTrustLevel(trustLevel);
        persist(contact);
        getContainer().flush();
        return contact;
    }

    private String currentUserName() {
        return container.getUser().getName();
    }

    @javax.inject.Inject
    private DomainObjectContainer container;
     
    @Inject
    Persons persons;

    
 
}
