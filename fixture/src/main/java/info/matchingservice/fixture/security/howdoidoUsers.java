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

package info.matchingservice.fixture.security;

import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;

public class howdoidoUsers extends AbstractUserFixtureScript {

    public static final String USER_NAME = "user";
    public static final String EMAIL = "@example.com";

    protected void execute(ExecutionContext executionContext) {
        for (Integer i =1; i <= 5; i++){
            ApplicationUser applicationUser = create(
                    USER_NAME.concat(i.toString()),
                    USER_NAME.concat(i.toString()).concat(EMAIL),
                    AccountType.LOCAL, null,
                    executionContext);
            applicationUser.updatePassword(USER_NAME.concat(i.toString()).concat(EMAIL));
            applicationUser.unlock();


        }
    }

}
