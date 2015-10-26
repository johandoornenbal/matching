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

import info.matchingservice.dom.Howdoido.Interfaces.RepresentationType;
import info.matchingservice.dom.Howdoido.Interfaces.Template;
import info.matchingservice.dom.MatchingSecureMutableObject;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.TitleBuffer;

import javax.inject.Inject;
import javax.jdo.annotations.*;

/**
 * Created by jodo on 31/08/15.
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
@DomainObject(autoCompleteRepository = BasicQuestions.class, editing = Editing.DISABLED)
public class BasicQuestion extends MatchingSecureMutableObject implements info.matchingservice.dom.Howdoido.Interfaces.Question {

    public BasicQuestion() {
        super("basicQuestion, basicTemplate");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getQuestionId() {
        return this.getId();
    }

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        if (getBasicQuestion().length() >= 30) {
        buf.append(getBasicQuestion().substring(0,30));
        } else {
            buf.append(getBasicQuestion());
        }
        buf.append(" - ");
        buf.append(getBasicTemplate().getName());
        return buf.toString();
    }

    //region > basicQuestion (property)
    private String basicQuestion;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @PropertyLayout(multiLine = 3)
    public String getBasicQuestion() {
        return basicQuestion;
    }

    public void setBasicQuestion(final String Question) {
        this.basicQuestion = Question;
    }

    public BasicQuestion updateQuestion(
            @ParameterLayout(multiLine = 3, named = "question")
            final String question) {
        setBasicQuestion(question);
        return this;
    }

    public String default0UpdateQuestion(final String question) {
        return getBasicQuestion();
    }
    //endregion


    //region > basicTemplate (property)
    private BasicTemplate basicTemplate;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicTemplate getBasicTemplate() {
        return basicTemplate;
    }

    public void setBasicTemplate(final BasicTemplate template) {
        this.basicTemplate = template;
    }
    //endregion

    //region > basicFormType (property)
    private BasicRepresentationType basicFormType;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false", name = "basicFormTypeId")
    public BasicRepresentationType getBasicFormType() {
        return basicFormType;
    }

    public void setBasicFormType(final BasicRepresentationType basicFormType) {
        this.basicFormType = basicFormType;
    }

    public BasicQuestion updateBasicFormType(
            @ParameterLayout(named = "form type")
            final BasicRepresentationType type) {
        setBasicFormType(type);
        return this;
    }

    public BasicRepresentationType default0UpdateBasicFormType(final BasicRepresentationType type) {
        return getBasicFormType();
    }
    //endregion

    public BasicTemplate deleteQuestion(final boolean AreYouSure) {
        BasicTemplate myTemplate = getBasicTemplate();
        container.removeIfNotAlready(this);
        return myTemplate;
    }

    public String validateDeleteQuestion(final boolean AreYouSure) {
        return AreYouSure ? null : "Please confirm";
    }

    @Override
    @Programmatic
    public Template getTemplate() {
        return getBasicTemplate();
    }

    @Override
    @Programmatic
    public RepresentationType getFormType() {
        return getBasicFormType();
    }

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

    @Inject
    DomainObjectContainer container;

}
