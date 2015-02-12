package info.matchingservice.integtest.dom;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import info.matchingservice.dom.Config;
import info.matchingservice.dom.Configs;
import info.matchingservice.fixture.MatchingTestsFixture;
import info.matchingservice.integtest.MatchingIntegrationTest;

public class ConfigTest extends MatchingIntegrationTest {
	
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new MatchingTestsFixture());
    }
    
    @Inject
    Configs configs;
    
    public static class TestConfig extends ConfigTest {
    	
    	Config c1;
    	
    	@Test
    	public void valuesSet() throws Exception {
    		c1 = configs.allConfigs().get(0);
    		assertTrue(c1.getDemoFixturesLoaded());
    		assertThat(configs.allConfigs().size(), is(1));
    	}
    	
    	
    }

}
