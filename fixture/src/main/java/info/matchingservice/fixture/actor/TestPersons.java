package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;
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


            final String lorumIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Maecenas congue ligula ac quam viverra nec consectetur ante hendrerit. Donec et mollis dolor. Praesent et diam eget libero egestas mattis sit amet vitae augue. Nam tincidunt congue enim, ut porta lorem lacinia consectetur. Donec ut libero sed arcu vehicula ultricies a non tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ut gravida lorem. Ut turpis felis, pulvinar a semper sed, adipiscing id dolor. Pellentesque auctor nisi id magna consequat sagittis. Curabitur dapibus enim sit amet elit pharetra tincidunt feugiat nisl imperdiet. Ut convallis libero in urna ultrices accumsan. Donec sed odio eros. Donec viverra mi quis quam pulvinar at malesuada arcu rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In rutrum accumsan ultricies. Mauris vitae nisi at sem facilisis semper ac in est.";

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
                PersonRoleType.STUDENT,
                executionContext, lorumIpsum);
        
        createPerson( 
                "Gerard", 
                "", 
                "Dou",
                new LocalDate(1970, 1, 18),
                null,
                "gerard",
                true,
                PersonRoleType.PRINCIPAL,
                executionContext,
                lorumIpsum);
        
        createPerson( 
                "Rembrandt", 
                "van", 
                "Rijn",
                new LocalDate(1963, 12, 30),
                null,
                "rembrandt",
                true,
                PersonRoleType.PROFESSIONAL,
                executionContext, lorumIpsum);
        
        createPerson( 
                "Michiel", 
                "de", 
                "Ruyter",
                new LocalDate(1980, 8, 12),
                null,
                "michiel",
                true,PersonRoleType.PRINCIPAL,
                executionContext, lorumIpsum);
    	
        createPerson(
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                new LocalDate(1632, 10, 24),
                null,
                "antoni",
                true,
                PersonRoleType.PROFESSIONAL,
                executionContext, lorumIpsum);

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
