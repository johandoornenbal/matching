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

import java.util.UUID;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.util.TitleBuffer;

import info.matchingservice.dom.Howdoido.Interfaces.Category;
import info.matchingservice.dom.Howdoido.Interfaces.Provider;
import info.matchingservice.dom.Howdoido.Interfaces.Rate;
import info.matchingservice.dom.Howdoido.Interfaces.Rating;
import info.matchingservice.dom.Howdoido.Interfaces.Receiver;
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
                name = "findBasicRatingByCreator", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRating "
                        + "WHERE creator == :creator"),
        @javax.jdo.annotations.Query(
                name = "findBasicRatingByReceiver", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRating "
                        + "WHERE receiver == :receiver"),
        @javax.jdo.annotations.Query(
                name = "findBasicRatingByCategory", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRating "
                        + "WHERE basicCategory == :basicCategory"),
        @javax.jdo.annotations.Query(
                name = "findBasicRatingByReceiverAndCategory", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRating "
                        + "WHERE receiver == :receiver && basicCategory == :basicCategory")
})
@DomainObject(autoCompleteRepository = BasicRatings.class, editing = Editing.DISABLED)
public class BasicRating extends MatchingDomainObject<BasicRating> implements Rating {

    public BasicRating() {
        super("creator, requestReceiver, basicTemplate");
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append("user: ");
        buf.append(getReceiver().title());
        buf.append(" | category: ");
        buf.append(getBasicCategory().getName());
        buf.append(" | rating: ");
        buf.append(getFeedbackRating().ratingValue().toString());
        return buf.toString();
    }

    private UUID uniqueItemId;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }

    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }


    //region > creator (property)
    private BasicUser creator;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    public BasicUser getCreator() {
        return creator;
    }

    public void setCreator(final BasicUser creator) {
        this.creator = creator;
    }
    //endregion

    //region > receiver (property)
    private BasicUser receiver;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicUser getReceiver() {
        return receiver;
    }

    public void setReceiver(final BasicUser receiver) {
        this.receiver = receiver;
    }
    //endregion

    //region > feedbackRating (property)
    private FeedbackRating feedbackRating;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public FeedbackRating getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(final FeedbackRating feedbackRating) {
        this.feedbackRating = feedbackRating;
    }
    //endregion

    //region > basicCategory (property)
    private BasicCategory basicCategory;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "false")
    public BasicCategory getBasicCategory() {
        return basicCategory;
    }

    public void setBasicCategory(final BasicCategory basicCategory) {
        this.basicCategory = basicCategory;
    }
    //endregion

    @Override
    @Programmatic
    public Provider getRatingReceiver() {
        return getReceiver();
    }

    @Override
    @Programmatic
    public Receiver getRatingCreator() {
        return getCreator();
    }

    @Override
    @Programmatic
    public Rate getRate() {
        return getFeedbackRating();
    }

    @Override
    @Programmatic
    public Category getCategory() {
        return getBasicCategory();
    }
}
