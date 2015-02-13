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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.ViewModel;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ElementComparisonPersisted extends MatchingDomainObject<ElementComparisonPersisted>{
    
    public ElementComparisonPersisted(){
        super("demandProfileElementOwner, matchingSupplyProfileElement");     
    }
    
    private Profile demandProfileElementOwner;

//    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Profile getDemandProfileElementOwner() {
        return demandProfileElementOwner;
    }
    
    public void setDemandProfileElementOwner(final Profile owner) {
        this.demandProfileElementOwner = owner;
    }
    
    private ProfileElement demandProfileElement;
    
//    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public ProfileElement getDemandProfileElement() {
        return demandProfileElement;
    }
    
    public void setDemandProfileElement(final ProfileElement initiator) {
        this.demandProfileElement = initiator;
    }
    
    private ProfileElement matchingSupplyProfileElement;
    
//    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public ProfileElement getMatchingSupplyProfileElement(){
        return matchingSupplyProfileElement;
    }
    
    public void setMatchingSupplyProfileElement(final ProfileElement element) {
        this.matchingSupplyProfileElement = element;
    }
    
    private Profile matchingProfileElementOwner;
    
    @MemberOrder(sequence="30")
    @PropertyLayout(named="Profiel")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Profile getMatchingProfileElementOwner(){
        return matchingProfileElementOwner;
    }
    
    public void setMatchingProfileElementOwner(final Profile owner) {
        this.matchingProfileElementOwner = owner;
    }
    
    private Actor matchingProfileElementActorOwner;
    
    @MemberOrder(sequence="20")
    @PropertyLayout(named="Eigenaar")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Actor getMatchingProfileElementActorOwner() {
        return matchingProfileElementActorOwner;
    }
    
    public void setMatchingProfileElementActorOwner(final Actor actor) {
        this.matchingProfileElementActorOwner = actor;
    }
    
    private Integer calculatedMatchingValue;
    
    @MemberOrder(sequence="10")
    @PropertyLayout(named="Matchingswaarde")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer matchingvalue) {
        this.calculatedMatchingValue = matchingvalue;
    }
    
    //helpers
    
    public String toString() {
//        return getDemandProfileElement().toString() + " vs. " + getMatchingSupplyProfileElement().toString();
    		return "PersistedElementMatch";
    }
    
    public int compareTo(ElementComparisonPersisted that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfileElementOwner, that.matchingProfileElementOwner)
            .result();
    }

}
