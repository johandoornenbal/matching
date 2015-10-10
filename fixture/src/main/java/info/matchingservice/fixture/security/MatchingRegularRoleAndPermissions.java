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

import org.isisaddons.module.security.dom.permission.ApplicationPermissionMode;
import org.isisaddons.module.security.dom.permission.ApplicationPermissionRule;
import org.isisaddons.module.security.seed.scripts.AbstractRoleAndPermissionsFixtureScript;

import info.matchingservice.dom.Configs;
import info.matchingservice.dom.MatchingService;
import info.matchingservice.fixture.MatchingDemoFixture;

public class MatchingRegularRoleAndPermissions extends AbstractRoleAndPermissionsFixtureScript {

    public static final String ROLE_NAME = "matching-regular-role";

    public MatchingRegularRoleAndPermissions() {
        super(ROLE_NAME, "Read/write access to matching dom");
    }

    @Override
    protected void execute(ExecutionContext executionContext) {
        newPackagePermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                MatchingService.class.getPackage().getName(),
                MatchingDemoFixture.class.getPackage().getName(),
                "org.isisaddons.module.devutils"
                );
        newClassPermissions(
                ApplicationPermissionRule.VETO,
                ApplicationPermissionMode.VIEWING,
                Configs.class);
        newClassPermissions(ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                org.isisaddons.module.security.app.user.MeService.class
        );
        newClassPermissions(ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.VIEWING,
                org.isisaddons.module.security.dom.user.ApplicationUser.class
        );
        newMemberPermissions(
                ApplicationPermissionRule.ALLOW,
                ApplicationPermissionMode.CHANGING,
                org.isisaddons.module.security.dom.user.ApplicationUser.class,
                "updatePassword"
        );

    }

}
