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
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.MatchingDomainService;

@DomainService(repositoryFor = Service.class, nature = NatureOfService.DOMAIN)
public class Services extends MatchingDomainService<Service> implements ServicesRepository {

    public Services() {
        super(Services.class, Service.class);
    }

    @Programmatic
    public Service createService(
            @ParameterLayout(named = "description")
            final String description,
            @ParameterLayout(named = "summary")
            final String summary,
            @ParameterLayout(named = "owner")
            final Person owner,
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

        return createService(
                description,
                summary,
                owner,
                type,
                priceEuro,
                priceCredits,
                priceStreet,
                postalCode,
                currentUsername()
        );
    }

    @Programmatic
    public Service createService(
            final String description,
            final String summary,
            final Person owner,
            final ServiceType type,
            final BigDecimal priceEuro,
            final BigDecimal priceCredits,
            final BigDecimal priceStreet,
            final String postalCode,
            final String ownedBy
    ){

        Service service = newTransientInstance(Service.class);

        service.setServiceDescription(description);
        service.setServiceSummary(summary);
        service.setServiceOwner(owner);
        service.setServiceType(type);
        service.setPriceEuro(priceEuro);
        service.setPriceCredits(priceCredits);
        service.setPriceStreet(priceStreet);
        service.setPostalCode(postalCode);
        service.setServiceStatus(ServiceStatus.OPEN); //default
        service.setUniqueItemId(UUID.randomUUID());

        service.setOwnedBy(ownedBy);

        persistIfNotAlready(service);
        return service;



    }

    //overridden from the service repo interface
    @Override
    @Programmatic
    public List<Service> findServicesByOwner(Person owner){

        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByOwner",
                        "serviceOwner", owner);
        return allMatches(query);


    }

    @Override public List<Service> findServicesByUser(final String username) {
        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByOwnedBy",
                        "ownedBy", username);
        return allMatches(query);

    }

    @Override
    @Programmatic
    public List<Service> findServicesByUUID(UUID uniqueItemId) {

        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByUniqueItemId",
                        "uniqueItemId", uniqueItemId);
        return allMatches(query);

    }



    @Override
    @Programmatic
    public List<Service> allServices(){

        return allInstances();

    }

    @Override
    @Programmatic
    public List<Service> findServicesByDescription(String description) {


        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByDescription",
                        "serviceDescription", description);
        return allMatches(query);

    }

    @Override
    @Programmatic
    public List<Service> findServicesByDescriptionContains(String description) {

        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByDescriptionContains",
                        "serviceDescription", description);
        return allMatches(query);


    }

    @Override
    @Programmatic
    public List<Service> findServicesByType(ServiceType serviceType) {

        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByType",
                        "serviceType", serviceType);
        return allMatches(query);

    }

    @Override
    @Programmatic
    public List<Service> findServicesByStatus(ServiceStatus serviceStatus) {

        QueryDefault<Service> query =
                QueryDefault.create(
                        Service.class,
                        "findServiceByStatus",
                        "serviceStatus", serviceStatus);
        return allMatches(query);

    }


    private String currentUsername(){


        return container.getUser().getName();
    }

    @Inject
    DomainObjectContainer container;



}