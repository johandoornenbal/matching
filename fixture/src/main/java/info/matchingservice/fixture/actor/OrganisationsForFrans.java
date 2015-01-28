package info.matchingservice.fixture.actor;


public class OrganisationsForFrans extends OrganisationAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createOrganisation( 
                "Frans&Co",
                "frans",
                executionContext);
        
        createOrganisation(
                "Frans&Co2",
                "frans",
                executionContext);
    }
    
    

}
