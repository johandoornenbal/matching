package nl.socrates.app.integtest;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract;
import org.apache.isis.core.integtestsupport.scenarios.ScenarioExecutionForIntegration;

/**
 * Base class for integration tests.
 */
public abstract class SocratesIntegrationTest extends IntegrationTestAbstract {
    
    private static final Logger LOG = LoggerFactory.getLogger(SocratesIntegrationTest.class);
    
    @BeforeClass
    public static void initClass() {
        PropertyConfigurator.configure("logging.properties");
        SocratesSystemInitializer.initIsft();
        LOG.info("Starting tests");
        new ScenarioExecutionForIntegration();
    }

}
