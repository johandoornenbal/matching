package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class PersonForFrans extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "111", 
                "Frans", 
                "", 
                "Hals",
                new LocalDate(1962, 7, 16),
                "frans",
                executionContext);
    }

}
