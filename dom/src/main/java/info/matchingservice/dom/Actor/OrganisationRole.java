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

import java.util.List;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findMyRoles", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.OrganisationRole "
                    + "WHERE roleOwner == :roleOwner"),
    @javax.jdo.annotations.Query(
            name = "findSpecificRole", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.OrganisationRole "
                    + "WHERE roleOwner == :roleOwner && role == :role"),
})
public class OrganisationRole extends Role {
    
    private Organisation roleOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Organisation getRoleOwner(){
        return roleOwner;
    }
    
    public void setRoleOwner(final Organisation roleOwner){
        this.roleOwner = roleOwner;
    }
    
    private OrganisationRoleType role;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public OrganisationRoleType getRole(){
        return role;
    }
    
    public void setRole(final OrganisationRoleType role) {
        this.role=role;
    }
    
    // Region //// Delete action //////////////////////////////
    public List<OrganisationRole> delete(
            @ParameterLayout(named="areYouSure")
            @Parameter(optional=Optionality.TRUE)
            boolean areYouSure,
            Organisation organisation
            ) {
        container.removeIfNotAlready(this);
        container.informUser("Rol verwijderd");
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findMyRoles",
                        "roleOwner", organisation);
        return container.allMatches(query);
    }
    
    public String validateDelete(boolean areYouSure, Organisation organisation) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region //// injections ///////////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;

}
