package info.matchingservice.fixture.actor;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Persons;

import javax.inject.Inject;


public class PersonalContactsForFrans extends PersonalContactAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
        executeChild(new TestPersons(), executionContext);
        
        createPersonalContact(
                persons.findPersons("Rijn").get(0),
                "frans",
                TrustLevel.INNER_CIRCLE,
                executionContext);
        
        createPersonalContact(
                persons.findPersons("Ruyter").get(0),
                "frans",
                TrustLevel.ENTRY_LEVEL,
                executionContext);
    }
    
    @Inject
    private Persons persons;

}
