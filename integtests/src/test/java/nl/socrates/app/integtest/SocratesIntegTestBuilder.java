package nl.socrates.app.integtest;

import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.objectstore.jdo.datanucleus.DataNucleusPersistenceMechanismInstaller;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

public class SocratesIntegTestBuilder extends IsisSystemForTest.Builder {

    public SocratesIntegTestBuilder() {

        withLoggingAt(org.apache.log4j.Level.INFO);
        with(testConfiguration());
        with(new DataNucleusPersistenceMechanismInstaller());

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

        // withServices(
        // new FakeExcelService());
    }

    private static IsisConfiguration testConfiguration() {
        final IsisConfigurationForJdoIntegTests testConfiguration = new IsisConfigurationForJdoIntegTests();
        testConfiguration.addRegisterEntitiesPackagePrefix("nl");

        // uncomment to use log4jdbc instead
        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionDriverName",
        // "net.sf.log4jdbc.DriverSpy");

        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionURL",
        // "jdbc:hsqldb:mem:test;sqllog=3");

        //
        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionURL",
        // "jdbc:sqlserver://localhost:1433;instance=.;databaseName=estatio");
        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionDriverName",
        // "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionUserName",
        // "estatio");
        // testConfiguration.put("isis.persistor.datanucleus.impl.javax.jdo.option.ConnectionPassword",
        // "estatio");

        return testConfiguration;
    }

    // public static class FakeExcelService implements ExcelService {
    // @Override
    // public <T> Blob toExcel(List<T> domainObjects, Class<T> cls, String
    // fileName) throws Exception {
    // return null;
    // }
    //
    // @Override
    // public <T extends ViewModel> List<T> fromExcel(Blob excelBlob, Class<T>
    // cls) throws Exception {
    // return null;
    // }
    // }
}
