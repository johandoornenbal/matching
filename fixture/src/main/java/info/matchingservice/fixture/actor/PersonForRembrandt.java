package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class PersonForRembrandt extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "112", 
                "Rembrandt", 
                "van", 
                "Rijn",
                "rembrandt",
                executionContext);
    }

}
