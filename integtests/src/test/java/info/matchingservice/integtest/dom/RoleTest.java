package info.matchingservice.integtest.dom;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScript.ExecutionContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import info.matchingservice.dom.Actor.PersonRole;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.PersonRoles;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

public class RoleTest extends MatchingIntegrationTest {
    
    @Inject 
    PersonRoles roles;
    
//    @BeforeClass
//    public static void setupTransactionalData() throws Exception {
//        scenarioExecution().install(new MatchingTestsFixture());
//    }
    
    @Before
    public void setupData() {
        runScript(new FixtureScript() {
            @Override
            protected void execute(ExecutionContext executionContext) {  	
                executionContext.executeChild(this, new TestPersons());
            }
        });
    }
    
    public static class testRole extends RoleTest {
        
        private static final PersonRoleType ROLE = PersonRoleType.STUDENT;
        private static final String USERNAME = "tester";
        
        PersonRole r1;
        
        @Test
        public void valuesSet() throws Exception {
            r1 = roles.allRoles().get(0);
            assertThat(r1.getRole(), is(ROLE));
            assertThat(r1.getOwnedBy(), is(USERNAME));
        }
    }
    
    public static class NewRole extends RoleTest {
        
        private static final PersonRoleType ROLE = PersonRoleType.PROFESSIONAL;
        private static final String USERNAME = "frans";
        
        PersonRole r1;
        PersonRole r2;
        
        @Before
        public void setUp() throws Exception {
            r1 = roles.createRole(ROLE, USERNAME);
        }
        
        @Test
        public void valuesSet() throws Exception {
            Integer maxindex = roles.allRoles().size() - 1;
            r2 = roles.allRoles().get(maxindex);
            assertThat(r1.getRole(), is(ROLE));
            assertThat(r1.getOwnedBy(), is(USERNAME));
            assertThat(r1,is(r2));
        }
        
    }
    
    public static class ExistingRole extends RoleTest {
        
        private static final PersonRoleType ROLE = PersonRoleType.PROFESSIONAL;
        private static final String USERNAME = "frans";
        
        PersonRole r1;
        
        @Before
        public void setUp() throws Exception {
            r1 = roles.createRole(ROLE, USERNAME);
        }
        
        @Test
        public void testValidateCreateRole() throws Exception {
            assertThat(roles.validateCreateRole(ROLE, USERNAME), is("ONE_INSTANCE_AT_MOST"));
            assertTrue(roles.validateCreateRole(ROLE, "xyz") == null);
        }
    }
    

}
