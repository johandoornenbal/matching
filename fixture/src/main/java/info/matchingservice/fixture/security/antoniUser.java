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

public class antoniUser extends AbstractUserFixtureScript {

    public static final String USER_NAME = "antoni";
    public static final String EMAIL = "antoni@example.com";

    @Override
    protected void execute(ExecutionContext executionContext) {
        ApplicationUser applicationUser = create(USER_NAME, EMAIL, AccountType.LOCAL, null, executionContext);
        applicationUser.updatePassword("pass");
        applicationUser.unlock();
    }

}
