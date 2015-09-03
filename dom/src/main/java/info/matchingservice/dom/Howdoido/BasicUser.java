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

import java.util.SortedSet;

import javax.inject.Inject;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.Howdoido.Interfaces.Feedback;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackProvider;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackReceiver;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackRequest;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackTemplate;
import info.matchingservice.dom.MatchingSecureMutableObject;

/**
 * Created by jodo on 30/08/15.
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@DomainObject(autoCompleteRepository = BasicUsers.class, editing = Editing.DISABLED)
@DomainObjectLayout()
public class BasicUser extends MatchingSecureMutableObject implements FeedbackProvider, FeedbackReceiver {

    public BasicUser() {
        super("email");
    }

    //region > email (property)
    private String email;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    //endregion

    public BasicUser updateEmail(
            @ParameterLayout(named = "email")
            final String email) {
        setEmail(email);
        return this;
    }

    public String default0UpdateEmail(final String email) {
        return getEmail();
    }

    public String validateUpdateEmail(final String email) {
        //check valid email
        for (BasicUser bu : basicUsers.allBasicUsers()){
            if (bu.getEmail().toLowerCase().equals(email.toLowerCase())) {
                return "This emailaddress is already taken";
            }
        }

        return null;
    }

    //region > name (property)
    private String name;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "true")
    @Title
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    public BasicUser updateName(
            @ParameterLayout(named = "name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName(final String name) {
        return getName();
    }

    //** ownedBy - Override for secure object **//
    private String ownedBy;

    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden= Where.ALL_TABLES)
    @Property(editing= Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    //** ownedBy - Override for secure object **//

    @Override
    public FeedbackRequest askFeedback(final FeedbackProvider provider, final FeedbackTemplate template) {
        return null;
    }

    @Override
    public SortedSet<Feedback> collectFeedback() {
        return null;
    }

    @Override
    public SortedSet<FeedbackRequest> collectOutstandingFeedbackRequests() {
        return null;
    }

    @Override
    public Feedback giveFeedback(final FeedbackRequest request) {
        return null;
    }

    @Override
    public SortedSet<FeedbackRequest> collectUnansweredFeedbackRequests() {
        return null;
    }

    @Override
    public SortedSet<Feedback> collectProvidedFeedback() {
        return null;
    }

    @Override
    public String getEmailAsUniqueId() {
        return getEmail();
    }

    @Inject
    BasicUsers basicUsers;

}
