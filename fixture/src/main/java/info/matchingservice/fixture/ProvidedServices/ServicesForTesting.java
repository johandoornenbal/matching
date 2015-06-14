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

package info.matchingservice.fixture.ProvidedServices;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.ProvidedServices.ServiceStatus;
import info.matchingservice.dom.ProvidedServices.ServiceType;
import info.matchingservice.fixture.actor.TestPersons;

/**
 * Created by jodo on 14/06/15.
 */
public class ServicesForTesting extends ServiceAbstract {

    @Override
    protected void execute(FixtureScript.ExecutionContext executionContext) {

        //preqs
        executeChild(new TestPersons(), executionContext);

        Person frans = persons.findPersons("Hals").get(0);
        Person gerard = persons.findPersons("Dou").get(0);
        Person rembrand = persons.findPersons("Rijn").get(0);

        createService(
                "Schildercursus",
                "Schilderlessen van een ervaren proffesional, leuk leerzaam",
                rembrand,
                ServiceType.SERVICE_COURSE,
                new BigDecimal(200),
                new BigDecimal(250),
                new BigDecimal(700),
                "8926PJ",
                executionContext
        )
                .setServiceStatus(ServiceStatus.OPEN);

        createService(
                "Raamwas cursussen",
                "Raamwas lessen van een ervaren proffesional, leuk leerzaam",
                gerard,
                ServiceType.SERVICE_COURSE,
                new BigDecimal(50),
                new BigDecimal(50),
                new BigDecimal(200),
                "8926PJ",
                executionContext).
                setServiceStatus(ServiceStatus.CLOSED);

        createService(
                "Cursus kleren vouwen",
                "Mega leerzaam! lessen voor een geordende kleerkast",
                frans,
                ServiceType.SERVICE_COURSE,
                new BigDecimal(140),
                new BigDecimal(50),
                new BigDecimal(300),
                "8926PJ",
                executionContext);

    }

    @Inject
    Persons persons;

}
