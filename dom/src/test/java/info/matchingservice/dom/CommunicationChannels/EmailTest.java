package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.Actor.ActorForTesting;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonForTesting;
import info.matchingservice.dom.PojoTester;
import org.junit.Test;

/**
 * Created by jonathan on 21-4-15.
 */
public class EmailTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest{


        @Test
        public void test(){

            final Email email = new Email();
            newPojoTester().withFixture(pojos(Person.class, PersonForTesting.class))
            .exercise(email);


        }




    }




}
