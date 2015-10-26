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
import info.matchingservice.dom.MatchingSecureMutableObject;
import org.apache.isis.applib.annotation.*;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "deleteBasicTemplate", language = "JDOQL",
                value = "DELETE "
                        + "FROM info.matchingservice.dom.Howdoido.BasicTemplate "
                        + "WHERE id == :id")
})
@DomainObject(autoCompleteRepository = BasicTemplates.class, editing = Editing.DISABLED)
public class BasicTemplate extends MatchingSecureMutableObject implements Template {

    public BasicTemplate() {
        super("dateTime, name");
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
    }

    //region > name (property)
    private String name;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @Title
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicTemplate updateName(final String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName(final String name) {
        return getName();
    }

    //endregion

    //region > basicCategory (property)
    private BasicCategory basicCategory;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    public BasicCategory getBasicCategory() {
        return basicCategory;
    }

    public void setBasicCategory(final BasicCategory category) {
        this.basicCategory = category;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicTemplate updateCategory(final BasicCategory category) {
        setBasicCategory(category);
        return this;
    }

    public BasicCategory default0UpdateCategory(final BasicCategory category) {
        return getBasicCategory();
    }

    public List<BasicCategory> autoComplete0UpdateCategory(final String search){
        return basicCategories.findByNameContains(search);
    }
    //endregion

    //region > templateOwner (property)
    private BasicUser templateOwner;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "false")
    public BasicUser getTemplateOwner() {
        return templateOwner;
    }

    public void setTemplateOwner(final BasicUser templateOwner) {
        this.templateOwner = templateOwner;
    }
    //endregion

    //region > dateTime (property)
    private LocalDateTime dateTime;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "false")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    //endregion

    //region > categorySuggestion (property)
    private String categorySuggestion;

    @MemberOrder(sequence = "5")
    @Column(allowsNull = "true")
    public String getCategorySuggestion() {
        return categorySuggestion;
    }

    public void setCategorySuggestion(final String categorySuggestion) {
        this.categorySuggestion = categorySuggestion;
    }
    //endregion

    //region > basicQuestions (SortedSet)
    private SortedSet<BasicQuestion> basicQuestions = new TreeSet<BasicQuestion>();

    @Persistent(mappedBy = "basicTemplate", dependentElement = "true")
    @CollectionLayout(render= RenderType.EAGERLY)
    public SortedSet<BasicQuestion> getBasicQuestions() {
        return basicQuestions;
    }

    public void setBasicQuestions(final SortedSet<BasicQuestion> personalContacts) {
        this.basicQuestions = personalContacts;
    }
    //endregion

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicTemplate createBasicQuestion(
            @ParameterLayout(multiLine = 3, named = "question")
            final String question,
            @ParameterLayout(named = "form type")
            final BasicRepresentationType formType
            ) {
        basicQuestionsRepo.createBasicQuestion(question, this, formType);
        return this;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Create Feedback Request")
    public Request createRequest(
            @ParameterLayout(named = "type in name or email address")
            @Parameter(optionality = Optionality.OPTIONAL)
            final String search,
            @ParameterLayout(named = "or find existing user")
            @Parameter(optionality = Optionality.OPTIONAL)
            final BasicUser userProvider) {


        BasicUser provider = new BasicUser();

        if (userProvider!= null) {

            provider = userProvider;

        } else {

            if (basicUsers.findBasicUserByEmail(search) != null) {
                provider = basicUsers.findBasicUserByEmail(search);
            } else {

                if (basicUsers.findBasicUserByName(search) != null) {
                    provider = basicUsers.findBasicUserByName(search);
                } else {

                    provider = basicUsers.findOrCreateNewBasicUserByEmail(search);

                }
            }
        }

        return basicRequests.createBasicRequest(getTemplateOwner(), provider, this);

    }

    public List<BasicUser> autoComplete1CreateRequest(String search) {

        List<BasicUser> list = new ArrayList<>();

        if (basicUsers.findBasicUserByEmailContains(search).size() > 0 ) {
            for (BasicUser u : basicUsers.findBasicUserByEmailContains(search)) {
                list.add(u);
            }

        }
        if (basicUsers.findBasicUserByNameContains(search).size() > 0 ) {
            for (BasicUser u : basicUsers.findBasicUserByNameContains(search)) {
                if (!list.contains(u)) {
                    list.add(u);
                }
            }
        }

        return list;

    }

    public String validateCreateRequest(final String search, final BasicUser userProvider) {

        if (userProvider!=null) {
            return null;
        }

        if (basicUsers.findBasicUserByEmail(search) != null ) {
            return null;
        }

        if (basicUsers.findBasicUserByName(search) != null ) {
            return null;
        }

        if (search != null && search.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")) {
            return null;
        }

        return "No valid email address given or existing user found";
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Create Anonymous Feedback Request")
    public BasicForm createAnonymousForm() {
        return basicForms.createBasicForm(getTemplateOwner(), getTemplateOwner(), this);
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public BasicUser deleteBasicTemplate(final Boolean confirm) {
        for (BasicQuestion basicQuestion : getBasicQuestions()) {
            getContainer().remove(basicQuestion);
        }
        for (BasicRequest basicRequest : basicRequests.findBasicRequestByTemplate(this)){
            getContainer().remove(basicRequest);
        }
        BasicUser myUser = getTemplateOwner();
        getContainer().removeIfNotAlready(this);
        return myUser;
    }

    public String validateDeleteBasicTemplate(final Boolean confirm) {
        return confirm ? null : "Please confirm";
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public BasicTemplate duplicateBasicTemplate(){
        BasicTemplate newTemplate = basicTemplatesRepo.createBasicTemplate(getName().concat(" (copy)"), getBasicCategory(), getTemplateOwner());
        for (BasicQuestion question : getBasicQuestions()){
            newTemplate.createBasicQuestion(question.getBasicQuestion(), question.getBasicFormType());
        }
        return newTemplate;
    }


    @Override
    @Programmatic
    public Question createQuestion(final String question, final RepresentationType representationType) {
        createBasicQuestion(question, (BasicRepresentationType) representationType);
        //TODO: fulfill contract for interface instead of returning null
        return null;
    }

    @Override
    @Programmatic
    public List<Question> questions() {
        List<Question> list = new ArrayList<>();
        for (BasicQuestion q : getBasicQuestions()) {
            list.add(q);
        }
        return list;
    }

    @Override
    @Programmatic
    public Request createRequest(final Provider provider, final Template template) {
        BasicUser user = (BasicUser) provider;
        return createRequest(basicUsers.findBasicUserByEmail(((BasicUser) provider).getEmail()), this);
    }

    @Override
    @Programmatic
    public Receiver getReceiver() {
        return getTemplateOwner();
    }


    @Override public Category getCategory() {
        return getBasicCategory();
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
    BasicQuestions basicQuestionsRepo;

    @Inject
    BasicForms basicForms;

    @Inject
    BasicUsers basicUsers;

    @Inject
    BasicCategories basicCategories;

    @Inject
    BasicRequests basicRequests;

    @Inject
    BasicTemplates basicTemplatesRepo;

}
