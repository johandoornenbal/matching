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
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = PersonRole.class, nature=NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
@DomainServiceLayout(menuOrder="20")
public class PersonRoles extends MatchingDomainService<PersonRole> {

    public PersonRoles() {
        super(PersonRoles.class, PersonRole.class);
    }
    
    public PersonRole createRole(final PersonRoleType role){
        return createRole(role, currentUserName());
    }
    
    public String validateCreateRole(final PersonRoleType role){
        return validateCreateRole(role, currentUserName());
    }
    
    @Programmatic
    public List<PersonRole> allRoles() {
        return container.allInstances(PersonRole.class);
    }
    
    // Region>helpers ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic //userName can now also be set by fixtures
    public PersonRole createRole(final PersonRoleType role, final String userName) {
        PersonRole newrole = newTransientInstance(PersonRole.class);
        newrole.setRole(role);
        newrole.setOwnedBy(userName);
        persist(newrole);
        return newrole;       
    }
    
    @Programmatic //userName can now also be set by fixtures
    public String validateCreateRole(final PersonRoleType role, final String userName){
        QueryDefault<PersonRole> query = 
                QueryDefault.create(
                        PersonRole.class, 
                    "findSpecificRole", 
                    "ownedBy", userName,
                    "role", role);        
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
    }
    
    //
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
