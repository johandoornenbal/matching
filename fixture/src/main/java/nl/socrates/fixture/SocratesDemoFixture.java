package nl.socrates.fixture;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import nl.socrates.dom.party.SocPerson;
import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.dom.party.SocPersons;
import nl.socrates.fixture.geography.refdata.CountriesAndStatesRefData;
import nl.socrates.fixture.party.PersonForInezDo;
import nl.socrates.fixture.party.PersonForJohanDo;


public class SocratesDemoFixture extends FixtureScript {
    
    @Override
    protected void execute(ExecutionContext executionContext) {
        // prereqs
//        execute(new TearDownFixture(), executionContext);
        
        // create
        execute(new PersonForJohanDo(), executionContext);
        execute(new PersonForInezDo(), executionContext);
        
        create("JKORT", "J", "Jan","met de","Korte-Achternaam", "Johannes", PersonGenderType.MALE, new LocalDate(1960,6,26), "Amsterdam", "Nederland", executionContext);
        
        execute(new CountriesAndStatesRefData(), executionContext);

        
    }
    
    // //////////////////////////////////////

    private SocPerson create(String reference, String initials, String firstName, String middleName, String lastName, String baptismalName, PersonGenderType gender, LocalDate dateOfBirth, String placeOfBirth, String Nationality, ExecutionContext executionContext) {
        return persons.newPerson(reference, initials, firstName, middleName,lastName, baptismalName, gender, dateOfBirth, placeOfBirth, Nationality);
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private SocPersons persons;

}
