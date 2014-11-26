package info.matchingservice.fixture.actor;


public class PersonForAntoni extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "115", 
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                "antoni",
                executionContext);
    }

}
