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

import com.google.common.base.Objects;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.MatchingTrustedContacts;
import info.matchingservice.dom.TrustLevel;

@DomainService(nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class BasicContactContributions {

    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed=Contributed.AS_ACTION)
    public BasicContact addAsPersonalContact(
            @ParameterLayout(named="contactPerson")
            final BasicUser contactPerson) {
        return basicContacts.createPersonalContact(contactPerson, currentUserName());
    }

    public boolean hideAddAsPersonalContact(final BasicUser contactPerson){

        // do not show on own personinstance - you cannot add yourself as personal contact
        if (contactPerson.getOwnedBy().equals(currentUserName())) {
            return true;
        }
        // do not show when already contacted
        QueryDefault<BasicContact> query =
                QueryDefault.create(
                        BasicContact.class,
                    "findPersonalContactUniqueContact",
                    "ownedBy", currentUserName(),
                    "contact", contactPerson);
        return container.firstMatch(query) != null?
        true  : false;
    }

    /**
     * There should be at most 1 instance for each owner - contact combination.
     *
     */
    public String validateAddAsPersonalContact(final BasicUser contactPerson) {

        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "NO_USE";
        }

        QueryDefault<BasicContact> query =
                QueryDefault.create(
                        BasicContact.class,
                    "findPersonalContactUniqueContact",
                    "ownedBy", currentUserName(),
                    "contact", contactPerson);
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"
        :null;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicContact> contacts(final BasicUser user) {
        return basicContacts.allPersonalContactsOfBasicUser(user);
    }

    //Business rule:
    //only visible for inner-circle
    public boolean hideContacts(final BasicUser user) {
        // user is owner
        if (Objects.equal(user.getOwnedBy(), container.getUser().getName())) {
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*matching-admin-role")) {
            return false;
        }
        if (trustedContacts.trustLevel(user.getOwnedBy(), container.getUser().getName()) != null && TrustLevel.INNER_CIRCLE.compareTo(trustedContacts.trustLevel(user.getOwnedBy(), container.getUser().getName())) <= 0) {
            return false;
        }
        return true;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicContact> contactsReferingToMe(final BasicUser user) {
        return basicContacts.allPersonalContactsReferringToBasicUser(user);
    }

    //Business rule:
    //only visible for intimate-circle
    public boolean hideContactsReferingToMe(final BasicUser user) {
        // user is owner
        if (Objects.equal(user.getOwnedBy(), container.getUser().getName())) {
            return false;
        }
        // user is admin of app
        if (container.getUser().hasRole(".*matching-admin-role")) {
            return false;
        }
        if (trustedContacts.trustLevel(user.getOwnedBy(), container.getUser().getName()) != null && TrustLevel.INTIMATE.compareTo(trustedContacts.trustLevel(user.getOwnedBy(), container.getUser().getName())) <= 0) {
            return false;
        }
        return true;
    }


    private String currentUserName() {
        return container.getUser().getName();
    }

    //** INJECTIONS **//
    @Inject
    private DomainObjectContainer container;

    @Inject
    BasicContacts basicContacts;

    @Inject
    MatchingTrustedContacts trustedContacts;
 
}
