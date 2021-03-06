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

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.ProfileMatchAssessment;
import info.matchingservice.dom.Assessment.ProfileMatchAssessments;
import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Profile.Profile;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import java.util.UUID;

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
                    + "WHERE ownedBy == :ownedBy && supplyCandidate == :vacancyCandidate && profile == :vacancyProfile"),
    @javax.jdo.annotations.Query(
            name = "findProfileMatchesByDemandProfile", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.ProfileMatch "
                    + "WHERE profile == :profile"),
    @javax.jdo.annotations.Query(
            name = "findProfileMatchesBySupplyCandidate", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.ProfileMatch "
                    + "WHERE supplyCandidate == :supplyCandidate"),
    @javax.jdo.annotations.Query(
            name = "findProfileMatchesByDemandProfileAndStatus", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.ProfileMatch "
                    + "WHERE profile == :profile && candidateStatus == :candidateStatus")
})
@DomainObject(editing=Editing.DISABLED)
public class ProfileMatch extends MatchingSecureMutableObject<ProfileMatch> {

    public ProfileMatch() {
        super("ownedBy, supplyCandidate, profile, candidateStatus, uniqueItemId");
    }
    
    public String title(){
        return "SAVED_MATCHING_PROFILE_OF " + this.getSupplyCandidate().title();
    }

    @Action(semantics = SemanticsOf.SAFE)
    public String getOID() {
        return JDOHelper.getObjectId(this).toString();
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

    //region > timeStamp (property)
    private LocalDateTime timeStamp;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    //endregion
    
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
    
    private Person owner;
    
    @PropertyLayout()
    @Property(editing=Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getOwner() {
        return owner;
    }
    
    public void setOwner(final Person owner) {
        this.owner = owner;
    }
    
    private Person supplyCandidate;
    
    @Property(editing=Editing.DISABLED)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Person getSupplyCandidate() {
        return supplyCandidate;
    }
    
    public void setSupplyCandidate(final Person candidate) {
        this.supplyCandidate = candidate;
    }
    
    private Profile profile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public Profile getProfile() {
        return profile;
    }
    
    public void setProfile(final Profile vacancyProfile){
        this.profile = vacancyProfile;
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
    
    @Action(semantics=SemanticsOf.IDEMPOTENT)
    public ProfileMatch updateCandidateStatus(final CandidateStatus status) {
        setCandidateStatus(status);
        return this;
    }
    
    public CandidateStatus default0UpdateCandidateStatus(final CandidateStatus status){
        return getCandidateStatus();
    }
    
    public Profile deleteMatch(
            @ParameterLayout(named="confirmDelete")
            @Parameter(optionality=Optionality.OPTIONAL)
            boolean confirmDelete
            ){
        //first delete profileMatchAssessments belonging to this profileMatch
        for (ProfileMatchAssessment profileMatchAssessment : profileMatchAssessments.findProfileMatchesAssessmentByProfileMatch(this)) {
            profileMatchAssessment.deleteAssessment(true);
        }
        container.removeIfNotAlready(this);
        return this.getProfile();
    }
    
    public String validateDeleteMatch(boolean confirmDelete) {
        return confirmDelete? null:"CONFIRM_DELETE";
    }
    
// Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;

    @Inject
    ProfileMatchAssessments profileMatchAssessments;

}
