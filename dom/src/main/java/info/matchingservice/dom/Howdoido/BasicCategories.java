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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import info.matchingservice.dom.MatchingDomainService;

/**
 * Created by jodo on 31/08/15.
 */
@DomainService(nature = NatureOfService.DOMAIN, repositoryFor = BasicCategory.class)
public class BasicCategories extends MatchingDomainService<BasicCategory> {

    public BasicCategories() {
        super(BasicCategories.class, BasicCategory.class);
    }

    public List<BasicCategory> allBasicCategories () {
        return allInstances(BasicCategory.class);
    }

    @Programmatic
    public BasicCategory createBasicCategory (final String name, final BasicCategory parent) {

        BasicCategory newCategory = newTransientInstance(BasicCategory.class);

        newCategory.setName(name.toLowerCase());
        persistIfNotAlready(newCategory);

        if (parent != null) {
            basicCategoryRelationshipTuples.createTuple(parent, newCategory);
        }

        return newCategory;

    }

    @Programmatic
    public List<BasicCategory> findByNameContains(final String name) {
        return allMatches("findBasicCategoryByNameContains", "name", name);
    }

    @Programmatic
    public BasicCategory findByName(final String name) {
        return firstMatch("findBasicCategoryByName", "name", name);
    }

    @Programmatic
    public List<BasicCategoryRelationshipTuple> allTopParentTuples(final BasicCategory category) {

        return basicCategoryRelationshipTuples.allTopParentTuples(category);
    }



    @Inject
    BasicCategoryRelationshipTuples basicCategoryRelationshipTuples;

}
