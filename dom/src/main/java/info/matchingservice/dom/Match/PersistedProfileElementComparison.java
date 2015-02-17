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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileElementComparisonByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Match.PersistedProfileElementComparison "
                    + "WHERE ownedBy == :ownedBy")
})
@DomainObject(editing=Editing.DISABLED)
public class PersistedProfileElementComparison extends MatchingSecureMutableObject<PersistedProfileElementComparison>{
    
    public PersistedProfileElementComparison(){
        super("demandProfileElementOwner, uniqueItemId");
    }
    
    private Profile demandProfileElementOwner;

    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Profile getDemandProfileElementOwner() {
        return demandProfileElementOwner;
    }
    
    public void setDemandProfileElementOwner(final Profile owner) {
        this.demandProfileElementOwner = owner;
    }
    
    private ProfileElement demandProfileElement;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
    @javax.jdo.annotations.Column(allowsNull = "true")
    public ProfileElement getDemandProfileElement() {
        return demandProfileElement;
    }
    
    public void setDemandProfileElement(final ProfileElement initiator) {
        this.demandProfileElement = initiator;
    }
    
    private ProfileElement matchingSupplyProfileElement;
    
    @PropertyLayout(hidden=Where.EVERYWHERE)
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
    
    private Integer weight;
    
    @MemberOrder(sequence="11")
    @PropertyLayout(named="Gewicht")
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight){
    	this.weight = weight;
    }
    
	//** uniqueItemId **//
    private UUID uniqueItemId;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }
    
    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //-- uniqueItemId --//
    
    //helpers
    
    public String toString() {
        return getDemandProfileElement().toString() + " vs. " + getMatchingSupplyProfileElement().toString();
    }
    
    public int compareTo(PersistedProfileElementComparison that) {
        return ComparisonChain.start()
            .compare(this.calculatedMatchingValue, that.calculatedMatchingValue)
            .compare(this.matchingProfileElementOwner, that.matchingProfileElementOwner)
            .result();
    }
    
	//** ownedBy - Override for secure object **//
    private String ownedBy;
    
    @Override
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    @PropertyLayout(hidden=Where.NOWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }
    
    //** deletePersistedProfileElementComparison **//
    
    @Programmatic
    public void deletePersistedProfileElementComparison(){
    	
    	container.removeIfNotAlready(this);
    	
    }
    //-- deletePersistedProfileElementComparison --//
    
	//** INJECTIONS **//
    @javax.inject.Inject
    private DomainObjectContainer container;
	//-- INJECTIONS --//

}
