package info.matchingservice.fixture.actor;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;


public class PersonalContactsForMichiel extends PersonalContactAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	
        createPersonalContact(
                persons.findPersons("Hals").get(0),
                "michiel",
                TrustLevel.OUTER_CIRCLE,
                executionContext);
        
        createPersonalContact(
                persons.findPersons("Rijn").get(0),
                "michiel",
                TrustLevel.ENTRY_LEVEL,
                executionContext);
    }
    
    @Inject
    private Persons persons;

}
