package nl.socrates.app.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import nl.socrates.app.integtest.SocratesIntegrationTest;
import nl.socrates.dom.party.SocParties;
import nl.socrates.fixture.SocratesDemoFixture;

import org.junit.BeforeClass;
import org.junit.Test;

public class PartiesTest extends SocratesIntegrationTest {

    @Inject
    SocParties parties;

    @BeforeClass
    public static void setupTransactionalData() {
        scenarioExecution().install(new SocratesDemoFixture());
    }

    public static class AllParties extends PartiesTest {

        @Test
        public void size() throws Exception {
            assertThat(parties.allParties().size(), is(3));
        }
    }

    public static class FindParties extends PartiesTest {

        @Test
        public void findJohan() throws Exception {
            assertThat(parties.findParties("*johan*").size(), is(1));
        }

        @Test
        public void findNothing() throws Exception {
            assertThat(parties.findParties("*dfgsdgf*").size(), is(0));
        }
    }


}
