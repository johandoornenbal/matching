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

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.ProvidedServices.Services;

/**
 * Created by jodo on 14/06/15.
 */
public class ServicesOccurrencesForTesting extends ServiceAbstract {

    @Override
    protected void execute(FixtureScript.ExecutionContext executionContext) {

        //preqs
        executeChild(new ServicesForTesting(), executionContext);

        createServiceOccurrence(
                services.findServicesByUser("frans").get(0),
                new LocalDate(2015, 01, 01),
                "frans",
                executionContext
        );

        createServiceOccurrence(
                services.findServicesByUser("frans").get(0),
                new LocalDate(2015,01,14),
                "frans",
                executionContext
        );

        createServiceOccurrence(
                services.findServicesByUser("frans").get(0),
                new LocalDate(2015,01,28),
                "frans",
                executionContext
        );

        createServiceOccurrence(
                services.findServicesByUser("gerard").get(0),
                new LocalDate(2015,01,10),
                "gerard",
                executionContext
        );

        createServiceOccurrence(
                services.findServicesByUser("gerard").get(0),
                new LocalDate(2015,01,17),
                "gerard",
                executionContext
        );


    }

    @Inject
    Services services;



}
