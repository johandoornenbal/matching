package info.matchingservice.fixture.actor;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;


public class PersonalContactsForRembrandt extends PersonalContactAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new PersonForFrans(), executionContext);
        executeChild(new PersonForMichiel(), executionContext);
        
        createPersonalContact(
                persons.findPersons("Hals").get(0),
                "rembrandt",
                TrustLevel.INNER_CIRCLE,
                executionContext);
        
        createPersonalContact(
                persons.findPersons("Ruyter").get(0),
                "rembrandt",
                TrustLevel.ENTRY_LEVEL,
                executionContext);
    }
    
    @Inject
    private Persons persons;

}
