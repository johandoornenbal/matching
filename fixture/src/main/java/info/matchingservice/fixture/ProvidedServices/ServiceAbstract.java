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

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.ProvidedServices.Service;
import info.matchingservice.dom.ProvidedServices.ServiceOccurrence;
import info.matchingservice.dom.ProvidedServices.ServiceOccurrences;
import info.matchingservice.dom.ProvidedServices.ServiceType;
import info.matchingservice.dom.ProvidedServices.Services;

public abstract class ServiceAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);


    protected Service createService(
            final String description,
            final String summary,
            final Person owner,
            final ServiceType type,
            final BigDecimal priceEuro,
            final BigDecimal priceCredits,
            final BigDecimal priceStreet,
            final String postalCode,
            ExecutionContext executionContext){

        Service service = services.createService(
                description,
                summary,
                owner,
                type,
                priceEuro,
                priceCredits,
                priceStreet,
                postalCode,
                owner.getOwnedBy());

        return executionContext.add(this, service);


    }

    protected ServiceOccurrence createServiceOccurrence(
            final Service service,
            final LocalDate localDate,
            final String ownedBy,
            ExecutionContext executionContext){

        ServiceOccurrence serviceOccurrence =  serviceOccurrences.createServiceOccurence(service, localDate, ownedBy);

        return executionContext.add(this, serviceOccurrence);

    }

    @Inject
    Services services;

    @Inject
    ServiceOccurrences serviceOccurrences;

}

