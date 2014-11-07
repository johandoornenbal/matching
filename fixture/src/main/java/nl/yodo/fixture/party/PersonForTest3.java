package nl.yodo.fixture.party;

import nl.yodo.dom.TrustLevel;

public class PersonForTest3 extends YodoPersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createYodoPerson(
                "333", 
                "Teo", 
                "", 
                "Test3", 
                "test3",
                "test2",
                TrustLevel.OUTER_CIRCLE, //user test2 takes user test3 as a personalcontact at OUTER_CIRCLE
                "test1",
                TrustLevel.ENTRY_LEVEL,
                executionContext);
    }

}
