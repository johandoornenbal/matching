package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContacts;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

public class PersonalContactTest extends MatchingIntegrationTest {
	
	@Inject
    Persons persons;
	
	@Inject
	PersonalContacts personalcontacts;
	
	@BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
	
	public static class hideAddAsPersonalContact extends PersonalContactTest {
		
				
		@Test
        public void shouldBeHiddenAddAndDelete() throws Exception {
			
			Person p1;
			Person frans;
			p1 = persons.createPerson("Test", "van der", "Test", new LocalDate(1962,7,16), null, "tester");
			assertThat(p1.getOwnedBy(),is("tester"));
			// because p1 is owned by tester Action AddAsPersonalContact should be hidden
			assertThat(personalcontacts.hideAddAsPersonalContact(p1), is(true));
			// and validate should indicate that it is no use to add yourself as a contact person
			assertThat(personalcontacts.validateAddAsPersonalContact(p1), is("NO_USE"));
			
			frans = persons.findPersons("hals").get(0);
			// because frans is not a contact yet, contact Action AddAsPersonalContact should NOT be hidden
			assertThat(personalcontacts.hideAddAsPersonalContact(frans), is(false));
			assertThat(personalcontacts.allPersonalContactsOfUser().size(), is(0));
			personalcontacts.addAsPersonalContact(frans);
			// because frans is now added as a personal contact Action AddAsPersonalContact should be hidden
			assertThat(personalcontacts.hideAddAsPersonalContact(frans), is(true));
			// and validate should indicate that frans is already a contact
			assertThat(personalcontacts.validateAddAsPersonalContact(frans), is("ONE_INSTANCE_AT_MOST"));
			assertThat(personalcontacts.allPersonalContactsOfUser().size(), is(1));
			
			personalcontacts.allPersonalContactsOfUser().get(0).deleteTrustedContact(true);
			// because frans is not a contact anymore, contact Action AddAsPersonalContact should NOT be hidden
			assertThat(personalcontacts.hideAddAsPersonalContact(frans), is(false));
			assertThat(personalcontacts.allPersonalContactsOfUser().size(), is(0));
		}
		
	}

}
