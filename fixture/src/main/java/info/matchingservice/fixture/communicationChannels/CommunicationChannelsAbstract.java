package info.matchingservice.fixture.communicationChannels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.fixture.actor.TestPersons;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import javax.inject.Inject;

/**
 * Created by jonathan on 22-4-15.
 */
public abstract class CommunicationChannelsAbstract extends FixtureScript {


    @Override
    protected abstract void execute(ExecutionContext executionContext);


    protected Email createEmail(
            String emailAddres,
            CommunicationChannelType type,
            Person person,
            ExecutionContext executionContext) {

        Email email = communicationChannels.createEmail(emailAddres, type, person);


        return executionContext.add(this, email);


    }


    protected Phone createPhone(Person person, CommunicationChannelType type,String phoneNumber, ExecutionContext executionContext) {

        Phone phone = communicationChannels.createPhone(person, type, phoneNumber);


        return executionContext.add(this, phone);


    }

    protected Address createAddress(Person person,
                                    CommunicationChannelType type,
                                    String address1,
                                    String postalCode,
                                    ExecutionContext executionContext) {
        Address address = communicationChannels.createAddress(person, type, address1, postalCode);

        return executionContext.add(this,address);

    }







    @Inject
    private CommunicationChannels communicationChannels;


    @Inject
    private Persons persons;

}
