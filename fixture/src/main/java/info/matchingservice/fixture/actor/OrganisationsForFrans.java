package info.matchingservice.fixture.actor;


public class OrganisationsForFrans extends OrganisationAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createOrganisation(
                "123", 
                "Frans&Co",
                "frans",
                executionContext);
        
        createOrganisation(
                "124", 
                "Frans&Co2",
                "frans",
                executionContext);
    }
    
    

}
