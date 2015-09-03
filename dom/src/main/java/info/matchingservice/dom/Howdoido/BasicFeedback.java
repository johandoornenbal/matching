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

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;

import info.matchingservice.dom.Howdoido.Interfaces.Feedback;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackReceiver;
import info.matchingservice.dom.Howdoido.Interfaces.FeedbackRequest;
import info.matchingservice.dom.TrustLevel;

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
@DomainObject()
@DomainObjectLayout()
public class BasicFeedback implements Feedback {

    @Override
    public FeedbackRequest getFeedbackRequest() {
        return null;
    }

    @Override
    public void setFeedbackRequest() {

    }

    @Override
    public FeedbackRating getRating() {
        return null;
    }

    @Override
    public void setRating(final FeedbackRating rating) {

    }

    @Override
    public TrustLevel accessibleFor() {
        return null;
    }

    @Override
    public Feedback rateFeedback(final FeedbackRating rating, final FeedbackReceiver feedbackReceiver) {
        return null;
    }
}
