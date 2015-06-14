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

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.MatchingDomainService;

@DomainService(nature= NatureOfService.VIEW)
public class ServiceContributions extends MatchingDomainService<Service> {


    public ServiceContributions(){
        super(ServiceContributions.class, Service.class);

    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    public List<Service> allServices() {
        return services.allServices();
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT, restrictTo = RestrictTo.PROTOTYPING)
    public Service createService(
            @ParameterLayout(named = "description")
            final String description,
            @ParameterLayout(named = "summary")
            final String summary,
            @ParameterLayout(named = "type")
            final ServiceType type,
            @ParameterLayout(named = "priceEuro")
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal priceEuro,
            @ParameterLayout(named = "priceCredits")
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal priceCredits,
            @ParameterLayout(named = "priceStreet")
            @Parameter(optionality = Optionality.OPTIONAL)
            final BigDecimal priceStreet,
            @ParameterLayout(named = "postalCode")
            @Parameter(optionality = Optionality.OPTIONAL)
            final String postalCode
    ){
        return services.createService(
                description,
                summary,
                persons.findPersonUnique(container.getUser().getName()),
                type,
                priceEuro,
                priceCredits,
                priceStreet,
                postalCode
                );
    }




//    @Action(semantics = SemanticsOf.SAFE)
//    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
//    @CollectionLayout(render = RenderType.EAGERLY)
//    public List<ServiceOccurrence> collectOccurrences(Service service){
//
//        return serviceOccurrences.findServiceOccurrencesByService(service);
//
//    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Service createServiceOccurence(final Service service,
            final @ParameterLayout(named = "Date")LocalDate localDate){

//        serviceOccurrences.createServiceOccurence(service, localDate);
        return service;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Person> collectSuppliers(final Service service){

        return services.findSuppliersByService(service);



    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Person> collectPublic(final Service service){

        return services.findPublicByService(service);



    }



    //** INJECT ** //

    @Inject
    Services services;

    @Inject
    Persons persons;

    @Inject
    DomainObjectContainer container;


//    @Inject
//    ServiceOccurrences serviceOccurrences;


    //-- INJECT -- //






}
