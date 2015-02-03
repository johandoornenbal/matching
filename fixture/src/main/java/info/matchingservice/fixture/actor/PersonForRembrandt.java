package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class PersonForRembrandt extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson( 
                "Rembrandt", 
                "van", 
                "Rijn",
                new LocalDate(1963, 12, 30),
                "rembrandt",
                null,
                executionContext);
    }

}
