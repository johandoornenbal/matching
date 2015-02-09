package info.matchingservice.fixture.actor;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Actor.PersonalContacts;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PersonalContactAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected PersonalContact createPersonalContact (
            Person contactPerson,
            String ownedBy,
            TrustLevel trustLevel,
            ExecutionContext executionContext
            ) {
        PersonalContact newPersonalContact = personalsContacts.createPersonalContact(contactPerson, ownedBy, trustLevel);
                       
        return executionContext.add(this, newPersonalContact);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private PersonalContacts personalsContacts;


}