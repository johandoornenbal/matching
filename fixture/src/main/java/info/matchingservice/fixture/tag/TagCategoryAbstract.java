/**
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.matchingservice.fixture.tag;

import javax.inject.Inject;

import info.matchingservice.dom.Tags.TagCategories;
import info.matchingservice.dom.Tags.TagCategory;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class TagCategoryAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected TagCategory createTagCategory(
            String tagCategoryDescription,
            ExecutionContext executionContext
            ){
        TagCategory newCat = tagCategories.createTagCategory(tagCategoryDescription);
        return executionContext.add(this, newCat);
    }
    
    @Inject
    TagCategories tagCategories;

}
