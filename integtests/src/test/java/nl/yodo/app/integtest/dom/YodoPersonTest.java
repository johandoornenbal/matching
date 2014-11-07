package nl.yodo.app.integtest.dom;

import javax.inject.Inject;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import nl.yodo.app.integtest.YodoIntegrationTest;
import nl.yodo.dom.Party.YodoPerson;
import nl.yodo.dom.Party.YodoPersons;
import nl.yodo.fixture.party.YodoPersonsTeardown;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class YodoPersonTest extends YodoIntegrationTest {
    
    @Inject
    YodoPersons persons;
    
    public static class TestPerson extends YodoPersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final String UNIQUE_ID = "111";
        private static final String OWNED_BY = "test1";
        
        YodoPerson p1;
        
        @Test
        public void valuesSet() throws Exception {
            p1 = persons.allPersons().get(0);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));
            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            assertThat(p1.getUniquePartyId(), is(UNIQUE_ID));            
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            
        }
        
    }

    public static class NewPerson extends YodoPersonTest {
        
        private static final String LAST_NAME = "Test1";
        private static final String MIDDLE_NAME = "van der";
        private static final String FIRST_NAME = "T.";
        private static final String UNIQUE_ID = "321";
        private static final String OWNED_BY = "test1";
        
        YodoPerson p1;
        YodoPerson p2;
        
        @BeforeClass
        public static void setupTransactionalData() {
            scenarioExecution().install(new YodoPersonsTeardown());
        }
        
        @Before
        public void setUp() throws Exception {
            p1=persons.newPerson(UNIQUE_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, OWNED_BY);
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = persons.allPersons().size() - 1;
            p2 = persons.allPersons().get(maxindex);
            
            assertThat(p1.getFirstName(), is(FIRST_NAME));
            assertThat(p1.getLastName(), is(LAST_NAME));

            assertThat(p1.getMiddleName(), is(MIDDLE_NAME));
            
            assertThat(p1.getUniquePartyId(), is(UNIQUE_ID));
            
            assertThat(p1.getOwnedBy(), is(OWNED_BY));
            
            assertThat(p2.getUniquePartyId(), is(UNIQUE_ID));
            assertThat(p2.getOwnedBy(), is(OWNED_BY));
            
            assertThat(p1,is(p2));
            
        }
        
    }    
}
