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

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Password;

import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUserStatus;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 30/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicUser.class)
@DomainServiceLayout()
public class BasicUsers extends MatchingDomainService<BasicUser> {

    public BasicUsers() {
        super(BasicUsers.class, BasicUser.class);
    }


    @Programmatic
    public BasicUser findOrCreateNewBasicUserByEmail(
            final String email
    ){


        //check if user already exists and return the user if so
        if (findBasicUserByEmail(email)!=null) {
            return findBasicUserByEmail(email);
        }

        // else: try if app user exists
        ApplicationUser newAppUser;

        //check if app user already exists
        if (applicationUsers.findUserByEmail(email.toLowerCase()) != null) {
            newAppUser = applicationUsers.findUserByEmail(email.toLowerCase());
        } else {
            // create new app user
            newAppUser = createApplicationUser(email.toLowerCase(),email.toLowerCase(),email.toLowerCase());
        }
        newAppUser.setStatus(ApplicationUserStatus.ENABLED);
        newAppUser.addRole(applicationRoles.findRoleByName("matching-regular-role"));

        //create new basic user
        final BasicUser newUser = newTransientInstance(BasicUser.class);
        newUser.setName(email.toLowerCase());
        newUser.setEmail(email.toLowerCase());
        newUser.setOwnedBy(email.toLowerCase());
        persistIfNotAlready(newUser);

        return newUser;
    }

    @Programmatic
    public BasicUser createBasicUser(
            final String name,
            final String email
    ){
        final BasicUser newUser = newTransientInstance(BasicUser.class);
        newUser.setName(name);
        newUser.setEmail(email.toLowerCase());
        newUser.setOwnedBy(currentUserName());
        persistIfNotAlready(newUser);

        return newUser;
    }

    @Programmatic
    public BasicUser createBasicUser(
            final String name,
            final String email,
            final String owner
    ){
        final BasicUser newUser = newTransientInstance(BasicUser.class);
        newUser.setName(name);
        newUser.setEmail(email.toLowerCase());
        newUser.setOwnedBy(currentUserName());
        newUser.setOwnedBy(owner);
        persistIfNotAlready(newUser);

        return newUser;
    }


    @Action(semantics = SemanticsOf.SAFE)
    public List<BasicUser> allBasicUsers() {
        return allInstances();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public BasicUser findBasicUserByName(final String search) {
        if (search == "") {
            return null;
        }
        if (search == null) {
            return null;
        }
        return firstMatch("findBasicUserByName", "name", search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public BasicUser findBasicUserByLogin(final String ownedBy) {
        if (ownedBy == "") {
            return null;
        }
        if (ownedBy == null) {
            return null;
        }
        return firstMatch("findBasicUserByLogin", "ownedBy", ownedBy);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public BasicUser findBasicUserByEmail(final String search) {
        if (search =="") {
            return null;
        }
        if (search == null) {
            return null;
        }
        return firstMatch("findBasicUserByEmail", "email", search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<BasicUser> findBasicUserByNameContains(final String search) {
        if (search =="") {
            return null;
        }
        if (search == null) {
            return null;
        }
        return allMatches("findBasicUserByNameContains", "name", search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<BasicUser> findBasicUserByEmailContains(final String search) {
        if (search =="") {
            return null;
        }
        if (search == null) {
            return null;
        }
        return allMatches("findBasicUserByEmailContains", "email", search.toLowerCase());
    }

    // Region>helpers ////////////////////////////

    private String currentUserName() {
        return getUser().getName();
    }

    @Programmatic
    private ApplicationUser createApplicationUser(
            final String name,
            final String emailAddress,
            final String passwordString) {

        final ApplicationUser applicationUser;
        final Password password = new Password(passwordString);
        applicationUser = applicationUsers.newLocalUser(name, password, password, null, null, emailAddress);

        return applicationUser;
    }

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private ApplicationTenancies applicationTenancies;

    @Inject
    CommunicationChannels communicationChannels;

    @Inject
    ApplicationRoles applicationRoles;

}
