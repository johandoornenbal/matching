package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;


public class TestPersons extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
    	
        createPerson(
                "Frans", 
                "", 
                "Hals",
                new LocalDate(1962, 7, 16),
                null,
                "frans",
                executionContext);
        
        createPerson( 
                "Gerard", 
                "", 
                "Dou",
                new LocalDate(1970, 1, 18),
                null,
                "gerard",
                executionContext);
        
        createPerson( 
                "Rembrandt", 
                "van", 
                "Rijn",
                new LocalDate(1963, 12, 30),
                null,
                "rembrandt",
                executionContext);
        
        createPerson( 
                "Michiel", 
                "de", 
                "Ruyter",
                new LocalDate(1980, 8, 12),
                null,
                "michiel",
                executionContext);
    	
        createPerson(
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                new LocalDate(1632, 10, 24),
                null,
                "antoni",
                executionContext);
    }

}
