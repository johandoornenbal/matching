package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class PersonForGerard extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "113", 
                "Gerard", 
                "", 
                "Dou",
                "gerard",
                executionContext);
    }

}
