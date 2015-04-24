package info.matchingservice.fixture.communicationChannels;

import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.fixture.actor.TestPersons;

/**
 * Created by jonathan on 22-4-15.
 */
public class TestAddress extends CommunicationChannelsAbstract{


    @Override
    protected void execute(ExecutionContext executionContext) {


        String streets = "Madison Avenue\n" +
                "1st Avenue\n" +
                "12th Street East\n" +
                "Route 27\n" +
                "Pleasant Street\n" +
                "B Street\n" +
                "3rd Street\n" +
                "Linden Avenue\n" +
                "Morris Street\n" +
                "Chestnut Avenue\n" +
                "Berkshire Drive\n" +
                "Summer Street\n" +
                "3rd Street North\n" +
                "Dogwood Drive\n" +
                "Jefferson Street\n" +
                "Park Place\n" +
                "Cardinal Drive\n" +
                "Hickory Lane\n" +
                "Cemetery Road\n" +
                "Fairview Avenue\n" +
                "Belmont Avenue\n" +
                "Lexington Drive\n" +
                "Fairway Drive\n" +
                "Highland Avenue\n" +
                "Rose Street\n" +
                "2nd Street East\n" +
                "Lawrence Street\n" +
                "Holly Court\n" +
                "York Street\n" +
                "Essex Court";



        Scanner scanner = new Scanner(streets);

        //preqs
        executionContext.executeChild(this, new TestPersons());


        List<Person> personen = persons.allPersons();


        String street;
        String postcode;
        for(Person p: personen){


            postcode = "";

            if(!scanner.hasNextLine()){

                scanner = new Scanner(streets);

            }

            street = scanner.nextLine();

            for(int i = 0; i <4; i++){

                postcode = postcode + (int)(Math.random()*10);


            }

            createAddress(p, CommunicationChannelType.ADDRESS_MAIN, street, postcode, executionContext);



        }






    }

    @Inject
    Persons persons;


}
