/*
 *
 *  Copyright 2014 YODO international projects and consultancy
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package nl.socrates.dom.party;

import java.util.List;

import org.joda.time.LocalDate;

import nl.socrates.dom.RegexValidation;
import nl.socrates.dom.SocratesDomainService;
import nl.socrates.dom.utils.StringUtils;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.annotation.RegEx;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

@DomainService(menuOrder = "20", repositoryFor = Person.class)
@Named("Personen")
public class Persons extends SocratesDomainService<Person> {

    public Persons() {
        super(Persons.class, Person.class);
    }

    // //////////////////////////////////////

    @ActionSemantics(Of.NON_IDEMPOTENT)
    @MemberOrder(name = "Contacten", sequence = "1")
    @Named("Nieuw persoon")
    public Person newPerson(
            final @Named("Referentie") @Optional @RegEx(validation=RegexValidation.Person.REFERENCE) String reference,
            final @Named("Voorletter(s)") @RegEx(validation=RegexValidation.Person.INITIALS) String initials,
            final @Named("Voornaam") String firstName,
            final @Named("Tussen") @Optional String middleName,
            final @Named("Achternaam") String lastName,
            final @Named("Doopnaam") @Optional String baptismalName,
            final @Named("Geslacht") PersonGenderType Gender,
            final @Named("Geboortedatum") LocalDate dateOfBirth,
            final @Named("Geboorteplaats") String placeOfBirth,
            final @Named("Nationaliteit") String Nationality) {
        final Person person = newTransientInstance(Person.class);
        person.setReference(reference);
        person.setInitials(initials);
        person.setLastName(lastName);
        person.setMiddleName(middleName);
        person.setFirstName(firstName);
        person.setBaptismalName(baptismalName);
        person.setDateOfBirth(dateOfBirth);
        person.setGender(Gender);
        person.setPlaceOfBirth(placeOfBirth);
        person.setNationality(Nationality);
        person.updating();
        person.setOwner(container.getUser().getName());
        persist(person);
        return person;
    }

    // //////////////////////////////////////

    @Prototype
    @ActionSemantics(Of.SAFE)
    @MemberOrder(name = "Contacten", sequence = "99.2")
    @Named("Alle personen")
    public List<Person> allPersons() {
        return allInstances();
    }
    
    public List<Person> findPersons(final String lastname) {
        return allMatches("matchByLastName", "lastName", StringUtils.wildcardToCaseInsensitiveRegex(lastname));
    }
    
    @com.google.common.eventbus.Subscribe 
    @Programmatic
    public void onPersonContactEvent(PersonContactEvent e) {
            //TODO: iets als test
        container.informUser("Event: " + e.getTestString() + " " + e.getContact().toString());
        findContact(e.getContact()).setLastContact(e.getContactOwner());
    }
    
    @Programmatic
    public Person findContact(final Person contact) {
        int index = this.allPersons().indexOf(contact);
        return this.allPersons().get(index);
    }

    @javax.inject.Inject
    private DomainObjectContainer container;
    
}
