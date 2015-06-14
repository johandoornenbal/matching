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

package info.matchingservice.dom.ProvidedServices;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = ServiceOccurrence.class, nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ServiceOccurrences extends MatchingDomainService<ServiceOccurrence> {

    public ServiceOccurrences() {
        super(ServiceOccurrences.class, ServiceOccurrence.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<ServiceOccurrence> collectServiceOccurrences(Service service){

        QueryDefault<ServiceOccurrence> query =
                QueryDefault.create(
                        ServiceOccurrence.class,
                        "findServiceOccurrencesByService",
                        "service", service);
        return allMatches(query);
    }


    public ServiceOccurrence createServiceOccurence(
            final Service service,
            final LocalDate localDate){

        return createServiceOccurence(
                service,
                localDate,
                container.getUser().getName());

    }

    @Programmatic
    public ServiceOccurrence createServiceOccurence(
            final Service service,
            final LocalDate localDate,
            final String ownedBy){

        ServiceOccurrence serviceOccurrence = newTransientInstance(ServiceOccurrence.class);
        serviceOccurrence.setService(service);
        serviceOccurrence.setDate(localDate);
        serviceOccurrence.setOwnedBy(ownedBy);
        serviceOccurrence.setUniqueItemId(UUID.randomUUID());
        persistIfNotAlready(serviceOccurrence);
        return serviceOccurrence;

    }

    @Inject
    DomainObjectContainer container;



}
