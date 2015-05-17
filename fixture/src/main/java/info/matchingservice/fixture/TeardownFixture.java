package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class TeardownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

//        isisJdoSupport.executeUpdate("delete from isissecurity.\"ApplicationPermission\"");
//        isisJdoSupport.executeUpdate("delete from isissecurity.\"ApplicationUserRoles\"");
//        isisJdoSupport.executeUpdate("delete from isissecurity.\"ApplicationRole\"");
//        isisJdoSupport.executeUpdate("delete from isissecurity.\"ApplicationUser\"");
//        isisJdoSupport.executeUpdate("delete from isissecurity.\"ApplicationTenancy\"");

//        isisJdoSupport.executeUpdate("delete from \"NonTenantedEntity\"");
//        isisJdoSupport.executeUpdate("delete from \"TenantedEntity\"");
        
    	isisJdoSupport.executeUpdate("delete from \"Config\"");
    	
    	isisJdoSupport.executeUpdate("delete from \"TagHolder\"");
    	isisJdoSupport.executeUpdate("delete from \"Tag\"");
    	isisJdoSupport.executeUpdate("delete from \"TagCategory\"");
    	
    	isisJdoSupport.executeUpdate("delete from \"DemandAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"SupplyAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"Assessment\"");
        
        isisJdoSupport.executeUpdate("delete from \"PersonRole\"");
        isisJdoSupport.executeUpdate("delete from \"OrganisationRole\"");
        isisJdoSupport.executeUpdate("delete from \"SystemRole\"");
        isisJdoSupport.executeUpdate("delete from \"Role\"");
        isisJdoSupport.executeUpdate("delete from \"TagHolder\"");
        
        isisJdoSupport.executeUpdate("delete from \"ProfileMatch\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileComparison\"");
          
//        isisJdoSupport.executeUpdate("delete from \"DropDownForProfileElement\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileElement\"");
        
        isisJdoSupport.executeUpdate("delete from \"Profile\"");
        isisJdoSupport.executeUpdate("delete from \"Demand\"");
        isisJdoSupport.executeUpdate("delete from \"Supply\"");
        
//        isisJdoSupport.executeUpdate("delete from \"Phone\"");
//        isisJdoSupport.executeUpdate("delete from \"Email\"");
//        isisJdoSupport.executeUpdate("delete from \"CommunicationChannel\"");
        
        isisJdoSupport.executeUpdate("delete from \"MatchingTrustedContact\"");
        isisJdoSupport.executeUpdate("delete from \"CommunicationChannel\"");

        isisJdoSupport.executeUpdate("delete from \"Person\"");
        isisJdoSupport.executeUpdate("delete from \"Organisation\""); 
        isisJdoSupport.executeUpdate("delete from \"System\""); 
        isisJdoSupport.executeUpdate("delete from \"Actor\"");
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
