package info.matchingservice.fixture.actor;


public class PersonForGerard extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "113", 
                "Gerard", 
                "", 
                "Dou",
                "Het profiel van Gerard",
                "My Magic figure",
                7,
                "gerard",
                executionContext);
    }

}
