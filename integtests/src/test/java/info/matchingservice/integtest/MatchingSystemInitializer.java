package info.matchingservice.integtest;

import info.matchingservice.app.MatchingAppManifest;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with Matching app's domain services. 
 */
public class MatchingSystemInitializer {
    
    private MatchingSystemInitializer() {
        
    }
    
    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if (isft == null) {
            isft = new MatchingSystemBuilder().build().setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }


    private static class MatchingSystemBuilder extends IsisSystemForTest.Builder {
        public MatchingSystemBuilder() {
            withLoggingAt(org.apache.log4j.Level.INFO);
            with(new MatchingAppManifest());
            with(testConfiguration());
//            with(new DataNucleusPersistenceMechanismInstaller());

//            // services annotated with @DomainService
//            withServicesIn(
//                        "info.matchingservice.dom",
//                        "org.isisaddons.module",
//                        "org.apache.isis.core.wrapper",
//                        "org.apache.isis.applib",
//                        "org.apache.isis.core.metamodel.services",
//                        "org.apache.isis.core.runtime.services",
//                        "org.apache.isis.objectstore.jdo.datanucleus.service.support", // IsisJdoSupportImpl
//                        "org.apache.isis.objectstore.jdo.datanucleus.service.eventbus" // EventBusServiceJdo
//                         );
        }
    }
    
    private static IsisConfiguration testConfiguration() {
        final IsisConfigurationForJdoIntegTests testConfiguration = new IsisConfigurationForJdoIntegTests();
        testConfiguration.addRegisterEntitiesPackagePrefix("info");
        return testConfiguration;
    }
        
}
