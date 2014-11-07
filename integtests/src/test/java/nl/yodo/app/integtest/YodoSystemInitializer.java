package nl.yodo.app.integtest;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.DataNucleusPersistenceMechanismInstaller;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with Yodo Matching app's domain services. 
 */
public class YodoSystemInitializer {
    
    private YodoSystemInitializer() {
        
    }
    
    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if (isft == null) {
            isft = new YodoSystemBuilder().build().setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }

    private static class YodoSystemBuilder extends IsisSystemForTest.Builder {
        public YodoSystemBuilder() {
            withLoggingAt(org.apache.log4j.Level.INFO);
            with(testConfiguration());
            with(new DataNucleusPersistenceMechanismInstaller());

            // services annotated with @DomainService
            withServicesIn(
                        "nl.yodo",
                        "nl.socrates",
                        "org.isisaddons",
                        "org.apache.isis.core.wrapper",
                        "org.apache.isis.applib",
                        "org.apache.isis.core.metamodel.services",
                        "org.apache.isis.core.runtime.services",
                        "org.apache.isis.objectstore.jdo.datanucleus.service.support", // IsisJdoSupportImpl
                        "org.apache.isis.objectstore.jdo.datanucleus.service.eventbus" // EventBusServiceJdo
                         );
        }
    }
    
    private static IsisConfiguration testConfiguration() {
        final IsisConfigurationForJdoIntegTests testConfiguration = new IsisConfigurationForJdoIntegTests();
        testConfiguration.addRegisterEntitiesPackagePrefix("nl");
        return testConfiguration;
    }
        
}
