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

import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.DemandSupply.*;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.TrustedCircles.TrustedCircleConfigRepo;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.jdo.annotations.*;
import java.util.SortedSet;
import java.util.TreeSet;

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
public abstract class Actor extends MatchingSecureMutableObject<Actor> {
    
	//** API: PROPERTIES **//

    //region > activated (property)
    private boolean activated;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public boolean getActivated() {
        return activated;
    }

    public void setActivated(final boolean activated) {
        this.activated = activated;
    }
    //endregion

    //region > dateCreated (property)
    private LocalDate dateCreated;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    //endregion

    //-- API: PROPERTIES --//
	
	//** API: COLLECTIONS **//
	
	//** demandsOfActor **//
    private SortedSet<Demand> demands = new TreeSet<Demand>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandOwner", dependentElement = "true")
    public SortedSet<Demand> getDemands() {
        return demands;
    }
   
    public void setDemands(final SortedSet<Demand> demandsOfActor) {
        this.demands = demandsOfActor;
    }   
    //-- demandsOfActor --//
    
    //** suppliesOfActor **//
    private SortedSet<Supply> supplies = new TreeSet<Supply>();
    
    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "supplyOwner", dependentElement = "true")
    public SortedSet<Supply> getSupplies() {
        return supplies;
    }
   
    public void setSupplies(final SortedSet<Supply> suppliesOfActor) {
        this.supplies = suppliesOfActor;
    }
    //-- suppliesOfActor --//
    
    //** savedMatchesOfActor **//
    private SortedSet<ProfileMatch> savedMatches = new TreeSet<ProfileMatch>();
    
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<ProfileMatch> getSavedMatches() {
        return savedMatches;
    }
    
    public void setSavedMatches(final SortedSet<ProfileMatch> savedMatchesOfActor){
        this.savedMatches = savedMatchesOfActor;
    }
    
    //Business rule: 
    //only visible for inner-circle
    public boolean hideSavedMatches(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    //-- savedMatchesOfActor --//
    
    //** assessmentsGiven **//
    private SortedSet<Assessment> assessmentsGiven = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "assessmentOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Assessment> getAssessmentsGiven() {
        return assessmentsGiven;
    }
    
    public void setAssessmentsGiven(final SortedSet<Assessment> assessmentsGiven){
        this.assessmentsGiven = assessmentsGiven;
    }

    // business logic
    public boolean hideAssessmentsGiven(){
        return super.allowedTrustLevel(
                trustedCircleConfigRepo.propertyOrCollectionIsHiddenFor(
                        getOwnedBy(),
                        "assessmentsGiven",
                        "Actor")
        );
    }
    //-- assessmentsGiven --//
    
    //** assessmentsGiven **//
    private SortedSet<Assessment> assessmentsReceived = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "targetOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    public SortedSet<Assessment> getAssessmentsReceived() {
        return assessmentsReceived;
    }
    
    public void setAssessmentsReceived(final SortedSet<Assessment> assessmentsReceivedByActor){
        this.assessmentsReceived = assessmentsReceivedByActor;
    }

    // business logic
    public boolean hideAssessmentsReceived(){
        return super.allowedTrustLevel(
                trustedCircleConfigRepo.propertyOrCollectionIsHiddenFor(
                        getOwnedBy(),
                        "assessmentsReceived",
                        "Actor")
        );
    }    
    //-- assessmentsGiven --//
    
    //-- END API: COLLECTIONS --//
    
    //** GENERIC OBJECT STUFF **//
    
    //** Constructor **//
    public Actor() {
        super("ownedBy, dateCreated");
    }
        
    //** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout(hidden=Where.ALL_TABLES)
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
        String string = "Actor ownedBy ";
        return string.concat(getOwnedBy());
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
        return demandRepo.createDemand(demandDescription, demandSummary, demandStory, demandAttachment, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, ownedBy);
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
        final Demand demand = demandRepo.createDemand(demandDescription, "", "", null, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, ownedBy);
        return profiles.createDemandProfile(
        		demandProfileDescription, 
        		profileWeight, 
        		demandOrSupplyProfileStartDate, 
        		demandOrSupplyProfileEndDate, 
        		profileType, 
        		demand,
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
        return supplyRepo.createSupply(supplyDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, demandSupplyType, supplyOwner, ownedBy);
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
        final Supply supply = supplyRepo.createSupply(
        		supplyDescription, 
        		weight, 
        		demandOrSupplyProfileStartDate, 
        		demandOrSupplyProfileEndDate, 
        		demandSupplyType, 
        		supplyOwner, 
        		ownedBy);
        Profile newProfile = profiles.createSupplyProfile(
        		supplyProfileDescription, 
        		profileWeight, 
        		demandOrSupplyProfileStartDate, 
        		demandOrSupplyProfileEndDate, 
        		profileType, 
        		supply, 
        		ownedBy);
        
        // create Predicate Elements
        newProfile.createUseAgeElement(10);	// default: age is taken into account when matching 
        newProfile.createUseTimePeriodElement(10); // default: availibility is assumed
        
        return newProfile;
    }
    //-- HELPERS: programmatic actions --//
    
    //-- HELPERS --//

    //** INJECTIONS **//

    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Supplies supplyRepo;
    
    @Inject
    Demands demandRepo;

    @Inject
    Profiles profiles;

    @Inject
    private TrustedCircleConfigRepo trustedCircleConfigRepo;

    

    
}