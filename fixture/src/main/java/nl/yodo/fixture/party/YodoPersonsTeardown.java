package nl.yodo.fixture.party;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class YodoPersonsTeardown extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {    
        isisJdoSupport.executeUpdate("delete from \"YodoPersonalContact\"");
        isisJdoSupport.executeUpdate("delete from \"YodoPerson\"");
        isisJdoSupport.executeUpdate("delete from \"YodoParty\"");
        isisJdoSupport.executeUpdate("delete from \"Secobject\"");
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
