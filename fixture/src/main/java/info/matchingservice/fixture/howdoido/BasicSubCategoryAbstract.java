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

package info.matchingservice.fixture.howdoido;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Howdoido.BasicCategories;
import info.matchingservice.dom.Howdoido.BasicCategory;

public abstract class BasicSubCategoryAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected BasicCategory createSubCategory(
            BasicCategory parentCategory,
            String name,
            ExecutionContext executionContext
            ) {
        BasicCategory newSubCategory = basicCategories.createBasicCategory(name, parentCategory);
        return newSubCategory;
    }

    protected BasicCategory createAnySubCategory(
            BasicCategory parentCategory,
            String name,
            ExecutionContext executionContext
    ) {
        BasicCategory category = (BasicCategory) parentCategory.createChildCategory(name);
        for (BasicCategory cat : category.getDirectChildCategories()) {
            if (cat.getName().equals(name.toLowerCase())) {
                return cat;
            }
        }
        return null;
    }

    protected void makeCategoryAChild(
            BasicCategory parentCategory,
            BasicCategory childCategory,
            ExecutionContext executionContext
    ) {
        parentCategory.makeCategoryAChild(childCategory);
    }
    
    //region > injected services
    @javax.inject.Inject
    BasicCategories basicCategories;
}
