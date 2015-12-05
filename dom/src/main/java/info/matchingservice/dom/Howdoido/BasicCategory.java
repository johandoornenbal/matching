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

import info.matchingservice.dom.Howdoido.Interfaces.Category;
import info.matchingservice.dom.Howdoido.Viewmodels.AverageRatingForCategory;
import info.matchingservice.dom.MatchingDomainObject;
import org.apache.isis.applib.annotation.*;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
                name = "findBasicCategoryByNameContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicCategory "
                        + "WHERE name.toLowerCase().indexOf(:name) >= 0"),
        @javax.jdo.annotations.Query(
                name = "findBasicCategoryByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Howdoido.BasicCategory "
                        + "WHERE name == :name")
})
@DomainObject(editing = Editing.DISABLED, autoCompleteRepository = BasicCategories.class)
@DomainObjectLayout()
public class BasicCategory extends MatchingDomainObject implements Category {

    //region > name (property)
    private String name;

    public BasicCategory() {
        super("name");
    }

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false")
    @Title
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    //endregion

    public BasicCategory updateName(final @ParameterLayout(named = "name") String name) {
        setName(name);
        return this;
    }

    public String default0UpdateName(final String name) {
        return getName();
    }

    public String validateUpdateName(final String name) {

        //check valid name
        for (BasicCategory bc : basicCategories.allBasicCategories()){
            if (name.toLowerCase().equals(bc.getName().toLowerCase())) {
                return "Name of basic category should be unique";
            }
        }

        return null;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> getDirectParentCategoryTuples() {
        return basicCategoryRelationshipTuples.findByChild(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getDirectParentCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple parentTuple : basicCategoryRelationshipTuples.findByChild(this)) {
            result.add(parentTuple.getParent());
        }
        return result;
    }

   @Programmatic
    public List<BasicCategoryRelationshipTuple> getDirectChildCategoryTuples() {
        return basicCategoryRelationshipTuples.findByParent(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getDirectChildCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple childTuple : basicCategoryRelationshipTuples.findByParent(this)) {
            result.add(childTuple.getChild());
        }
        return result;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> getAllDescendantCategoryTuples() {
        return basicCategoryRelationshipTuples.allChildren(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getAllDescendantCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple childTuple : basicCategoryRelationshipTuples.allChildren(this)) {

            if (!result.contains(childTuple.getChild())) {
                result.add(childTuple.getChild());
            }
        }
        return result;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> getAllAscendantCategoryTuples() {
        return basicCategoryRelationshipTuples.allParents(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getAllAscendantCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple parentTuple : basicCategoryRelationshipTuples.allParents(this)) {
            if (!result.contains(parentTuple.getParent())) {
                result.add(parentTuple.getParent());
            }
        }
        return result;
    }


    @Programmatic
    public List<BasicCategoryRelationshipTuple> getAllTopParents() {
        return basicCategories.allTopParentTuples(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getTopCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple childTuple : basicCategoryRelationshipTuples.allTopParentTuples(this)) {
            if (!result.contains(childTuple.getParent())) {
                result.add(childTuple.getParent());
            }
        }
        return result;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> getAllFamilyCategoryTuples() {
        return basicCategoryRelationshipTuples.allTuplesOfFamily(this);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BasicCategory> getAllFamilyCategories() {
        List<BasicCategory> result = new ArrayList<>();
        for (BasicCategoryRelationshipTuple tuple : basicCategoryRelationshipTuples.allTuplesOfFamily(this)) {
            if (!result.contains(tuple.getParent())) {
                result.add(tuple.getParent());
            }
            if (!result.contains(tuple.getChild())) {
                result.add(tuple.getChild());
            }
        }
        return result;
    }


    @Override
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Category createChildCategory(final String uniqueCategoryName) {

        basicCategories.createBasicCategory(uniqueCategoryName, this);
        return this;
    }

    public String validateCreateChildCategory(final String uniqueCategoryName) {

        //check valid name
        for (BasicCategory bc : basicCategories.allBasicCategories()){
            if (uniqueCategoryName.toLowerCase().equals(bc.getName().toLowerCase())) {
                return "Name of basic category should be unique";
            }
        }

        return null;
    }


    @Override
    public Category makeCategoryAChild(
            @ParameterLayout(named = "Basic Category")
            @Parameter()
            final Category candidateCategoryToBeAddedInTree
    ) {
        basicCategoryRelationshipTuples.createTuple(this, (BasicCategory) candidateCategoryToBeAddedInTree);
        return this;
    }

    public List<Category> autoComplete0MakeCategoryAChild(String search) {
        List<Category> list = new ArrayList<>();
        for (BasicCategory cat : basicCategories.findByNameContains(search)) {
            if (!cat.getName().equals((this.getName()))) {
                list.add(cat);
            }
        }
        return list;
    }

    public String validateMakeCategoryAChild(final Category candidateCategoryToBeAddedInTree) {

        if (!checkedNoCircularRelationships(candidateCategoryToBeAddedInTree)) {
            return "Not possible because a circular relationship is not allowed: this category is a child of the one you try to establish as a child.";
        }

        for (BasicCategoryRelationshipTuple tuple : this.getDirectChildCategoryTuples()) {
            if (tuple.getParent() == this && tuple.getChild() == candidateCategoryToBeAddedInTree) {
                return "This category is a child already";
            }
        }

        return null;
    }

    @Override
    public Category disconnectFromParent(
            @ParameterLayout(named = "Basic Category")
            @Parameter()
            final Category parentToDisconnectFrom
    ){
        for (BasicCategoryRelationshipTuple tuple : basicCategoryRelationshipTuples.findByParentAndChild((BasicCategory) parentToDisconnectFrom, this)) {
            tuple.delete();
        }
        return this;
    }

    public List<Category> autoComplete0DisconnectFromParent(String search) {
        List<Category> list = new ArrayList<>();
        for (BasicCategory cat : this.getDirectParentCategories()) {
                list.add(cat);
        }
        return list;
    }


    @Override
    @Programmatic
    public boolean checkedNoCircularRelationships(Category candidateCategoryToBeAddedInTree) {

        BasicCategory candidate = (BasicCategory) candidateCategoryToBeAddedInTree;
        List<BasicCategory> categories = candidate.getAllDescendantCategories();

        if (categories.contains(this)) {
            return false;
        }

        return true;
    }

    @Action(semantics = SemanticsOf.SAFE)
    public boolean isTopcategory() {
        if (getAllAscendantCategories().size() > 0) {
            return false;
        }
        return true;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout
    public AverageRatingForCategory getAverageRatingForUser(
            @ParameterLayout(named = "user")
            final BasicUser basicUser) {

        Integer sum = 0;
        Integer numberOfRatings = basicRatings.findByReceiverAndCategory(basicUser, this).size();
        BigDecimal average;

        for (BasicRating r : basicRatings.findByReceiverAndCategory(basicUser, this)) {
            sum = sum + r.getFeedbackRating().ratingValue();
        }

        if (numberOfRatings > 0) {
            average = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(numberOfRatings));
        } else {
            average = BigDecimal.ZERO;
        }

        return new AverageRatingForCategory(basicUser, this,average,numberOfRatings);

    }

    public List<BasicUser> autoComplete0GetAverageRatingForUser(final String search) {
        List<BasicUser> list = new ArrayList<>();
        list.addAll(basicUsers.findBasicUserByNameContains(search));
        list.addAll(basicUsers.findBasicUserByEmailContains(search));
        return list;
    }

    @Inject
    private BasicCategories basicCategories;

    @Inject
    private BasicCategoryRelationshipTuples basicCategoryRelationshipTuples;

    @Inject
    private BasicRatings basicRatings;

    @Inject
    BasicUsers basicUsers;


}
