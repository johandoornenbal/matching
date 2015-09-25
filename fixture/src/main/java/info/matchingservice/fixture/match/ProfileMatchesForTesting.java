/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package info.matchingservice.fixture.match;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.Match.CandidateStatus;
import info.matchingservice.dom.Match.ProfileMatchingService;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.demand.TestDemandProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;

import javax.inject.Inject;

/**
 * Created by jodo on 06/05/15.
 */
public class ProfileMatchesForTesting extends ProfileMatchAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        //preqs
        executionContext.executeChild(this, new TestPersons());
        executionContext.executeChild(this, new TestDemandProfiles());
        executionContext.executeChild(this, new TestSupplyProfiles());
        executionContext.executeChild(this, new TestSupplyProfileElementsPersonProfiles());

        Person frans = persons.findPersons("Hals").get(0);
        Demand fransDemand = frans.getCollectDemands().first();
        Profile fransDemandProfile = fransDemand.getCollectDemandProfiles().first();
        Person gerard = persons.findPersons("Dou").get(0);
        Profile gerardSupplyProfile = gerard.getCollectSupplies().first().getCollectSupplyProfiles().first();
        Person rembrandt = persons.findPersons("Rijn").get(0);
        Profile rembrandtSupplyProfile = rembrandt.getCollectSupplies().first().getCollectSupplyProfiles().first();

        createProfileMatch(frans, gerard, fransDemandProfile, gerardSupplyProfile, "frans", CandidateStatus.CANDIDATE, executionContext);
        createProfileMatch(frans, rembrandt, fransDemandProfile, rembrandtSupplyProfile, "frans", CandidateStatus.CANDIDATE, executionContext);


    }

    @Inject
    Persons persons;

    @Inject
    ProfileMatchingService profileMatchingService;

}
