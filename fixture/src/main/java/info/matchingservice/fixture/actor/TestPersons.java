package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;

import info.matchingservice.fixture.security.antoniUser;
import info.matchingservice.fixture.security.differentUser;
import info.matchingservice.fixture.security.fransUser;
import info.matchingservice.fixture.security.gerardUser;
import info.matchingservice.fixture.security.jeanneUser;
import info.matchingservice.fixture.security.michielUser;
import info.matchingservice.fixture.security.rembrandtUser;
import info.matchingservice.fixture.security.test1User;
import info.matchingservice.fixture.security.test2User;
import info.matchingservice.fixture.security.testerUser;

public class TestPersons extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

            //preqs
            executeChild(new antoniUser(), executionContext);
            executeChild(new fransUser(), executionContext);
            executeChild(new michielUser(), executionContext);
            executeChild(new rembrandtUser(), executionContext);
            executeChild(new gerardUser(), executionContext);
            executeChild(new jeanneUser(), executionContext);
            executeChild(new test1User(), executionContext);
            executeChild(new test2User(), executionContext);
            executeChild(new differentUser(), executionContext);
            executeChild(new testerUser(), executionContext);

        createPerson(
                "Frans",
                "",
                "Hals",
                new LocalDate(1962, 7, 16),
                null,
                "frans",
                true,
                executionContext);
        
        createPerson( 
                "Gerard", 
                "", 
                "Dou",
                new LocalDate(1970, 1, 18),
                null,
                "gerard",
                true,
                executionContext);
        
        createPerson( 
                "Rembrandt", 
                "van", 
                "Rijn",
                new LocalDate(1963, 12, 30),
                null,
                "rembrandt",
                true,
                executionContext);
        
        createPerson( 
                "Michiel", 
                "de", 
                "Ruyter",
                new LocalDate(1980, 8, 12),
                null,
                "michiel",
                true,
                executionContext);
    	
        createPerson(
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                new LocalDate(1632, 10, 24),
                null,
                "antoni",
                true,
                executionContext);

            createPerson(
                    "Jeanne",
                    "d'",
                    "Arc",
                    new LocalDate(1970, 7, 10),
                    null,
                    "jeanne",
                    false,
                    executionContext);
    }

}
