package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class PersonForMichiel extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "114", 
                "Michiel", 
                "de", 
                "Ruyter",
                new LocalDate(1980, 8, 12),
                "michiel",
                executionContext);
    }

}
