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

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.annotation.Where;

@ViewModel
public class ProfileElementComparison extends MatchingDomainObject<ProfileElementComparison>{
    
    public ProfileElementComparison(
            Profile demandProfileElementOwner, 
            ProfileElement demandProfileElement, 
            ProfileElement matchingSupplyProfileElement, 
            Profile matchingProfileElementOwner, 
            Actor matchingProfileElementActorOwner,
            Integer calculatedMatchingValue,
            Integer weight){
        super("demandProfileElementOwner");
        this.demandProfileElementOwner = demandProfileElementOwner;
        this.demandProfileElement = demandProfileElement;
        this.matchingSupplyProfileElement = matchingSupplyProfileElement;
        this.matchingProfileElementOwner = matchingProfileElementOwner;
        this.matchingProfileElementActorOwner = matchingProfileElementActorOwner;
        this.calculatedMatchingValue = calculatedMatchingValue;
        this.weight = weight;
    }
    
    private Profile demandProfileElementOwner;

    @PropertyLayout(hidden=Where.EVERYWHERE)
    public Profile getDemandProfileElementOwner() {
        return demandProfileElementOwner;
    }
    
    public void setDemandProfileElementOwner(final Profile owner) {
        this.demandProfileElementOwner = owner;
    }
    
    private ProfileElement demandProfileElement;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileElement getDemandProfileElement() {
        return demandProfileElement;
    }
    
    public void setDemandProfileElement(final ProfileElement initiator) {
        this.demandProfileElement = initiator;
    }
    
    private ProfileElement matchingSupplyProfileElement;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    public ProfileElement getMatchingSupplyProfileElement(){
        return matchingSupplyProfileElement;
    }
    
    public void setMatchingSupplyProfileElement(final ProfileElement element) {
        this.matchingSupplyProfileElement = element;
    }
    
    private Profile matchingProfileElementOwner;
    
    @MemberOrder(sequence="30")
    @PropertyLayout(named="Profiel")
    public Profile getMatchingProfileElementOwner(){
        return matchingProfileElementOwner;
    }
    
    public void setMatchingProfileElementOwner(final Profile owner) {
        this.matchingProfileElementOwner = owner;
    }
    
    private Actor matchingProfileElementActorOwner;
    
    @MemberOrder(sequence="20")
    @PropertyLayout(named="Eigenaar")
    public Actor getMatchingProfileElementActorOwner() {
        return matchingProfileElementActorOwner;
    }
    
    public void setMatchingProfileElementActorOwner(final Actor actor) {
        this.matchingProfileElementActorOwner = actor;
    }
    
    private Integer calculatedMatchingValue;
    
    @MemberOrder(sequence="10")
    @PropertyLayout(named="Matchingswaarde")
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    private Integer weight;
    
    @MemberOrder(sequence="11")
    @PropertyLayout(named="Gewicht")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight){
    	this.weight = weight;
    }
    
    //helpers
    
    public String toString() {
        return getDemandProfileElement().toString() + " vs. " + getMatchingSupplyProfileElement().toString();
    }
    
    public int compareTo(ProfileElementComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfileElementOwner, that.matchingProfileElementOwner)
            .result();
    }

}
