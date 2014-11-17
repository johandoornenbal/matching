package info.matchingservice.fixture.actor;


public class PersonForRembrandt extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "112", 
                "Rembrandt", 
                "van", 
                "Rijn",
                "Dit is Rembrandt",
                "Getal van R",
                13,
                "rembrandt",
                executionContext);
    }

}
