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

package info.matchingservice.dom;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.userreg.SecurityModuleAppUserRegistrationServiceAbstract;

/**
 * Created by jodo on 07/05/15.
 */
@DomainService
public class AppUserRegistrationService extends SecurityModuleAppUserRegistrationServiceAbstract {
    protected ApplicationRole getInitialRole() {
        return findRole("");
    }
    protected Set<ApplicationRole> getAdditionalInitialRoles() {
        return Collections.singleton(findRole(""));
    }
    private ApplicationRole findRole(final String roleName) {
        return applicationRoles.findRoleByName(roleName);
    }
    @Inject
    private ApplicationRoles applicationRoles;
}