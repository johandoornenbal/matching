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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.value.Blob;

import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.TrustedCircles.TrustedCircleConfigRepo;
import lombok.Getter;
import lombok.Setter;

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

    public Actor() {
        super("ownedBy, dateCreated");
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @Getter @Setter
    private boolean activated;


    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @Getter @Setter
    private LocalDate dateCreated;


    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "owner", dependentElement = "true")
    @Getter @Setter
    private SortedSet<Demand> demands = new TreeSet<Demand>();

    @CollectionLayout(render=RenderType.EAGERLY)
    @Persistent(mappedBy = "owner", dependentElement = "true")
    @Getter @Setter
    private SortedSet<Supply> supplies = new TreeSet<Supply>();


    @Persistent(mappedBy = "owner", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    @Getter @Setter
    private SortedSet<ProfileMatch> savedMatches = new TreeSet<ProfileMatch>();

    //Business rule: 
    //only visible for inner-circle
    public boolean hideSavedMatches(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }

    @Persistent(mappedBy = "assessmentOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    @Getter @Setter
    private SortedSet<Assessment> assessmentsGiven = new TreeSet<Assessment>();

    // business logic
    public boolean hideAssessmentsGiven(){
        return super.allowedTrustLevel(
                trustedCircleConfigRepo.propertyOrCollectionIsHiddenFor(
                        getOwnedBy(),
                        "assessmentsGiven",
                        "Actor")
        );
    }

    @Persistent(mappedBy = "targetOwnerActor", dependentElement = "true")
    @CollectionLayout(render=RenderType.EAGERLY)
    @Getter @Setter
    private SortedSet<Assessment> assessmentsReceived = new TreeSet<Assessment>();
    
    // business logic
    public boolean hideAssessmentsReceived(){
        return super.allowedTrustLevel(
                trustedCircleConfigRepo.propertyOrCollectionIsHiddenFor(
                        getOwnedBy(),
                        "assessmentsReceived",
                        "Actor")
        );
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

    public String title() {
        String string = "Actor ownedBy ";
        return string.concat(getOwnedBy());
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }

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
            final String imageUrl,
            final String ownedBy){
        return demandRepo.createDemand(demandDescription, demandSummary, demandStory, demandAttachment, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, imageUrl, ownedBy);
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
            final String imageUrl,
            final String ownedBy){
        final Demand demand = demandRepo.createDemand(demandDescription, "", "", null, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, imageUrl, ownedBy);
        return profiles.createDemandProfile(
        		demandProfileDescription, 
        		profileWeight, 
        		demandOrSupplyProfileStartDate, 
        		demandOrSupplyProfileEndDate, 
        		profileType,
                imageUrl,
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
            final String imageUrl,
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
                imageUrl,
        		supply, 
        		ownedBy);
        
        // create Predicate Elements
        newProfile.createUseAgeElement(10);	// default: age is taken into account when matching 
        newProfile.createUseTimePeriodElement(10); // default: availibility is assumed
        
        return newProfile;
    }

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