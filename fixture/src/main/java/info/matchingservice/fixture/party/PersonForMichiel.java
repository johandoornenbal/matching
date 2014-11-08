package info.matchingservice.fixture.party;

import info.matchingservice.dom.Party.RoleType;

public class PersonForMichiel extends PersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createPerson(
                "114", 
                "Michiel", 
                "de", 
                "Ruyter",
                RoleType.STUDENT,
                "michiel",
                executionContext);
    }

}
