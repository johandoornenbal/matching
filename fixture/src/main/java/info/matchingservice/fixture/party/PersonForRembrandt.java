package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

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
