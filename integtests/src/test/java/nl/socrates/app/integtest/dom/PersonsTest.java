package nl.socrates.app.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import nl.socrates.app.integtest.SocratesIntegrationTest;
import nl.socrates.dom.party.SocPerson;
import nl.socrates.dom.party.PersonGenderType;
import nl.socrates.dom.party.SocPersons;
import nl.socrates.fixture.SocratesDemoFixture;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersonsTest extends SocratesIntegrationTest {

    @Inject
    SocPersons persons;

    @BeforeClass
    public static void setupTransactionalData() {
        scenarioExecution().install(new SocratesDemoFixture());
    }

    public static class NewPerson extends PersonsTest {

        private static final String NATIONALITY = "NL";
        private static final String PLACE_OF_BIRTH = "Jubbega";
        private static final LocalDate DATE_OF_BIRTH = new LocalDate(1980, 2, 3);
        private static final PersonGenderType MALE = PersonGenderType.MALE;
        private static final String BAPTISMAL_NAME = "Jan";
        private static final String LAST_NAME = "Potgerukt";
        private static final String MIDDLE_NAME = "van de";
        private static final String FIRST_NAME = "Johannes";
        private static final String INITIALS = "JMC";
        private static final String REFERENCE = "reference";

        SocPerson p;

        @Before
        public void setUp() throws Exception {
            p = persons.newPerson(
                    REFERENCE,
                    INITIALS,
                    FIRST_NAME,
                    MIDDLE_NAME,
                    LAST_NAME,
                    BAPTISMAL_NAME,
                    MALE,
                    DATE_OF_BIRTH,
                    PLACE_OF_BIRTH,
                    NATIONALITY);
        }

        @Test
        public void valuesSet() throws Exception {
            assertThat(p.getReference(), is(REFERENCE));
            assertThat(p.getBaptismalName(), is(BAPTISMAL_NAME));
            assertThat(p.getDateOfBirth(), is(DATE_OF_BIRTH));
            /// etc
        }
    }

}
