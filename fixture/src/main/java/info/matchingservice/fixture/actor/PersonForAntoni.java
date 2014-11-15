package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.RoleType;

public class PersonForAntoni extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "115", 
                "Antoni", 
                "van", 
                "Leeuwenhoek",
                "antoni",
                executionContext);
    }

}
