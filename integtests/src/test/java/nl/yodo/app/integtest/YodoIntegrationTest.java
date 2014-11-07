package nl.yodo.app.integtest;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

import nl.yodo.fixture.YodoTestsFixtures;

/**
 * Base class for integration tests.
 */
public abstract class YodoIntegrationTest extends IntegrationTestAbstract {
    
    private static final Logger LOG = LoggerFactory.getLogger(YodoIntegrationTest.class);
    
    @BeforeClass
    public static void initClass() {
        PropertyConfigurator.configure("logging.properties");
        YodoSystemInitializer.initIsft();
        LOG.info("Starting tests");
        new ScenarioExecutionForIntegration();
    }
    
    @Before
    public void setUpData() throws Exception {
        scenarioExecution().install(new YodoTestsFixtures());
    }

}
