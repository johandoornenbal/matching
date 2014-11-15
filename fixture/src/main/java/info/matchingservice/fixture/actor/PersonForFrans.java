package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.PersonRoleType;

public class PersonForFrans extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "111", 
                "Frans", 
                "", 
                "Hals",
                "frans",
                executionContext);
    }

}
