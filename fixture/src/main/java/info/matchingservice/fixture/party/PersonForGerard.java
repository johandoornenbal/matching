package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

public class PersonForGerard extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "113", 
                "Gerard", 
                "", 
                "Dou",
                RoleType.STUDENT,
                "gerard",
                executionContext);
    }

}
