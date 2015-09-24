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

/**
 * Created by jodo on 03/09/15.
 */
public class BasicUserFixtures extends BasicUserAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createBasicUser("user1", "johan@filternet.nl", "user1", executionContext);
        createBasicUser("user2", "inez@filternet.nl", "user2", executionContext);
        createBasicUser("user3", "piet@example.com", "user3", executionContext);
        createBasicUser("user4", "jan@example.com", "user4", executionContext);
        createBasicUser("user5", "klaas@example.com", "user5", executionContext);

    }

}
