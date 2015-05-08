package info.matchingservice.fixture.communicationChannels;

import java.util.List;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;

/**
 * Created by jonathan on 22-4-15.
 */
public class TestPhone extends CommunicationChannelsAbstract{


    @Override
    protected void execute(ExecutionContext executionContext) {

        List<Person> personen = persons.allPersons();

        for(Person p: personen){

            String phoneNumber = "06";

            for(int i = 0; i <8; i++){

                phoneNumber = phoneNumber + ( "" + (int)(Math.random()*10) ) ;


            }




            createPhone(phoneNumber, CommunicationChannelType.PHONE_HOME, p, executionContext);



        }






    }

    @Inject
    Persons persons;


}
