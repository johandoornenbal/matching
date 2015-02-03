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
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;

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
    
    
    public Actor() {
        super("uniqueItemId");
    }
        
    public String title() {
        return getUniqueItemId().toString();
    }
    
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
    
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    
    
    //Region> DEMANDS /////////////////////////////////////////////////////////////
    
    private SortedSet<Demand> myDemands = new TreeSet<Demand>();
    
    @CollectionLayout(named="Vraag", render=RenderType.EAGERLY)
    @Persistent(mappedBy = "demandOwner", dependentElement = "true")
    public SortedSet<Demand> getMyDemands() {
        return myDemands;
    }
   
    public void setMyDemands(final SortedSet<Demand> myDemands) {
        this.myDemands = myDemands;
    }
    
    /**
     * Generic new Demand
     * @param demandDescription
     * @param weight
     * @param demandSupplyType
     * @return
     */
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Demand newDemand(
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType
            ) {
        return newDemand(demandDescription, weight, demandSupplyType, this, currentUserName());
    }
    
    //helper
    @Programmatic
    public Demand newDemand(
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner, 
            final String ownedBy){
        return demands.newDemand(demandDescription, weight, demandSupplyType, demandOwner, ownedBy);
    }
    
    @Programmatic
    public Profile newDemandAndProfile(
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner,
            final String demandProfileDescription,
            final Integer profileWeight,
            final ProfileType profileType,
            final String ownedBy){
        final Demand demand = demands.newDemand(demandDescription, weight, demandSupplyType, demandOwner, ownedBy);
        return profiles.newDemandProfile(demandProfileDescription, profileWeight, profileType, demand, ownedBy);
    }
    
    
    //Region> SUPPLIES /////////////////////////////////////////////////////////////

    private SortedSet<Supply> mySupplies = new TreeSet<Supply>();
    
    @CollectionLayout(named="Aanbod", render=RenderType.EAGERLY)
    @Persistent(mappedBy = "supplyOwner", dependentElement = "true")
    public SortedSet<Supply> getMySupplies() {
        return mySupplies;
    }
   
    public void setMySupplies(final SortedSet<Supply> supplies) {
        this.mySupplies = supplies;
    }
    
    /**
     * Generic new Supply
     * @param supplyDescription
     * @param weight
     * @param demandSupplyType
     * @return
     */
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Supply newSupply(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType){
        return newSupply(supplyDescription, weight, demandSupplyType, this, currentUserName());
    }
    
    //helper
    @Programmatic
    public Supply newSupply(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner, 
            final String ownedBy){
        return supplies.newSupply(supplyDescription, weight, demandSupplyType, supplyOwner, ownedBy);
    }
    
    @Programmatic
    public Profile newSupplyAndProfile(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner,
            final String supplyProfileDescription,
            final Integer profileWeight,
            final ProfileType profileType,
            final String ownedBy){
        final Supply supply = supplies.newSupply(supplyDescription, weight, demandSupplyType, supplyOwner, ownedBy);
        return profiles.newSupplyProfile(supplyProfileDescription, profileWeight, profileType, supply, ownedBy);
    }
    
    
    //END Region> SUPPLIES /////////////////////////////////////////////////////////////
    
    //Region> Saved Matches /////////////////////////////////////////////////////////////
    
    private SortedSet<ProfileMatch> mySavedMatches = new TreeSet<ProfileMatch>();
    
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @CollectionLayout(named="Mijn bewaarde 'matches'", render=RenderType.EAGERLY)
    public SortedSet<ProfileMatch> getMySavedMatches() {
        return mySavedMatches;
    }
    
    public void setMySavedMatches(final SortedSet<ProfileMatch> mySavedMatches){
        this.mySavedMatches = mySavedMatches;
    }
    
    public boolean hideMySavedMatches(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }
    
    //END Region> Saved Matches /////////////////////////////////////////////////////////////
    
    //Region> Assessments Given  /////////////////////////////////////////////////////////////
    
    private SortedSet<Assessment> assessmentsGivenByMe = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @CollectionLayout(named="Feedback die ik gegeven heb", render=RenderType.EAGERLY)
    public SortedSet<Assessment> getAssessmentsGivenByMe() {
        return assessmentsGivenByMe;
    }
    
    public void setAssessmentsGivenByMe(final SortedSet<Assessment> assessmentsGivenByMe){
        this.assessmentsGivenByMe = assessmentsGivenByMe;
    }
    
    public boolean hideAssessmentsGivenByMe(){
        return super.allowedTrustLevel(TrustLevel.INTIMATE);
    }
    
    //Region> Assessments Received  /////////////////////////////////////////////////////////////
    
    private SortedSet<Assessment> assessmentsReceivedByMe = new TreeSet<Assessment>();
    
    @Persistent(mappedBy = "targetOwnerActor", dependentElement = "true")
    @CollectionLayout(named="Feedback die ik ontvangen heb", render=RenderType.EAGERLY)
    public SortedSet<Assessment> getAssessmentsReceivedByMe() {
        return assessmentsReceivedByMe;
    }
    
    public void setAssessmentsReceivedByMe(final SortedSet<Assessment> assessmentsReceivedByMe){
        this.assessmentsReceivedByMe = assessmentsReceivedByMe;
    }
    
    public boolean hideAssessmentsReceivedByMe(){
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }    
    
    //END Region> ASSESSMENTS /////////////////////////////////////////////////////////////
    
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Supplies supplies;
    
    @Inject
    Demands demands;    

    @Inject
    Profiles profiles;
    
}