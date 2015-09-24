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

import info.matchingservice.dom.Howdoido.BasicUsers;

/**
 * Created by jodo on 03/09/15.
 */
public class BasicRequestFixtures extends BasicRequestAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        //preqs
        executionContext.executeChild(this, new BasicTopCategoryFixtures());
        executionContext.executeChild(this, new BasicSubCategoryFixtures());
        executionContext.executeChild(this, new BasicUserFixtures());
        executionContext.executeChild(this, new BasicTemplateFixtures());

        createBasicRequest(
                basicUsers.findBasicUserByName("user1"),
                basicUsers.findBasicUserByName("user2"),
                basicUsers.findBasicUserByName("user1").getMyTemplates().first(),
                executionContext
        );

        createBasicRequest(
                basicUsers.findBasicUserByName("user2"),
                basicUsers.findBasicUserByName("user1"),
                basicUsers.findBasicUserByName("user2").getMyTemplates().first(),
                executionContext
        );

    }

    @Inject
    BasicUsers basicUsers;

}
