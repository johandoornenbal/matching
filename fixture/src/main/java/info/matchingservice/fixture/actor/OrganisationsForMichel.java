package info.matchingservice.fixture.actor;


public class OrganisationsForMichel extends OrganisationAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createOrganisation(
                "125", 
                "De Zilveren Vloot",
                "michiel",
                executionContext);
        
    }
    
    

}
