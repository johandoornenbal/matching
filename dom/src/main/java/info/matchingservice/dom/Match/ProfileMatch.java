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

package info.matchingservice.dom.Match;

import java.util.UUID;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

/**
 * It takes an action on a Demand Profile to create and persist a ProfileMatch instance
 * 
 *
 * @version $Rev$ $Date$
 */
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileMatchUnique", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.ProfileMatch "
                    + "WHERE ownedBy == :ownedBy && supplyCandidate == :vacancyCandidate && demandProfile == :vacancyProfile")                  
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileMatch extends MatchingSecureMutableObject<ProfileMatch> {

    public ProfileMatch() {
        super("ownedBy, supplyCandidate, demandProfile, candidateStatus, uniqueItemId");
    }
    
    public String title(){
        return "SAVED_MATCHING_PROFILE_OF " + this.getSupplyCandidate().title();
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
    
    private String ownedBy;
    
    @Override
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @Property(editing=Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    private Actor ownerActor;
    
    @PropertyLayout()
    @Property(editing=Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getOwnerActor() {
        return ownerActor;
    }
    
    public void setOwnerActor(final Actor ownerActor) {
        this.ownerActor = ownerActor;
    }
    
    private Actor supplyCandidate;
    
    @Property(editing=Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Actor getSupplyCandidate() {
        return supplyCandidate;
    }
    
    public void setSupplyCandidate(final Actor candidate) {
        this.supplyCandidate = candidate;
    }
    
    private Profile demandProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vacancyProfile){
        this.demandProfile = vacancyProfile;
    }
    
    private CandidateStatus candidateStatus;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @PropertyLayout()
    public CandidateStatus getCandidateStatus() {
        return candidateStatus;
    }
    
    public void setCandidateStatus(final CandidateStatus candidateStatus){
        this.candidateStatus = candidateStatus;
    }
    
    private Profile matchingSupplyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getMatchingSupplyProfile(){
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile matchingSupplyProfile){
        this.matchingSupplyProfile = matchingSupplyProfile;
    }
    
    //Region>Actions
    
    public ProfileMatch ChangeStatus(final CandidateStatus status) {
        setCandidateStatus(status);
        return this;
    }
    
    public CandidateStatus default0ChangeStatus(final CandidateStatus status){
        return getCandidateStatus();
    }
    
    public Profile deleteMatch(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optional=Optionality.TRUE)
            boolean confirmDelete
            ){
        container.removeIfNotAlready(this);
        return this.getDemandProfile();
    }
    
    public String validateDeleteMatch(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
// Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
