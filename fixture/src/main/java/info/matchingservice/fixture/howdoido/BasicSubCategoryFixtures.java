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

import javax.inject.Inject;

import info.matchingservice.dom.Howdoido.BasicCategories;

/**
 * Created by jodo on 03/09/15.
 */
public class BasicSubCategoryFixtures extends BasicSubCategoryAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        //preqs
        executionContext.executeChild(this, new BasicTopCategoryFixtures());

        createSubCategory(basicCategories.findByName("academisch"), "alfa wetenschappen", executionContext);
        createSubCategory(basicCategories.findByName("academisch"), "beta wetenschappen", executionContext);

        createSubCategory(basicCategories.findByName("techniek"),"ict",executionContext);
        createSubCategory(basicCategories.findByName("techniek"),"electro techniek",executionContext);
        createSubCategory(basicCategories.findByName("techniek"),"werktuigbouw",executionContext);
        createSubCategory(basicCategories.findByName("techniek"),"bouwkunde",executionContext);

        createSubCategory(basicCategories.findByName("kunst"),"muziek",executionContext);
        createSubCategory(basicCategories.findByName("kunst"),"beeldende kunst",executionContext);
        createSubCategory(basicCategories.findByName("kunst"),"dans",executionContext);

        createSubCategory(basicCategories.findByName("sociaal"),"coaching",executionContext);
        createSubCategory(basicCategories.findByName("sociaal"),"uiterlijk",executionContext);
        createSubCategory(basicCategories.findByName("sociaal"),"communicatie",executionContext);
        createSubCategory(basicCategories.findByName("sociaal"),"evenementen",executionContext);

        createSubCategory(basicCategories.findByName("organisatie"),"commercieel",executionContext);
        createSubCategory(basicCategories.findByName("organisatie"),"ideeel",executionContext);
        createSubCategory(basicCategories.findByName("organisatie"),"vrijwilligers organisaties",executionContext);

        createAnySubCategory(basicCategories.findByName("organisatie"),"stichtingen",executionContext);

    }

    @Inject
    BasicCategories basicCategories;
}
