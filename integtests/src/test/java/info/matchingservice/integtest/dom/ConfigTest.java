package info.matchingservice.integtest.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import info.matchingservice.dom.Config;
import info.matchingservice.dom.Configs;
import info.matchingservice.integtest.MatchingIntegrationTest;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest extends MatchingIntegrationTest {
	
    @BeforeClass
    public static void setupTransactionalData() throws Exception {
        scenarioExecution().install(new info.matchingservice.fixture.TestConfig());
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
