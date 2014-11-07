package info.matchingservice.fixture.party;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class PersonsTeardown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {    
        isisJdoSupport.executeUpdate("delete from \"Person\"");
        isisJdoSupport.executeUpdate("delete from \"Party\"");
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
