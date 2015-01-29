/*
 *
 *  Copyright 2015 Yodo Int. Projects and Consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = OrganisationRole.class)
@Hidden
@DomainServiceLayout(named="Organisatie Rollen", menuOrder="20")
public class OrganisationRoles extends MatchingDomainService<OrganisationRole> {

    public OrganisationRoles() {
        super(OrganisationRoles.class, OrganisationRole.class);
    }
    
    public OrganisationRole newRole(final OrganisationRoleType role, final Organisation roleOwner){
        return newRole(role, roleOwner, currentUserName());
    }
    
    public String validateNewRole(final OrganisationRoleType role, final Organisation roleOwner){
        return validateNewRole(role, roleOwner, currentUserName());
    }
    
    public List<OrganisationRole> allRoles() {
        return container.allInstances(OrganisationRole.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public OrganisationRole newRole(final OrganisationRoleType role, final Organisation roleOwner, final String userName) {
        OrganisationRole newrole = newTransientInstance(OrganisationRole.class);
        newrole.setRole(role);
        newrole.setRoleOwner(roleOwner);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateNewRole(final OrganisationRoleType role, final Organisation roleOwner, final String userName){
        QueryDefault<OrganisationRole> query = 
                QueryDefault.create(
                        OrganisationRole.class, 
                    "findSpecificRole", 
                    "roleOwner", roleOwner,
                    "role", role);        
        return container.firstMatch(query) != null?
        "This role you already have"        
        :null;
    }
    
    //
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
