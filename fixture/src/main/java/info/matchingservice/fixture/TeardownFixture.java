package info.matchingservice.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.objectstore.jdo.applib.service.support.IsisJdoSupport;

public class TeardownFixture extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) { 
        
        isisJdoSupport.executeUpdate("delete from \"Quality\"");
        
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"NeedAssessment\"");
        isisJdoSupport.executeUpdate("delete from \"Assessment\"");
        
        isisJdoSupport.executeUpdate("delete from \"PersonRole\"");
        isisJdoSupport.executeUpdate("delete from \"OrganisationRole\"");
        isisJdoSupport.executeUpdate("delete from \"SystemRole\"");
        isisJdoSupport.executeUpdate("delete from \"Role\"");
        
        isisJdoSupport.executeUpdate("delete from \"ProfileMatch\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileComparison\"");
          
        isisJdoSupport.executeUpdate("delete from \"ProfileDropDownElement\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileTextElement\"");
        isisJdoSupport.executeUpdate("delete from \"Pe_Keyword\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileFigureElement\"");
        isisJdoSupport.executeUpdate("delete from \"ProfileElement\"");
        isisJdoSupport.executeUpdate("delete from \"Profile\"");
        
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileDropDownElement\"");
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileTextElement\"");
        isisJdoSupport.executeUpdate("delete from \"Vpe_Keyword\"");
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileFigureElement\"");
        isisJdoSupport.executeUpdate("delete from \"VacancyProfileElement\"");
        isisJdoSupport.executeUpdate("delete from \"VacancyProfile\"");
        isisJdoSupport.executeUpdate("delete from \"PersonNeed\"");
        isisJdoSupport.executeUpdate("delete from \"OrganisationNeed\"");
        isisJdoSupport.executeUpdate("delete from \"Need\"");
        
        isisJdoSupport.executeUpdate("delete from \"Person\"");
        isisJdoSupport.executeUpdate("delete from \"Organisation\""); 
        isisJdoSupport.executeUpdate("delete from \"System\""); 
        isisJdoSupport.executeUpdate("delete from \"Actor\"");
    }
    
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}
