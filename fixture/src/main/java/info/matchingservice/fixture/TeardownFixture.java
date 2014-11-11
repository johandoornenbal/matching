package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class TeardownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) { 
        isisJdoSupport.executeUpdate("delete from \"Role\"");
        isisJdoSupport.executeUpdate("delete from \"Profile\"");
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileElement\"");
        isisJdoSupport.executeUpdate("delete from \"Vacancy\"");
        isisJdoSupport.executeUpdate("delete from \"Need\"");
        isisJdoSupport.executeUpdate("delete from \"Person\"");     
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
