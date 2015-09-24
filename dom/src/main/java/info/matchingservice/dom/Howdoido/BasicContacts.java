/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package info.matchingservice.dom.Howdoido;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.TrustLevel;

@DomainService(repositoryFor = PersonalContact.class, nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
//@DomainServiceLayout(menuOrder="13")
public class BasicContacts extends MatchingDomainService<BasicContact>{

    public BasicContacts() {
        super(BasicContacts.class, BasicContact.class);
    }

    @Programmatic
    public List<BasicContact> listAllPersonalContacts() {
        return container.allInstances(BasicContact.class);
    }

    @Programmatic
    public List<BasicContact> allPersonalContactsOfUser(){
        QueryDefault<BasicContact> query =
                QueryDefault.create(
                        BasicContact.class,
                    "findPersonalContact",
                    "ownedBy", currentUserName());
        return allMatches(query);
    }

    @Programmatic
    public List<BasicContact> allPersonalContactsOfBasicUser(final BasicUser basicUser){
        QueryDefault<BasicContact> query =
                QueryDefault.create(
                        BasicContact.class,
                        "findPersonalContact",
                        "ownedBy", basicUser.getOwnedBy());
        return allMatches(query);
    }

    @Programmatic
    public List<BasicContact> allPersonalContactsReferringToBasicUser(final BasicUser basicUser){
    	final List<BasicContact> contacts = Collections.emptyList();
    	if (basicUser == null ){
    		return contacts;
    	} else {
            QueryDefault<BasicContact> query =
                    QueryDefault.create(
                            BasicContact.class,
                            "findPersonalContactReferringToPerson",
                            "contact", basicUser.getOwnedBy());
            return allMatches(query);
    	}
    }

    @Programmatic //userName can now also be set by fixtures
    public BasicContact createPersonalContact(
            final BasicUser contactPerson,
            final String userName) {
        final BasicContact contact = newTransientInstance(BasicContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnerPerson(basicUsers.findBasicUserByName(userName));
        contact.setOwnedBy(userName);
        persist(contact);
        return contact;
    }

    @Programmatic //userName and trustLevel can now also be set by fixtures
    public BasicContact createPersonalContact(
            final BasicUser contactPerson,
            final String userName,
            final TrustLevel trustLevel) {
        final BasicContact contact = newTransientInstance(BasicContact.class);
        contact.setContactPerson(contactPerson);
        contact.setContact(contactPerson.getOwnedBy());
        contact.setOwnerPerson(basicUsers.findBasicUserByName(userName));
        contact.setOwnedBy(userName);
        contact.setTrustLevel(trustLevel);
        persist(contact);
        return contact;
    }

    private String currentUserName() {
        return container.getUser().getName();
    }

    @Inject
    private DomainObjectContainer container;
     
    @Inject
    BasicUsers basicUsers;

    
 
}
