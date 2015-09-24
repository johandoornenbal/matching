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
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.util.TitleBuffer;

import info.matchingservice.dom.Howdoido.Interfaces.Answer;
import info.matchingservice.dom.Howdoido.Interfaces.Form;
import info.matchingservice.dom.Howdoido.Interfaces.TrustLevel;
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
                name = "findBasicAnswerByForm", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicAnswer "
                        + "WHERE basicForm == :basicForm")
})
@DomainObject(autoCompleteRepository = BasicAnswers.class, editing = Editing.DISABLED)
public abstract class BasicAnswer extends MatchingDomainObject<BasicAnswer> implements Answer {

    public BasicAnswer() {
        super("basicForm, questionToAnswer");
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append("Answer to question ");
        if (getQuestionToAnswer().length() >= 30) {
            buf.append(getQuestionToAnswer().substring(0, 30));
        } else {
            buf.append(getQuestionToAnswer());
        }
        return buf.toString();
    }

    //region > basicForm (property)
    private BasicForm basicForm;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @PropertyLayout(hidden = Where.PARENTED_TABLES)
    public BasicForm getBasicForm() {
        return basicForm;
    }

    public void setBasicForm(final BasicForm basicForm) {
        this.basicForm = basicForm;
    }
    //endregion

    //region > questionToAnswer (property)
    private String questionToAnswer;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public String getQuestionToAnswer() {
        return questionToAnswer;
    }

    public void setQuestionToAnswer(final String questionToAnswer) {
        this.questionToAnswer = questionToAnswer;
    }
    //endregion

    //region > matchingTrustlevel (property)
    private info.matchingservice.dom.TrustLevel matchingTrustlevel;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public info.matchingservice.dom.TrustLevel getMatchingTrustlevel() {
        return matchingTrustlevel;
    }

    public void setMatchingTrustlevel(final info.matchingservice.dom.TrustLevel matchingTrustlevel) {
        this.matchingTrustlevel = matchingTrustlevel;
    }
    //endregion

    @Override
    @Programmatic
    public Form getForm() {
        return getBasicForm();
    }

    @Override
    @Programmatic
    public TrustLevel getTrustLevel() {
        return getMatchingTrustlevel();
    }
}
