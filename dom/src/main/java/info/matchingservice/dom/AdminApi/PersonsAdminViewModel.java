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

package info.matchingservice.dom.AdminApi;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ViewModel;

import info.matchingservice.dom.Actor.Persons;

/**
 * Created by jodo on 13/06/15.
 */
//TODO: NOT IN USE; HOW TO GET THIS WORKING?

@ViewModel
public class PersonsAdminViewModel {

    PersonsAdminViewModel(){}

    PersonsAdminViewModel(String lastName, LocalDate dateCreated){
        this.lastName = lastName;
        this.dateCreated = dateCreated;
    }

    //region > lastName (property)
    private String lastName;

    @MemberOrder(sequence = "1")
    public String getLastname() {
        return lastName;
    }

    public void setLastname(final String lastName) {
        this.lastName = lastName;
    }
    //endregion

    //region > dateCreated (property)
    private LocalDate dateCreated;

    @MemberOrder(sequence = "2")
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }
    //endregion

    //region > person (property)
//    public Person getPerson() {
//        return persons.findPersons(lastName).get(0);
//    }
    //endregion

    @Inject
    Persons persons;
}
