package info.matchingservice.fixture.actor;


public class PersonForFrans extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "111", 
                "Frans", 
                "", 
                "Hals",
                "Profiel van Frans",
                "Getal van Frans",
                5,
                "frans",
                executionContext);
    }

}
