package info.matchingservice.fixture.communicationChannels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by jonathan on 22-4-15.
 */
public class TestPhone extends CommunicationChannelsAbstract{


    @Override
    protected void execute(ExecutionContext executionContext) {

        List<Person> personen = persons.activePerson();

        for(Person p: personen){

            String phoneNumber = "06";

            for(int i = 0; i <8; i++){

                phoneNumber = phoneNumber + ( "" + (int)(Math.random()*10) ) ;


            }


            createPhone(p, CommunicationChannelType.PHONE_MAIN, phoneNumber, executionContext);



        }






    }

    @Inject
    Persons persons;


}
