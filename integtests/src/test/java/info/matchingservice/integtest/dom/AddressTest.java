package info.matchingservice.integtest.dom;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.communicationChannels.TestAddress;
import info.matchingservice.integtest.MatchingIntegrationTest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by jonathan on 1-5-15.
 */
public class AddressTest extends MatchingIntegrationTest {

    @Inject
    CommunicationChannels communicationChannels;

    @Inject
    Persons persons;


    @BeforeClass
    public static void setupTransactionalData() throws Exception {

        scenarioExecution().install(new TestPersons());
        scenarioExecution().install(new TestAddress());

    }




    public static class deleteAddressTest extends AddressTest{


        public Person frans;
        public Address address;


        @Before
        public void setUp() throws Exception {

            //given
            frans = persons.findPersons("Hals").get(0);

            address = (Address)communicationChannels.allCommunicationChannels(frans, Address.class).get(0);



        }


        @Test
        public void deleteAddress() throws Exception {

            // remove adress
            address.deleteCommunicationChannel(true);



//            assertNull(address);


        }







    }

    public static class updateAddressTest extends AddressTest{


        public Person frans;
        public Address address;


        @Before
        public void setUp() throws Exception {


            //given
            frans = persons.findPersons("Hals").get(0);
            address = (Address)communicationChannels.allCommunicationChannels(frans, Address.class).get(0);





        }


        @Test
        public void updateAddress() throws Exception {

            address.updateAddress(CommunicationChannelType.ADDRESS_COMPANY, "Friesland", "Hoogeland 14", "7777AA");

            assertThat(address.getAddress() ,is("Hoogeland 14"));
            assertThat(address.getPostalCode(), is("7777AA"));
            assertThat(address.getType(), is(CommunicationChannelType.ADDRESS_COMPANY));




        }



    }



}
