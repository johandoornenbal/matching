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
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainObject;
import info.matchingservice.dom.Profile.Profile;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findProfileComparisonByDemandProfile", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Match.ProfileComparison "
                        + "WHERE demandProfile == :demandProfile"),
        @javax.jdo.annotations.Query(
                name = "findProfileComparisonBySupplyProfile", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Match.ProfileComparison "
                        + "WHERE matchingSupplyProfile == :supplyProfile"),
        @javax.jdo.annotations.Query(
                name = "findProfileComparisonByDemandAndSupplyProfile", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.Match.ProfileComparison "
                        + "WHERE demandProfile == :demandProfile && matchingSupplyProfile == :supplyProfile")
})
@DomainObject()
public class ProfileComparison extends MatchingDomainObject<ProfileComparison> {
    
    public ProfileComparison() {
        super("demandProfile, matchingSupplyProfile");
    }
    
    private Profile demandProfile;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing= Editing.DISABLED)
    public Profile getDemandProfile() {
        return demandProfile;
    }
    
    public void setDemandProfile(final Profile vac) {
        this.demandProfile = vac;
    }
    
    private Profile matchingSupplyProfile;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing=Editing.DISABLED)
    public Profile getMatchingSupplyProfile() {
        return matchingSupplyProfile;
    }
    
    public void setMatchingSupplyProfile(final Profile prof) {
        this.matchingSupplyProfile = prof;
    }
    
    private Integer calculatedMatchingValue;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing=Editing.DISABLED)
    public Integer getCalculatedMatchingValue() {
        return calculatedMatchingValue;
    }
    
    public void setCalculatedMatchingValue(final Integer calculatedMatchingValue) {
        this.calculatedMatchingValue = calculatedMatchingValue;
    }
    
    //Region>Actions ////////////////////////////////////////////////////////
    
    public ProfileMatch SaveMatch(){
        return profileMatches.newProfileMatch((Person) getDemandProfile().getDemand().getOwner(), (Person) getMatchingSupplyProfile().getSupply().getOwner(), getDemandProfile(), getMatchingSupplyProfile());
    }

    public String toString() {
        return getDemandProfile().toString() + " vs. " + getMatchingSupplyProfile().toString();
    }
    
    public Actor getProposedPerson() {
        return getMatchingSupplyProfile().getSupply().getOwner();
    }

    public Actor getDemandingPerson() {return getDemandProfile().getActorOwner(); }

    public void delete() {
        container.removeIfNotAlready(this);
        container.informUser("profileComparison deleted");
    }

    // Region>injections ////////////////////////////    
    @Inject
    private ProfileMatches profileMatches;

    @Inject
    private DomainObjectContainer container;


}
