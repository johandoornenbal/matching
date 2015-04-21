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

            address.setAddress1("Straat123");
            address.setAddress2("Laan213");
            address.setAddress3("15123");

            newPojoTester().withFixture(pojos(Person.class, PersonForTesting.class)).exercise(address);





        }


        @Test
        public void test1(){


            final Address address = new Address();


        }





    }


}
