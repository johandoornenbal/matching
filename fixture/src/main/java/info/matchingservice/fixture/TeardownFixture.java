package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class TeardownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {


        
    	isisJdoSupport.executeUpdate("delete from \"Config\"");

        isisJdoSupport.executeUpdate("delete from \"BasicCategorySuggestion\"");
        isisJdoSupport.executeUpdate("delete from \"BasicRating\"");
        isisJdoSupport.executeUpdate("delete from \"BasicCategoryRelationshipTuple\"");

        isisJdoSupport.executeUpdate("delete from \"BasicAnswer\"");
        isisJdoSupport.executeUpdate("delete from \"BasicForm\"");
        isisJdoSupport.executeUpdate("delete from \"BasicQuestion\"");
        isisJdoSupport.executeUpdate("delete from \"BasicFeedback\"");
        isisJdoSupport.executeUpdate("delete from \"BasicContact\"");
        isisJdoSupport.executeUpdate("delete from \"BasicRequest\"");
        isisJdoSupport.executeUpdate("delete from \"BasicTemplate\"");
        isisJdoSupport.executeUpdate("delete from \"BasicCategory\"");
        isisJdoSupport.executeUpdate("delete from \"BasicUser\"");

    	isisJdoSupport.executeUpdate("delete from \"TagHolder\"");
    	isisJdoSupport.executeUpdate("delete from \"Tag\"");
    	isisJdoSupport.executeUpdate("delete from \"TagCategory\"");

        isisJdoSupport.executeUpdate("delete from \"PersonRole\"");
        isisJdoSupport.executeUpdate("delete from \"OrganisationRole\"");
        isisJdoSupport.executeUpdate("delete from \"SystemRole\"");
        isisJdoSupport.executeUpdate("delete from \"Role\"");

        isisJdoSupport.executeUpdate("delete from \"Assessment\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileMatch\"");

        isisJdoSupport.executeUpdate("delete from \"ProfileComparison\"");

        isisJdoSupport.executeUpdate("delete from \"PersistedProfileElementComparison\"");

        isisJdoSupport.executeUpdate("delete from \"ProfileElement\"");


        isisJdoSupport.executeUpdate("delete from \"Profile\"");
        isisJdoSupport.executeUpdate("delete from \"Demand\"");
        isisJdoSupport.executeUpdate("delete from \"Supply\"");

        isisJdoSupport.executeUpdate("delete from \"MatchingTrustedContact\"");
        isisJdoSupport.executeUpdate("delete from \"CommunicationChannel\"");

        isisJdoSupport.executeUpdate("delete from \"ServiceOccurrence\"");
        isisJdoSupport.executeUpdate("delete from \"Service\"");

        isisJdoSupport.executeUpdate("delete from \"Person\"");



        isisJdoSupport.executeUpdate("delete from \"Organisation\""); 
        isisJdoSupport.executeUpdate("delete from \"System\""); 
        isisJdoSupport.executeUpdate("delete from \"Actor\"");
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
