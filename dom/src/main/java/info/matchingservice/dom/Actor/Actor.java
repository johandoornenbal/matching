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

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "PERSON_ID_UNQ", members = "uniqueActorId")
})
public abstract class Actor extends MatchingSecureMutableObject<Actor> {
    
	//** API: PROPERTIES **//
	
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
	
	//-- API: PROPERTIES --//
	
	//** API: COLLECTIONS **//
	
	//** demandsOfActor **//
    private SortedSet<Demand> collectDemands = new TreeSet<Demand>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandOwner", dependentElement = "true")
    public SortedSet<Demand> getCollectDemands() {
        return collectDemands;
    }
   
    public void setCollectDemands(final SortedSet<Demand> demandsOfActor) {
        this.collectDemands = demandsOfActor;
    }   
    //-- demandsOfActor --//
    
    //** suppliesOfActor **//
    private SortedSet<Supply> collectSupplies = new TreeSet<Supply>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "supplyOwner", dependentElement = "true")
    public SortedSet<Supply> getCollectSupplies() {
        return collectSupplies;
    }
   
    public void setCollectSupplies(final SortedSet<Supply> suppliesOfActor) {
        this.collectSupplies = suppliesOfActor;
    }
    //-- suppliesOfActor --//
    
    //** savedMatchesOfActor **//
    private SortedSet<ProfileMatch> collectSavedMatches = new TreeSet<ProfileMatch>();
    
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<ProfileMatch> getCollectSavedMatches() {
        return collectSavedMatches;
    }
    
    public void setCollectSavedMatches(final SortedSet<ProfileMatch> savedMatchesOfActor){
        this.collectSavedMatches = savedMatchesOfActor;
    }
    
    //Business rule: 
    //only visible for inner-circle
    public boolean hideCollectSavedMatches(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- savedMatchesOfActor --//
    
    //** assessmentsGivenByActor **//
    private SortedSet<Assessment> collectAssessmentsGivenByActor = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "assessmentOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Assessment> getCollectAssessmentsGivenByActor() {
        return collectAssessmentsGivenByActor;
    }
    
    public void setCollectAssessmentsGivenByActor(final SortedSet<Assessment> assessmentsGivenByActor){
        this.collectAssessmentsGivenByActor = assessmentsGivenByActor;
    }
    
    //Business rule: 
    //only visible for intimate-circle
    public boolean hideCollectAssessmentsGivenByActor(){
        return super.allowedTrustLevel(TrustLevel.INTIMATE);
    }
    //-- assessmentsGivenByActor --//
    
    //** assessmentsGivenByActor **//
    private SortedSet<Assessment> collectAssessmentsReceivedByActor = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "targetOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Assessment> getCollectAssessmentsReceivedByActor() {
        return collectAssessmentsReceivedByActor;
    }
    
    public void setCollectAssessmentsReceivedByActor(final SortedSet<Assessment> assessmentsReceivedByActor){
        this.collectAssessmentsReceivedByActor = assessmentsReceivedByActor;
    }
    
    // Business rule: 
    // only visible for inner-circle
    public boolean hideCollectAssessmentsReceivedByActor(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }    
    //-- assessmentsGivenByActor --//
    
    //-- END API: COLLECTIONS --//
    
    //** GENERIC OBJECT STUFF **//
    
    //** Constructor **//
    public Actor() {
        super("uniqueItemId");
    }
        
    //** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @Property(editing=Editing.DISABLED)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    //-- GENERIC OBJECT STUFF --//
    
    //** HELPERS **//
    //** HELPERS: generic object helpers **//
    public String title() {
        return getUniqueItemId().toString();
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    //-- HELPERS: generic object helpers --//
    
    //** HELPERS: programmatic actions **//   
    @Programmatic
    public Demand createDemand(
            final String demandDescription,
            final String demandSummary,
            final String demandStory,
            final Blob demandAttachment,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner, 
            final String ownedBy){
        return demands.createDemand(demandDescription, demandSummary, demandStory, demandAttachment, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, ownedBy);
    }
    
    @Programmatic
    public Profile createDemandAndProfile(
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner,
            final String demandProfileDescription,
            final Integer profileWeight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final String ownedBy){
        final Demand demand = demands.createDemand(demandDescription, "", "", null, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, ownedBy);
        return profiles.createDemandProfile(
        		demandProfileDescription, 
        		profileWeight, 
        		demandOrSupplyProfileStartDate, 
        		demandOrSupplyProfileEndDate, 
        		profileType, 
        		demand,
        		profileTypeMatchingRules.findProfileTypeMatchingRule("regel1"),
        		ownedBy
        		);
    }

    @Programmatic
    public Supply createSupply(
            final String supplyDescription,
            final Integer weight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner, 
            final String ownedBy){
        return supplies.createSupply(supplyDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, demandSupplyType, supplyOwner, ownedBy);
    }
    
    @Programmatic
    public Profile createSupplyAndProfile(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner,
            final String supplyProfileDescription,
            final Integer profileWeight,
            final LocalDate demandOrSupplyProfileStartDate,
            final LocalDate demandOrSupplyProfileEndDate,
            final ProfileType profileType,
            final String ownedBy){
        final Supply supply = supplies.createSupply(supplyDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, demandSupplyType, supplyOwner, ownedBy);
        return profiles.createSupplyProfile(supplyProfileDescription, profileWeight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, profileType, supply, ownedBy);
    }
    //-- HELPERS: programmatic actions --//
    
    //-- HELPERS --//

    //** INJECTIONS **//

    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Supplies supplies;
    
    @Inject
    Demands demands;    

    @Inject
    Profiles profiles;
    
    @Inject
    ProfileTypeMatchingRules profileTypeMatchingRules;
    
    
    //-- INJECTIONS --//
    
    
	//  /**
	//  * Generic new Demand
	//  * @param demandDescription
	//  * @param weight
	//  * @param demandSupplyType
	//  * @return
	//  */
	// @ActionLayout(hidden=Where.EVERYWHERE)
	// @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	// public Demand createDemand(
	//         final String demandDescription,
	//         final Integer weight,
	//         final DemandSupplyType demandSupplyType
	//         ) {
	//     return createDemand(demandDescription, "", "", null, weight, demandSupplyType, this, currentUserName());
	// }
	    
	//  /**
	//  * Generic new Supply
	//  * @param supplyDescription
	//  * @param weight
	//  * @param demandSupplyType
	//  * @return
	//  */
	// @ActionLayout(hidden=Where.EVERYWHERE)
	// @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	// public Supply createSupply(
	//         final String supplyDescription,
	//         final Integer weight,
	//         final DemandSupplyType demandSupplyType){
	//     return createSupply(supplyDescription, weight, demandSupplyType, this, currentUserName());
	// }
    
    
    
}