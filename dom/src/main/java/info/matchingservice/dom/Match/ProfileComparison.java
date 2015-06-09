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

import javax.inject.Inject;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.ViewModel;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Profile.Profile;


@ViewModel
public class ProfileComparison extends MatchingDomainObject<ProfileComparison> {
    
    public ProfileComparison() {
        super("demandProfile, uniqueItemId");
    }

    public ProfileComparison(Profile demandProfile, Profile matchingSupplyProfile, Integer calculatedMatchingValue) {
        super("matchInitiator");
        this.demandProfile = demandProfile;
        this.matchingSupplyProfile = matchingSupplyProfile;
        this.calculatedMatchingValue = calculatedMatchingValue;
    }
    
    private Profile demandProfile;

    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vac) {
        this.demandProfile = vac;
    }
    
    private Profile matchingSupplyProfile;

    public Profile getMatchingSupplyProfile() {
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile prof) {
        this.matchingSupplyProfile = prof;
    }
    
    private Integer calculatedMatchingValue;

    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer calculatedMatchingValue) {
        this.calculatedMatchingValue = calculatedMatchingValue;
    }
    
    //Region>Actions ////////////////////////////////////////////////////////
    
    public ProfileMatch SaveMatch(){
        return profileMatches.newProfileMatch(getDemandProfile().getDemandProfileOwner().getDemandOwner(), getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner(), getDemandProfile(), getMatchingSupplyProfile());
    }

    public String toString() {
        return getDemandProfile().toString() + " vs. " + getMatchingSupplyProfile().toString();
    }
    
    public Actor getProposedPerson() {
        return getMatchingSupplyProfile().getSupplyProfileOwner().getSupplyOwner();
    }

    public Actor getDemandingPerson() {return getDemandProfile().getActorOwner(); }
    
    public int compareTo(ProfileComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingSupplyProfile, that.matchingSupplyProfile)
            .result();
    }
    
    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;


}
