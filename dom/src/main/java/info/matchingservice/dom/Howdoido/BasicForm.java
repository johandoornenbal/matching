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
import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.util.TitleBuffer;

import info.matchingservice.dom.Howdoido.Interfaces.Answer;
import info.matchingservice.dom.Howdoido.Interfaces.Form;
import info.matchingservice.dom.Howdoido.Interfaces.Provider;
import info.matchingservice.dom.Howdoido.Interfaces.Rate;
import info.matchingservice.dom.Howdoido.Interfaces.Rating;
import info.matchingservice.dom.Howdoido.Interfaces.Receiver;
import info.matchingservice.dom.Howdoido.Interfaces.Template;
import info.matchingservice.dom.MatchingDomainObject;

/**
 * Created by jodo on 04/09/15.
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
                name = "findBasicFormByReceiver", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicForm "
                        + "WHERE formReceiver == :formReceiver"),
        @javax.jdo.annotations.Query(
                name = "findBasicFormByCreator", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicForm "
                        + "WHERE formCreator == :formCreator"),
        @javax.jdo.annotations.Query(
                name = "findBasicFormByReceiverAndPublished", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicForm "
                        + "WHERE formReceiver == :formReceiver && published == :published"),
        @javax.jdo.annotations.Query(
                name = "findBasicFormByReceiverAndRated", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicForm "
                        + "WHERE formReceiver == :formReceiver && formRated == :formRated")
})
@DomainObject(autoCompleteRepository = BasicForms.class, editing = Editing.DISABLED)
public class BasicForm extends MatchingDomainObject<BasicForm> implements Form {

    public BasicForm() {
        super("requestOwner, requestReceiver, basicTemplate");
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append("Feedback to ");
        buf.append(getFormReceiver().getName());
        buf.append(" regarding ");
        buf.append(getBasicTemplate().getName());
        return buf.toString();
    }

    //region > formCreator (property)
    private BasicUser formCreator;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public BasicUser getFormCreator() {
        return formCreator;
    }

    public void setFormCreator(final BasicUser formCreator) {
        this.formCreator = formCreator;
    }
    //endregion

    //region > receiver (property)
    private BasicUser formReceiver;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicUser getFormReceiver() {
        return formReceiver;
    }

    public void setFormReceiver(final BasicUser formReceiver) {
        this.formReceiver = formReceiver;
    }
    //endregion

    //region > type (property)
    private BasicTemplate basicTemplate;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public BasicTemplate getBasicTemplate() {
        return basicTemplate;
    }

    public void setBasicTemplate(final BasicTemplate basicTemplate) {
        this.basicTemplate = basicTemplate;
    }
    //endregion

    //region > formRated (property)
    private boolean formRated;
    @Column(allowsNull = "false")
    @MemberOrder(sequence = "4")

    public boolean getFormRated() {
        return formRated;
    }

    public void setFormRated(final boolean formRated) {
        this.formRated = formRated;
    }
    //endregion

    //region > published (property)
    private boolean published;

    @Column(allowsNull = "false")
    public boolean getPublished() {
        return published;
    }

    public void setPublished(final boolean published) {
        this.published = published;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicForm publish() {
        setPublished(true);
        return this;
    }

    public boolean hidePublish() {
        if (getFormReceiver().getOwnedBy().equals(getContainer().getUser().getName())) {
            return getPublished() ? true : false;
        }
        return true;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicForm unPublish() {
        setPublished(false);
        return this;
    }

    public boolean hideUnPublish() {
        if (getFormReceiver().getOwnedBy().equals(getContainer().getUser().getName())) {
            return getPublished() ? false : true;
        }
        return true;
    }
    //endregion

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicAnswer> getAnswers() {
        return basicAnswers.findByForm(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    public boolean isAnonymous() {
        return getFormCreator().equals(getFormReceiver()) ? true : false;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicRating createFeedbackRating(
            @ParameterLayout(named = "rating")
            final FeedbackRating rating) {
        setFormRated(true);
        return basicRatings.createBasicRating(
                getFormReceiver(),
                getFormCreator(),
                rating,
                (BasicCategory) getTemplate().getCategory());
    }

    public boolean hideCreateFeedbackRating(final FeedbackRating rating){

        if (getFormRated()) {
            return true;
        }

        if (isAnonymous()) {
            return true;
        }

        if (getFormReceiver().getOwnedBy().equals(getContainer().getUser().getName())) {
            return false;
        }

        return true;

    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicForm assignFeedbackToNewEmailAddress(
            @ParameterLayout(named = "email")
            @Parameter(regexPattern ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")
            final String email
    ) {
        BasicUser provider = basicUsers.findOrCreateNewBasicUserByEmail(email);
        setFormCreator(provider);
        return this;
    }

    public String validateAssignFeedbackToNewEmailAddress(final String email) {

        if (isAnonymous()) {
            return null;
        }
        return "Only valid for anonymous forms";
    }

    public boolean hideAssignFeedbackToNewEmailAddress(final String email) {

        if (isAnonymous()) {
            return false;
        }
        return true;
    }


    @Override
    @Programmatic
    public Provider getProvider() {
        return getFormCreator();
    }

    @Override
    @Programmatic
    public Receiver getReceiver() {
        return getFormReceiver();
    }

    @Override
    @Programmatic
    public Template getTemplate() {
        return getBasicTemplate();
    }

    @Override
    @Programmatic
    public List<Answer> requestedAnswers() {
        return null;
    }

    @Override
    @Programmatic
    public Rating createRating(Rate rating) {
        return createFeedbackRating((FeedbackRating) rating);
    }

    @Inject
    BasicAnswers basicAnswers;

    @Inject
    BasicRatings basicRatings;

    @Inject
    BasicUsers basicUsers;

}
