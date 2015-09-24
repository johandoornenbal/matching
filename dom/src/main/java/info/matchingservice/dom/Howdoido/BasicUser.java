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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Objects;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.TitleBuffer;

import org.isisaddons.module.security.dom.user.ApplicationUsers;

import info.matchingservice.dom.Howdoido.Interfaces.Category;
import info.matchingservice.dom.Howdoido.Interfaces.Contact;
import info.matchingservice.dom.Howdoido.Interfaces.Form;
import info.matchingservice.dom.Howdoido.Interfaces.Party;
import info.matchingservice.dom.Howdoido.Interfaces.Provider;
import info.matchingservice.dom.Howdoido.Interfaces.Rating;
import info.matchingservice.dom.Howdoido.Interfaces.Receiver;
import info.matchingservice.dom.Howdoido.Interfaces.Request;
import info.matchingservice.dom.Howdoido.Interfaces.Template;
import info.matchingservice.dom.Howdoido.Interfaces.TrustLevel;
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
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findBasicUserByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicUser "
                        + "WHERE name.toLowerCase() == :name.toLowerCase()"),
        @javax.jdo.annotations.Query(
                name = "findBasicUserByEmail", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicUser "
                        + "WHERE email.toLowerCase() == :email.toLowerCase()"),
        @javax.jdo.annotations.Query(
                name = "findBasicUserByNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicUser "
                        + "WHERE name.toLowerCase().indexOf(:name) >= 0"),
        @javax.jdo.annotations.Query(
                name = "findBasicUserByEmailContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicUser "
                        + "WHERE email.toLowerCase().indexOf(:email) >= 0"),
        @javax.jdo.annotations.Query(
                name = "findBasicUserByLogin", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicUser "
                        + "WHERE ownedBy == :ownedBy")
})
@DomainObject(autoCompleteRepository = BasicUsers.class, editing = Editing.DISABLED)
@DomainObjectLayout()
public class BasicUser extends MatchingSecureMutableObject implements Provider, Receiver {

    public BasicUser() {
        super("email");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        if (getName()!= "") {
            buf.append(getName());
            buf.append(" - ");
        }
        buf.append(getEmail());
        return buf.toString();
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

    /**
     * Updates the email property of BasicUser as well as Application user. Sets to lowerCase in both cases
     *
     * @param email needs to be valid email-addres not already in database; email is set to lowerCase
     *
     * @return this BasicUser
     */
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicUser updateEmail(
            @ParameterLayout(named = "email")
            final String email) {
        setEmail(email.toLowerCase());
        // set email also for user account
        applicationUsers.findUserByUsername(getOwnedBy()).setEmailAddress(email.toLowerCase());
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
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicUser updateName(
            @ParameterLayout(named = "name")
            final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName(final String name) {
        return getName();
    }

    //region > myTemplates (SortedSet)
    private SortedSet<BasicTemplate> myTemplates = new TreeSet<BasicTemplate>();

    @Persistent(mappedBy = "templateOwner", dependentElement = "true")
    @CollectionLayout(render= RenderType.EAGERLY)
    public SortedSet<BasicTemplate> getMyTemplates() {
        return myTemplates;
    }

    public void setMyTemplates(final SortedSet<BasicTemplate> myTemplates) {
        this.myTemplates = myTemplates;
    }

    //Business rule:
    //only visible for intimate-circle
    public boolean hideMyTemplates(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INTIMATE);
    }
    //endregion

    //** ownedBy - Override for secure object **//
    private String ownedBy;

    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden= Where.EVERYWHERE)
    @Property(editing= Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    //** ownedBy - Override for secure object **//

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicRequest> getReceivedFeedbackRequests() {
        return basicRequests.findRequestByRequestReceiver(this);
    }
    //Business rule:
    //only visible for intimate-circle
    public boolean hideReceivedFeedbackRequests(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INTIMATE);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicRequest> getSentFeedbackRequests() {
        return basicRequests.findRequestByOwner(this);
    }
    //Business rule:
    //only visible for inner-circle
    public boolean hideSentFeedbackRequests(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INNER_CIRCLE);
    }

    @Override
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Template createTemplate(
            @ParameterLayout(named = "name")
            final String name,
            @ParameterLayout(named = "category")
            final Category category) {
        return basicTemplates.createBasicTemplate(name, (BasicCategory) category, this);
    }

    public List<BasicCategory> autoComplete1CreateTemplate(String search) {
        return basicCategories.findByNameContains(search);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicForm> getPublishedFeedback() {
        return basicForms.findByReceiverAndPublished(this);
    }

    //Business rule:
    //only visible for entry-level
    public boolean hidePublishedFeedback(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.ENTRY_LEVEL);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicForm> getReceivedFeedback() {
        return basicForms.findByReceiver(this);
    }

    //Business rule:
    //only visible for inner-circle
    public boolean hideReceivedFeedback(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INNER_CIRCLE);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicForm> getUnratedReceivedFeedback() {
        return basicForms.findByReceiverAndUnRated(this);
    }

    //Business rule:
    //only visible for inner-circle
    public boolean hideUnratedReceivedFeedback(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INTIMATE);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicForm> getGivenFeedback() {
        return basicForms.findByCreator(this);
    }
    //Business rule:
    //only visible for intimate-circle
    public boolean hideGivenFeedback(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INTIMATE);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicRating> getMyReceivedRatings() {
        return basicRatings.findByReceiver(this);
    }

    //Business rule:
    //not visible for banned-circle
    public boolean hideMyReceivedRatings(){

        if (Objects.equal(getOwnedBy(), getContainer().getUser().getName())) {
            return false;
        }
        // user is admin of app
        if (getContainer().getUser().hasRole(".*matching-admin-role")) {
            return false;
        }

        if (getViewerRights() == null) {
            return false;
        }

        if (info.matchingservice.dom.TrustLevel.BANNED.compareTo(getViewerRights()) >= 0) {
            return true;
        }

        return false;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicRating> getMyGivenRatings() {
        return basicRatings.findByCreator(this);
    }

    //Business rule:
    //only visible for intimate-circle
    public boolean hideMyGivenRatings(){
        return super.allowedTrustLevel(info.matchingservice.dom.TrustLevel.INTIMATE);
    }



    @Override
    @Programmatic
    public List<Form> receivedForms() {
        List<Form> list = new ArrayList<>();
        for (Form f : getReceivedFeedback()) {
            list.add(f);
        }
        return list;
    }

    @Override
    @Programmatic
    public String getEmailAsUniqueId() {
        return getEmail();
    }

    @Override
    @Programmatic
    public Contact createContact(final Party party, final TrustLevel trustLevel) {
        return null;
    }

    @Override
    @Programmatic
    public List<Request> receivedRequests() {
        List<Request> list = new ArrayList<>();
        for (Request r : getReceivedFeedbackRequests()) {
            list.add(r);
        }
        return list;
    }

    @Override
    @Programmatic
    public List<Rating> receivedRatings() {
        List<Rating> list = new ArrayList<>();
        for (Rating r : getMyReceivedRatings()) {
            list.add(r);
        }
        return list;
    }


    @Inject
    BasicUsers basicUsers;

    @Inject
    BasicTemplates basicTemplates;

    @Inject
    BasicCategories basicCategories;

    @Inject
    BasicRequests basicRequests;

    @Inject
    BasicForms basicForms;

    @Inject
    BasicRatings basicRatings;

    @Inject
    ApplicationUsers applicationUsers;

}
