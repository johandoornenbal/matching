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

import info.matchingservice.dom.Actor.Person;

/**
 * implementation for class Services,
 */
public interface ServicesRepository {


    public List<Service> allServices();

    public List<Service> findServicesByDescription(String description);

    public List<Service> findServicesByDescriptionContains(String description);

    public List<Service> findServicesByType(ServiceType serviceType);

    public List<Service> findServicesByStatus(ServiceStatus serviceStatus);


    public List<Service> findServicesByOwner(Person owner);


    public List<Service> findServicesByUser(String username);

    public List<Service> findServicesByUUID (UUID uniqueItemId);



    //TODO
    // ? by date, place, price ?




}