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

import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.util.TitleBuffer;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findMyOrganisations", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE ownedBy == :ownedBy"),
    @javax.jdo.annotations.Query(
            name = "matchOrganisationByOrganisationName", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE organisationName.matches(:organisationName)"),
    @javax.jdo.annotations.Query(
            name = "matchOrganisationByOrganisationNameContains", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Actor.Organisation "
                    + "WHERE organisationName.indexOf(:organisationName) >= 0")                    
})

@DomainObject(autoCompleteRepository=Organisations.class, autoCompleteAction="autoComplete")
public class Organisation extends Actor {
 
	//** API: PROPERTIES **//
	
	//** organisationName **//
    private String organisationName;
    
    @MemberOrder(sequence = "10")
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(final String fn) {
        this.organisationName = fn;
    }
    //-- organisationName --//
    
    //** roles **//
    @PropertyLayout(multiLine=2)
    public String getRoles() {
        TitleBuffer tb = new TitleBuffer();
        if (getIsPrincipal()) {
            if (!tb.toString().equals("")){
                tb.append(",");
            }
            tb.append(OrganisationRoleType.PRINCIPAL.title());
        }
        return tb.toString();
    }
    //-- roles --//
    
	//-- API: PROPERTIES --//
    
	//** API: COLLECTIONS **//
    
    // method CollectDemands() is on Actor
    public boolean hideDemands() {
        return !getIsPrincipal();
    }
    
	//-- API: COLLECTIONS --//
    
	//** API: ACTIONS **//
    
    //** createOrganisationSupply **//
    public Profile createOrganisationSupply(){
        return createSupplyAndProfile("ORGANISATION_SUPPLY_OF " + this.title(), 10, DemandSupplyType.ORGANISATION_DEMANDSUPPLY, this, "ORGANISATION_PROFILE", 10, null, null, ProfileType.ORGANISATION_PROFILE, currentUserName());
    }
    
    // Business rule:
    // Je kunt alleen een Organisatieprofiel aanmaken als je
    // - eigenaar bent
    // - nog geen persoonssupply hebt
    public boolean hideCreateOrganisationSupply(){
        return hideCreateOrganisationSupply("", this);
    }
    
    public String validateCreateOrganisationSupply(){
        return validateCreateOrganisationSupply("", this);
    }
    //-- createOrganisationSupply --//

	//-- API: ACTIONS --//

    
	//** HELPERS **//
    
    //** HELPERS: generic object helpers **//
    
    @Override
    public String title() {
        return getOrganisationName();
    }
    
    public String toString() {
        return organisationName;
    }
	
    //-- HELPERS: generic object helpers --//
	
    //** HELPERS: programmatic actions **//
    
    @Programmatic // now values can be set by fixtures
    public Boolean getIsPrincipal(Organisation ownerOrganisation) {
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findSpecificRole",
                        "roleOwner", ownerOrganisation,
                        "role", OrganisationRoleType.PRINCIPAL);
        return !container.allMatches(query).isEmpty();
    }
    
    @Programmatic // now values can be set by fixtures
    public void addRolePrincipal(String ownedBy) {
        roles.createRole(OrganisationRoleType.PRINCIPAL, this, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideAddRolePrincipal(Organisation ownerPerson, String ownedBy) {
        // if you are not the owner
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person already has role Principal
        return getIsPrincipal(ownerPerson); 
    }
    
    @Programmatic // now values can be set by fixtures
    public void deleteRolePrincipal(Organisation roleOwner) {
        QueryDefault<OrganisationRole> query =
                QueryDefault.create(
                        OrganisationRole.class,
                        "findSpecificRole",
                        "roleOwner", roleOwner,
                        "role", OrganisationRoleType.PRINCIPAL);
        OrganisationRole roleToDelete = container.firstMatch(query);
        roleToDelete.delete(true, this);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideDeleteRolePrincipal(Organisation ownerPerson, String ownedBy) {
        // if you are not the owner of person
        if (!ownerPerson.getOwnedBy().equals(ownedBy)){
            return true;
        }
        //if person has not role Principal
        return !getIsPrincipal(ownerPerson);
    }
    
    @Programmatic
    public boolean hideCreateOrganisationSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return true;
        }
        
        // if there is already a organsation Supply
        // TODO: deze werkt nog niet omdat een ownedBy op verschillende organisaties kan slaan van de owner
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByActorOwnerAndType", 
                    "supplyOwner", needOwner,
                    "supplyType", DemandSupplyType.ORGANISATION_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return true;
        }
        
        return false;
    }
    
    @Programmatic
    public String validateCreateOrganisationSupply(final String needDescription, final Actor needOwner){
        // if you are not the owner
        if (!needOwner.getOwnedBy().equals(currentUserName())){
            return "NOT_THE_OWNER";
        }
     
        // if there is already a personal Supply
        QueryDefault<Supply> query = 
                QueryDefault.create(
                        Supply.class, 
                    "findSupplyByActorOwnerAndType", 
                    "supplyOwner", needOwner,
                    "supplyType", DemandSupplyType.ORGANISATION_DEMANDSUPPLY);
        if (container.firstMatch(query) != null) {
            return "ONE_INSTANCE_AT_MOST";
        }
        
        return null;
    }
    
    
	//-- HELPERS: programmatic actions --// 
	//-- HELPERS --//
	//** INJECTIONS **//
	//-- INJECTIONS --//
    
	//** HIDDEN: PROPERTIES **//
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Boolean getIsPrincipal() {
        return getIsPrincipal(this);
    }
	
    //-- HIDDEN: PROPERTIES --//
	
    //** HIDDEN: ACTIONS **//
    
    @MemberOrder(sequence = "60")
    @ActionLayout(hidden=Where.ANYWHERE)
    public Organisation addRolePrincipal() {
        addRolePrincipal(currentUserName());
        return this;
    }
    
    public boolean hideAddRolePrincipal() {
        return hideAddRolePrincipal(this, currentUserName());
    }

    @ActionLayout(hidden=Where.ANYWHERE)
    @MemberOrder(sequence = "61")
    public Organisation deleteRolePrincipal() {
        deleteRolePrincipal(this);
        return this;
    }
    
    public boolean hideDeleteRolePrincipal() {
        return hideDeleteRolePrincipal(this, currentUserName());
    }

	//-- HIDDEN: ACTIONS --//

//    @MemberOrder(sequence = "100")
//    @CollectionLayout(hidden=Where.EVERYWHERE, render=RenderType.EAGERLY)
//    public List<OrganisationRole> getAllMyRoles() {
//        QueryDefault<OrganisationRole> query =
//                QueryDefault.create(
//                        OrganisationRole.class,
//                        "findMyRoles",
//                        "roleOwner", this);
//        return container.allMatches(query);
//    }
    

    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    private OrganisationRoles roles;
    
}
