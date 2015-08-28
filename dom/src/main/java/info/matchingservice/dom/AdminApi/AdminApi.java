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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.PersonRoles;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.DropDownForProfileElement;
import info.matchingservice.dom.Dropdown.DropDownForProfileElements;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.ProfileElementChoice;
import info.matchingservice.dom.Profile.ProfileElementChoices;
import info.matchingservice.dom.Profile.ProfileElementType;
import info.matchingservice.dom.Profile.ProfileElementWidgetType;

/**
 * Created by jodo on 13/06/15.
 */
@DomainService(nature = NatureOfService.VIEW_MENU_ONLY)
public class AdminApi {

    public Person activatePerson(
            final @ParameterLayout(named = "Person") Person person
    ){
        container.informUser(persons.activatePerson(person.getOwnedBy()));
        return person;
    }

    public List<Person> autoComplete0ActivatePerson(String search) {
        return persons.findPersons(search);
    }


    public Person deActivatePerson(
            final @ParameterLayout(named = "Person") Person person
    ){
        container.informUser(persons.deActivatePerson(person.getOwnedBy()));
        return person;
    }

    public List<Person> autoComplete0DeActivatePerson(String search) {
        return persons.findPersons(search);
    }

    public List<Person> findPersonsNotActive() {
        List<Person> foundPersons = new ArrayList<Person>();
        for (Person nonActivePerson : persons.allPersons()) {
            if (!nonActivePerson.getActivated()){
                foundPersons.add(nonActivePerson);
            }
        }
        return foundPersons;
    }

    public DropDownForProfileElement createEducationLevel(String level) {
        return dropDownForProfileElements.createDropDownForProfileELements(ProfileElementType.EDUCATION_LEVEL, level);
    }

    public ProfileElementChoice createProfileElementChoice(
            final @ParameterLayout(named = "demandOrSupply") DemandOrSupply demandOrSupply,
            final @ParameterLayout(named = "widgetType") ProfileElementWidgetType widgetType,
            final @ParameterLayout(named = "elementDescription") String elementDescription,
            final @ParameterLayout(named = "action") String action

    ) {
        return profileElementChoices.createProfileElementChoice(
                demandOrSupply,
                widgetType,
                elementDescription,
                action
        );
    }

    public List<ProfileElementChoice> allProfileElementChoices() {
        return profileElementChoices.allProfileElementChoices();
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Maak student")
    public Person addRoleStudent(Person person) {
        personRoles.createRole(PersonRoleType.STUDENT, person.getOwnedBy());
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Maak professional")
    public Person addRoleProfessional(Person person) {
        personRoles.createRole(PersonRoleType.PROFESSIONAL, person.getOwnedBy());
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Maak opdrachtgever")
    public Person addRolePrincipal(Person person) {
        personRoles.createRole(PersonRoleType.PRINCIPAL, person.getOwnedBy());
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Geen student meer")
    public Person deleteRoleStudent(Person person) {
        person.deleteRoleStudent(person.getOwnedBy());
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Geen professional meer")
    public Person deleteRoleProfessional(Person person) {
        person.deleteRoleProfessional(person.getOwnedBy());
        return person;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(named = "Geen opdrachtgever meer")
    public Person deleteRolePrincipal(Person person) {
        person.deleteRolePrincipal(person.getOwnedBy());
        return person;
    }

    @Inject
    private Persons persons;

    @Inject
    private DomainObjectContainer container;

    @Inject
    DropDownForProfileElements dropDownForProfileElements;

    @Inject
    ProfileElementChoices profileElementChoices;

    @Inject
    private PersonRoles personRoles;

}
