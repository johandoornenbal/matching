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

import java.util.UUID;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

import info.matchingservice.dom.MatchingSecureMutableObject;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@Queries({
        @Query(
                name = "findServiceOccurrencesByService", language = "JDOQL",
                value = "SELECT "
                        + "FROM info.matchingservice.dom.ProvidedServices.ServiceOccurrence "
                        + "WHERE service == :service")
})
@DomainObject()
public class ServiceOccurrence extends MatchingSecureMutableObject<ServiceOccurrence> {
    public ServiceOccurrence() {
        super("uniqueItemId");
    }


    //region > uniqueItemId (property)
    private UUID uniqueItemId;

    @Column(allowsNull = "false")
    public UUID getUniqueItemId() {
        return uniqueItemId;
    }


    public void setUniqueItemId(final UUID uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
    }
    //endregion

    //region > service (property)
    private Service service;

    @Column(allowsNull = "false")
    public Service getService() {
        return service;
    }

    public void setService(final Service service) {
        this.service = service;
    }
    //endregion


    //region > date (property)
    private LocalDate date;

    @Column(allowsNull = "false")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }
    //endregion


    //** ownedBy - Override for secure object **//
    private String ownedBy;


    @Override
    @Column(allowsNull = "false")
    @Property(editing= Editing.DISABLED)
    @PropertyLayout(hidden= Where.EVERYWHERE)
    public String getOwnedBy() {
        return ownedBy;
    }


    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //--ownedBy--//
}