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

import info.matchingservice.dom.Actor.Person;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ViewModel;

import info.matchingservice.dom.Actor.Persons;

/**
 * Created by jodo on 13/06/15.
 */
@ViewModel
public class PersonRegistrationViewModel {



    PersonRegistrationViewModel(){}

    PersonRegistrationViewModel(final Person person){

        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getOwnedBy();
        this.dateCreated = person.getDateCreated();
        this.person = person;
    }


    //region > person (property)
    private Person person;

    @MemberOrder(sequence = "1")
    @PropertyLayout(named = "Profiel")
    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }
    //endregion


    //region > firstName (property)
    private String firstName;

    @MemberOrder(sequence = "2")
    @PropertyLayout(named = "Voornaam")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    //endregion
    //region > lastName (property)
    private String lastName;

    @MemberOrder(sequence = "3")
    @PropertyLayout(named = "Achternaam")
    public String getLastname() {
        return lastName;
    }

    public void setLastname(final String lastName) {
        this.lastName = lastName;
    }
    //endregion


    //region > email (property)
    private String email;

    @MemberOrder(sequence = "4")
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
    //endregion

    //region > dateCreated (property)
    private LocalDate dateCreated;

    @MemberOrder(sequence = "5")
    @PropertyLayout(named = "Registratie Datum")
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
