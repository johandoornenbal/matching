package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonForTesting;

import org.junit.Test;

public class PhoneTest {
	
	public static class BeanProperties extends AbstractBeanPropertiesTest {

        @Test
        public void test() {
            final Phone pojo = new Phone();
            newPojoTester()
            		.withFixture(pojos(Person.class, PersonForTesting.class))
                    .exercise(pojo);
        }

    }

}
