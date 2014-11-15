package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class PersonForMichiel extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "114", 
                "Michiel", 
                "de", 
                "Ruyter",
                "michiel",
                executionContext);
    }

}
