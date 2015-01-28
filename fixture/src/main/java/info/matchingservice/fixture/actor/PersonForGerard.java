package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class PersonForGerard extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "113", 
                "Gerard", 
                "", 
                "Dou",
                new LocalDate(1970, 1, 18),
                "gerard",                
                executionContext);
    }

}
