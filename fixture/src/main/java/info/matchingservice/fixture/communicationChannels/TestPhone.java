package info.matchingservice.fixture.communicationChannels;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.fixture.actor.TestPersons;

/**
 * Created by jonathan on 22-4-15.
 */
public class TestPhone extends CommunicationChannelsAbstract{


    @Override
    protected void execute(ExecutionContext executionContext) {

        //preqs
        executionContext.executeChild(this, new TestPersons());

//        List<Person> personen = persons.allPersons();

        Person p = persons.findPersons("Hals").get(0);

//        for(Person p: personen){

//            String phoneNumber = "06";
//
//            for(int i = 0; i <8; i++){
//
//                phoneNumber = phoneNumber + ( "" + (int)(Math.random()*10) ) ;
//
//
//            }

            String phoneNumber = "06 12345678";


            createPhone(p, CommunicationChannelType.PHONE_MAIN, phoneNumber, executionContext);



//        }






    }

    @Inject
    Persons persons;


}
