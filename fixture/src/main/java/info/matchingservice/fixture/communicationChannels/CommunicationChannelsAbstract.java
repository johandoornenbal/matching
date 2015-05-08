package info.matchingservice.fixture.communicationChannels;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;

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


    protected Phone createPhone(
            String phoneNumber,
            CommunicationChannelType type,
            Person person,
            String ownedBy,
            ExecutionContext executionContext) {

        Phone phone = communicationChannels.createPhone(person, type, phoneNumber, ownedBy );


        return executionContext.add(this, phone);


    }

    protected Address createAddress(
            Person person,
            CommunicationChannelType type,
            String address1,
            String postalCode,
            String woonPlaats,
            ExecutionContext executionContext) {
        Address address = communicationChannels.createAddress(person, type, address1, postalCode, woonPlaats);

        return executionContext.add(this,address);

    }







    @Inject
    private CommunicationChannels communicationChannels;


    @Inject
    private Persons persons;

}
