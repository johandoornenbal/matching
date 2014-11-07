package nl.yodo.fixture.party;

import nl.yodo.dom.TrustLevel;

public class PersonForTest2 extends YodoPersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createYodoPerson(
                "222", 
                "Tessa", 
                "de", 
                "Tester2", 
                "test2",
                "test1",
                TrustLevel.ENTRY_LEVEL, //user test1 takes user test2 as a personalcontact at ENTRY_LEVEL
                "test4",
                TrustLevel.ENTRY_LEVEL,
                executionContext);
    }

}
