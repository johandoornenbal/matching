package info.matchingservice.fixture.actor;


public class PersonForMichiel extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "114", 
                "Michiel", 
                "de", 
                "Ruyter",
                "Dit is Michiel",
                "Michiels getal",
                11,
                "michiel",
                executionContext);
    }

}
