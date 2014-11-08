package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

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
