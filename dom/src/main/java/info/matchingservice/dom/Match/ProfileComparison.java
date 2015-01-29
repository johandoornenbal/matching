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

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.annotation.Where;


@ViewModel
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
public class ProfileComparison extends MatchingDomainObject<ProfileComparison> {
    
    public ProfileComparison() {
        super("uniqueItemId, demandProfile");
    }

    public ProfileComparison(Profile demandProfile, Profile matchingSupplyProfile, Integer value) {
        super("matchInitiator");
        this.demandProfile = demandProfile;
        this.matchingSupplyProfile = matchingSupplyProfile;
        this.calculatedMatchingValue = value;
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
    
    private Profile demandProfile;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vac) {
        this.demandProfile = vac;
    }
    
    private Profile matchingSupplyProfile;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Profile getMatchingSupplyProfile() {
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile prof) {
        this.matchingSupplyProfile = prof;
    }
    
    private Integer calculatedMatchingValue;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    //Region>Actions ////////////////////////////////////////////////////////
    
    public ProfileMatch SaveMatch(){
        return profileMatches.newProfileMatch(getDemandProfile().getDemandProfileOwner().getDemandOwner(), getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner(), getDemandProfile(), getMatchingSupplyProfile());
    }
//    
//    //TODO: uitbreiden met controle (en hideXxx ) of er al een save is gemaakt met deze kenmerken...
//    // Hide if not owner or if already saved match
//    public boolean hideSaveMatch() {
//        QueryDefault<ProfileMatch> query = 
//                QueryDefault.create(
//                        ProfileMatch.class, 
//                    "findProfileMatchUnique", 
//                    "ownedBy", currentUserName(),
//                    "vacancyCandidate", getMatchingSupplyProfile().getSupplyProfileOwner(),
//                    "vacancyProfile", getDemandProfile());
//        return !getDemandProfile().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName()) || container.firstMatch(query) != null;
//    }
//    
//    public String validateSaveMatch() {
//        QueryDefault<ProfileMatch> query = 
//                QueryDefault.create(
//                        ProfileMatch.class, 
//                    "findProfileMatchUnique", 
//                    "ownedBy", currentUserName(),
//                    "vacancyCandidate", getMatchingSupplyProfile().getSupplyProfileOwner(),
//                    "vacancyProfile", getDemandProfile());
//        if (container.firstMatch(query) != null) {
//            return "You already saved this candidate for this vacancy";
//        }
//        if (!getDemandProfile().getDemandProfileOwner().getDemandOwner().getOwnedBy().equals(currentUserName())){
//            return "Sorry, you are not the owner of this match";
//        } else {
//            return null;
//        }
//            
//    }
    
    
    //Region>Helpers ////////////////////////////////////////////////////////
//    private String currentUserName() {
//        return container.getUser().getName();
//    }
    
    public String toString() {
        return getDemandProfile().toString() + " vs. " + getMatchingSupplyProfile().toString();
    }
    
    public Actor getProposedPerson() {
        return getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner();
    }    
    
    public int compareTo(ProfileComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingSupplyProfile, that.matchingSupplyProfile)
            .result();
    }
    
    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;
    
//    @javax.inject.Inject
//    private DomainObjectContainer container;

}
