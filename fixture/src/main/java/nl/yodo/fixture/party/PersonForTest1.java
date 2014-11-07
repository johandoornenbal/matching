package nl.yodo.fixture.party;

import nl.yodo.dom.TrustLevel;

public class PersonForTest1 extends YodoPersonAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        createYodoPerson(
                "111", 
                "T.", 
                "van der", 
                "Test1", 
                "test1",
                "test4",
                TrustLevel.ENTRY_LEVEL, //user test4 takes user test1 as a personalcontact at ENTRY_LEVEL
                "test3",
                TrustLevel.INNER_CIRCLE, //user test3 takes user test1 as a personal contact at INNER_CIRCLE level
                executionContext);
    }

}
