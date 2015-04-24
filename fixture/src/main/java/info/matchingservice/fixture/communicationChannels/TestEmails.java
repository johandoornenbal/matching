package info.matchingservice.fixture.communicationChannels;

import java.util.List;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;

/**
 * Created by jonathan on 22-4-15.
 */
public class TestEmails extends CommunicationChannelsAbstract{


    @Override
    protected void execute(ExecutionContext executionContext) {

        List<Person> personen = persons.allPersons();

        for(Person p: personen){

            createEmail((p.getFirstName() + "." + p.getLastName() + "@Xtalus.com"), CommunicationChannelType.EMAIL_MAIN, p, executionContext);

        }

    }

    @Inject
    Persons persons;

}
