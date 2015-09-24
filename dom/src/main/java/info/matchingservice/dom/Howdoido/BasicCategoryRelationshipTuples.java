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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 02/09/15.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class BasicCategoryRelationshipTuples extends MatchingDomainService<BasicCategoryRelationshipTuple>{

    public BasicCategoryRelationshipTuples() {
        super(BasicCategoryRelationshipTuples.class, BasicCategoryRelationshipTuple.class);
    }

    @Programmatic
    public void createTuple(final BasicCategory parent, final BasicCategory child) {

        BasicCategoryRelationshipTuple newTuple = newTransientInstance(BasicCategoryRelationshipTuple.class);
        newTuple.setParent(parent);
        newTuple.setChild(child);
        persistIfNotAlready(newTuple);

    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> findByParent(final BasicCategory parent) {
        return allMatches("findByParent", "parent", parent);
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> findByChild(final BasicCategory child) {
        return allMatches("findByChild", "child", child);
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> findByParentAndChild(final BasicCategory parent, final BasicCategory child) {
        return allMatches("findByParentAndChild", "parent", parent, "child", child);
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> allTuplesOfFamily(final BasicCategory category) {
        List<BasicCategoryRelationshipTuple> result = new ArrayList<>();

        // first find all the topParents of the family by checking the topparents of
        // children on the botton of any topParentTuple
        // then collect all the descending tuples of all the topParentTuples
        List<BasicCategoryRelationshipTuple> parents = category.getAllTopParents();

        BasicCategory anyTopparentCategory;

        if (parents.size() > 0) {

            anyTopparentCategory = parents.get(0).getParent();

        } else {

            //category is top category
            anyTopparentCategory = category;

        }

        List<BasicCategoryRelationshipTuple> topParents = new ArrayList<>();

        for (BasicCategoryRelationshipTuple childTuple : anyTopparentCategory.getAllDescendantCategoryTuples()) {

            for (BasicCategoryRelationshipTuple topTuple : childTuple.getChild().getAllTopParents()){

                if (!topParents.contains(topTuple)){
                    topParents.add(topTuple);
                }

            }

        }

        for (BasicCategoryRelationshipTuple tuple : topParents) {
            for (BasicCategoryRelationshipTuple familyMemberTuple : tuple.getParent().getAllDescendantCategoryTuples()) {
                if (!result.contains(familyMemberTuple)) {
                    result.add(familyMemberTuple);
                }
            }
        }

        return result;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> allChildren(final BasicCategory category) {
        List<BasicCategoryRelationshipTuple> result = new ArrayList<>();

        for (BasicCategoryRelationshipTuple childTuple : category.getDirectChildCategoryTuples()) {
            if (childTuple.getChild().getDirectChildCategoryTuples().size() > 0) {
                result.add(childTuple);
                result.addAll(allChildren(childTuple.getChild()));
            }
            else
            {
                result.add(childTuple);
            }
        }

        return result;
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> allParents(final BasicCategory category) {
        List<BasicCategoryRelationshipTuple> result = new ArrayList<>();

        for (BasicCategoryRelationshipTuple parentTuple : category.getDirectParentCategoryTuples()) {
            if (parentTuple.getParent().getDirectParentCategoryTuples().size() > 0) {
                result.add(parentTuple);
                result.addAll(allParents(parentTuple.getParent()));
            }
            else
            {
                result.add(parentTuple);
            }
        }

        return result;
    }


    @Programmatic
    public List<BasicCategoryRelationshipTuple> allTopParentTuples(final BasicCategory category) {

        List<BasicCategoryRelationshipTuple> result = new ArrayList<>();

        for (BasicCategoryRelationshipTuple parentTuple : category.getDirectParentCategoryTuples()) {
            if (parentTuple.getParent().getDirectParentCategoryTuples().size()>0) {
                result.addAll(allTopParentTuples(parentTuple.getParent()));
            } else {
                result.add(parentTuple);
            }
        }

        return result;
    }

}
