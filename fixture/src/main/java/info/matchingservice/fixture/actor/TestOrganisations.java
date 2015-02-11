package info.matchingservice.fixture.actor;


public class TestOrganisations extends OrganisationAbstract {

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
        
        createOrganisation( 
                "De Zilveren Vloot",
                "michiel",
                executionContext);
    }
    
    

}
