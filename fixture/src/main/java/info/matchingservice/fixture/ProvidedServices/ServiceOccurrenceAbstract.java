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

import info.matchingservice.dom.ProvidedServices.Service;
import info.matchingservice.dom.ProvidedServices.ServiceOccurrence;
import info.matchingservice.dom.ProvidedServices.ServiceOccurrences;

public abstract class ServiceOccurrenceAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);

    protected ServiceOccurrence createServiceOccurrence(
            final Service service,
            final LocalDate localDate,
            final String ownedBy,
            ExecutionContext executionContext){

        ServiceOccurrence serviceOccurrence =  serviceOccurrences.createServiceOccurence(service, localDate, ownedBy);

        return executionContext.add(this, serviceOccurrence);

    }

    @Inject
    ServiceOccurrences serviceOccurrences;

}

