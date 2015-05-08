package info.matchingservice.dom.CommunicationChannels;

import info.matchingservice.dom.AbstractBeanPropertiesTest;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonForTesting;
import org.junit.Test;

/**
 * Created by jonathan on 21-4-15.
 */
public class AddressTest {

    public static class BeanProperties extends AbstractBeanPropertiesTest{

        @Test
        public void test(){


            final Address address = new Address();

            address.setAddress("Straat123");



            newPojoTester().withFixture(pojos(Person.class, PersonForTesting.class)).exercise(address);





        }








    }


}
