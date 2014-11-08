package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

public class PersonForFrans extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "111", 
                "Frans", 
                "", 
                "Hals",
                RoleType.STUDENT,
                "frans",
                executionContext);
    }

}
