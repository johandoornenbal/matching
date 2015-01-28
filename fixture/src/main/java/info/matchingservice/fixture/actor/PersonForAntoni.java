package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class PersonForAntoni extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                new LocalDate(1632, 10, 24),
                "antoni",
                executionContext);
    }

}
