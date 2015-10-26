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

import info.matchingservice.dom.Howdoido.Interfaces.*;
import info.matchingservice.dom.MatchingDomainObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.TitleBuffer;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.*;

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
                name = "findBasicRequestByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRequest "
                        + "WHERE requestOwner == :owner"),
        @javax.jdo.annotations.Query(
                name = "findBasicDeniedRequestByOwner", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRequest "
                        + "WHERE requestOwner == :owner && requestDenied == true"),
        @javax.jdo.annotations.Query(
                name = "findBasicRequestByRequestReceiver", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRequest "
                        + "WHERE requestReceiver == :requestReceiver && requestDenied == false && requestHonoured == false"),
        @javax.jdo.annotations.Query(
                name = "findBasicRequestByTemplate", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicRequest "
                        + "WHERE basicTemplate == :basicTemplate")
})
@DomainObject(autoCompleteRepository = BasicRequests.class, editing = Editing.DISABLED)
public class BasicRequest extends MatchingDomainObject<BasicRequest> implements Request {

    public BasicRequest() {
        super("requestOwner, requestReceiver, basicTemplate");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
            return JDOHelper.getObjectId(this).toString();
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append("Feedback request to ");
        buf.append(getRequestReceiver().title());
        return buf.toString();
    }

    //region > requestOwner (property)
    private BasicUser requestOwner;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicUser getRequestOwner() {
        return requestOwner;
    }

    public void setRequestOwner(final BasicUser requestOwner) {
        this.requestOwner = requestOwner;
    }
    //endregion

    //region > requestReceiver (property)
    private BasicUser requestReceiver;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicUser getRequestReceiver() {
        return requestReceiver;
    }

    public void setRequestReceiver(final BasicUser requestReceiver) {
        this.requestReceiver = requestReceiver;
    }
    //endregion

    //region > template (property)
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

    //region > requestHonoured (property)
    private Boolean requestHonoured;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "true")
    public Boolean getRequestHonoured() {
        return requestHonoured;
    }

    public void setRequestHonoured(final Boolean requestHonoured) {
        this.requestHonoured = requestHonoured;
    }
    //endregion

    //region > requestDenied (property)
    private Boolean requestDenied;

    @MemberOrder(sequence = "5")
    @Column(allowsNull = "true")
    public Boolean getRequestDenied() {
        return requestDenied;
    }

    public void setRequestDenied(final Boolean requestDenied) {
        this.requestDenied = requestDenied;
    }
    //endregion

    //region > date (property)
    private LocalDateTime dateTime;

    @MemberOrder(sequence = "6")
    @Column(allowsNull = "true")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    //endregion

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Template deleteRequest(
            @ParameterLayout(named = "are you sure?")
            final boolean areYouSure) {

        container.removeIfNotAlready(this);

        return getBasicTemplate();

    }

    public String validateDeleteRequest(final boolean areYouSure) {

        return areYouSure ? null : "Please confirm";
    }

    public boolean hideDeleteRequest(final boolean areYouSure) {

        if (getRequestOwner().getOwnedBy().equals(container.getUser().getName())) {
            return false;
        }

        return true;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicForm createFeedback() {
        setRequestHonoured(true);
        return basicForms.createBasicForm(
                getRequestReceiver(),
                getRequestOwner(),
                getBasicTemplate());
    }

    public boolean hideCreateFeedback() {

        if (getRequestReceiver().getOwnedBy().equals(container.getUser().getName())) {
            return false;
        }

        return true;
    }

    public BasicRequest denyRequest() {
        setRequestDenied(true);
        return this;
    }

    public boolean hideDenyRequest() {

        if (getRequestReceiver().getOwnedBy().equals(container.getUser().getName())) {
            return false;
        }

        return true;
    }


    @Override
    public Provider getProvider() {
        return getRequestReceiver();
    }

    @Override
    public Receiver getReceiver() {
        return getRequestOwner();
    }

    @Override
    @Programmatic
    public Template getTemplate() {
        return getBasicTemplate();
    }

    @Override
    @Programmatic
    public Form createForm() {
        return createFeedback();
    }

    @Inject
    DomainObjectContainer container;

    @Inject
    BasicForms basicForms;
}
